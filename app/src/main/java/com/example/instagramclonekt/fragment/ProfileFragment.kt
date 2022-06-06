package com.example.instagramclonekt.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
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
import com.example.instagramclonekt.utils.Extensions.toast
import com.example.instagramclonekt.utils.Logger
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import java.lang.Exception
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.instagramclonekt.activity.LikedPostsActivity


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
    lateinit var tv_fullname1:TextView
    lateinit var tv_email:TextView
    lateinit var tv_posts:TextView
    lateinit var tv_followers:TextView
    lateinit var tv_following:TextView

    lateinit var toolbar: Toolbar
    //lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view)
        setHasOptionsMenu(true)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(activity,3))

        iv_profile = view.findViewById(R.id.iv_profile)
        tv_fullname = view.findViewById(R.id.tv_fullname)
        tv_fullname1 = view.findViewById(R.id.tv_fullname1)
        tv_posts = view.findViewById(R.id.tv_posts)
        tv_email = view.findViewById(R.id.tv_email)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)

        toolbar = view.findViewById(R.id.toolBar) as Toolbar

        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }

        base = requireActivity() as BaseActivity

        val iv_logout = view.findViewById<ImageView>(R.id.iv_logout)
        iv_logout.setOnClickListener {

        }


        toolbar.inflateMenu(R.menu.nav_drawer);
        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.first) {

                activity?.let{
                    val intent = Intent (it, LikedPostsActivity::class.java)
                    it.startActivity(intent)
                }

            } else if (item.itemId == R.id.second) {
                AuthManager.signOut()
                base.callSignInActivity(requireActivity())
            } else {
                // do something
            }
            false
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

    private fun loadMyPosts(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadPosts(uid, object: DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                tv_posts.text = posts.size.toString()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                TODO("Not yet implemented")
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
        tv_fullname1.text = user.fullname

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_drawer,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }


}