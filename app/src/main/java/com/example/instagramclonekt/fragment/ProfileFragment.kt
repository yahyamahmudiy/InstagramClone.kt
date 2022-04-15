package com.example.instagramclonekt.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclonekt.R
import com.example.instagramclonekt.activity.BaseActivity
import com.example.instagramclonekt.adapter.HomeAdapter
import com.example.instagramclonekt.adapter.ProfileAdapter
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.StorageManager
import com.example.instagramclonekt.manager.handler.DBUserHandler
import com.example.instagramclonekt.manager.handler.StorageHandler
import com.example.instagramclonekt.model.Post
import com.example.instagramclonekt.model.User
import com.example.instagramclonekt.utils.Logger
import com.google.android.material.imageview.ShapeableImageView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import java.lang.Exception

/*
* In ProfileFragment, posts that user uploaded can  be seen and user is able to change his/her profile photo.
*/
class ProfileFragment : Fragment() {
    val TAG = ProfileFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    lateinit var base:BaseActivity
    var pickedPhoto:Uri? = null
    var allPhotos = ArrayList<Uri>()
    lateinit var iv_profile:ShapeableImageView
    lateinit var tv_fullname:TextView
    lateinit var tv_email:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(activity,2))

        iv_profile = view.findViewById(R.id.iv_profile)
        tv_fullname = view.findViewById(R.id.tv_fullname)
        tv_email = view.findViewById(R.id.tv_email)
        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }

        base = requireActivity() as BaseActivity

        val iv_logout = view.findViewById<ImageView>(R.id.iv_logout)
        iv_logout.setOnClickListener {
            AuthManager.signOut()
            base.callSignInActivity(requireActivity())

        }
        loadUserInfo()

        refreshAdapter(loadPosts())
    }



    private fun uploadUserPhoto() {
        if (pickedPhoto == null) return
        StorageManager.uploadUserPhoto(pickedPhoto!!, object : StorageHandler {
            override fun onSuccess(imgUrl: String) {
                DatabaseManager.updateUserImage(imgUrl)
                iv_profile.setImageURI(pickedPhoto)
            }

            override fun onError(exception: Exception?) {

            }
        })
    }

    private fun loadUserInfo() {
        DatabaseManager.loadUser(AuthManager.currentUser()!!.uid, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                if (user != null) {
                    showUserInfo(user)
                }
            }

            override fun onError(e: Exception) {

            }
        })
    }
    private fun showUserInfo(user: User){
        tv_fullname.text = user.fullname
        tv_email.text = user.email

        Glide.with(this).load(user.userImg)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(iv_profile)
    }

    /*
    * Pick photo using FishBun library
    */
    private fun pickFishBunPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    val photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            allPhotos = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
            pickedPhoto = allPhotos.get(0)
            uploadUserPhoto()
        }
    }

    fun refreshAdapter(list:ArrayList<Post>){
        val adapter = ProfileAdapter(this, list)
        recyclerView.adapter = adapter
    }

    fun loadPosts():ArrayList<Post>{
        val items = ArrayList<Post>()

        items.add(Post("https://i.pinimg.com/236x/ca/a5/ab/caa5ab719d1f777db347b250abf62748.jpg"))
        items.add(Post("https://i.pinimg.com/236x/e2/c6/f8/e2c6f8e3f98a1ffe81d2c85f96bd048a.jpg"))
        items.add(Post("https://i.pinimg.com/564x/0a/6a/a6/0a6aa64732767db0c5a6b1068719adb0.jpg"))

        return items
    }
}