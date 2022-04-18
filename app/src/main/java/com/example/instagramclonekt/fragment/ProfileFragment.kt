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
import com.example.instagramclonekt.manager.handler.DBPostsHandler
import com.example.instagramclonekt.manager.handler.DBUserHandler
import com.example.instagramclonekt.manager.handler.DBUsersHandler
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
    lateinit var tv_posts:TextView
    lateinit var tv_followers:TextView
    lateinit var tv_following:TextView

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
        tv_posts = view.findViewById(R.id.tv_posts)
        tv_email = view.findViewById(R.id.tv_email)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)
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
        loadMyPosts()
        loadMyFollowers()
        loadMyFollowing()

    }

    fun loadMyFollowers(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowers(uid,object : DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                tv_followers.text = users.size.toString()

            }

            override fun onError(e: Exception) {

            }

        })
    }

    fun loadMyFollowing(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowing(uid,object : DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                tv_following.text = users.size.toString()
            }

            override fun onError(e: Exception) {

            }

        })
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

    fun loadMyPosts(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadPosts(uid,object :DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                tv_posts.text = posts.size.toString()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {

            }

        })

    }


}