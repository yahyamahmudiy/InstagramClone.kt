package com.example.instagramclonekt.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.HomeAdapter
import com.example.instagramclonekt.adapter.StoryAdapter
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.handler.DBPostHandler
import com.example.instagramclonekt.manager.handler.DBPostsHandler
import com.example.instagramclonekt.manager.handler.DBUsersHandler
import com.example.instagramclonekt.model.Post
import com.example.instagramclonekt.model.User
import com.example.instagramclonekt.utils.DialogListener
import com.example.instagramclonekt.utils.Utils
import java.lang.Exception
import java.lang.RuntimeException


class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private var listener:HomeListener? = null
    lateinit var iv_camera:ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerView1: RecyclerView
    lateinit var adapter:HomeAdapter
    lateinit var adapter1: StoryAdapter
    var feeds = ArrayList<Post>()
    var items = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    fun initViews(view: View) {
        iv_camera = view.findViewById(R.id.iv_camera)
        recyclerView = view.findViewById(R.id.recyclerView)

        val gridLayoutManager = GridLayoutManager(activity,1)
        //gridLayoutManager.setReverseLayout(true);
        //gridLayoutManager.setStackFromEnd(true);

        recyclerView.layoutManager = gridLayoutManager

        recyclerView1 = view.findViewById(R.id.recyclerView1)
        recyclerView1.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false))

        iv_camera.setOnClickListener {
            listener!!.scrollToUpload()
        }

        loadMyFeeds()
        loadUsers()
        refreshAdapter1(items)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && feeds.size > 0){
            Log.d(TAG, "setUserVisibleHint: ${isVisibleToUser}")
            loadMyFeeds()
        }
    }

    /*
     * onAttach is for communication of fragments
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeListener){
            context
        }else{
            throw RuntimeException("$context must implement HomeListener")
        }
    }

    /*
     * onDetach is for communication of fragments
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        adapter = HomeAdapter(this,items)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun refreshAdapter1(items: ArrayList<User>) {
        adapter1 = StoryAdapter(this,items)
        recyclerView1.adapter = adapter1

    }

    private fun loadUsers() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
                    override fun onSuccess(following: ArrayList<User>) {
                        items.clear()
                        items.addAll(mergedUsers(uid,users, following))
                        refreshAdapter1(items)
                    }

                    override fun onError(e: Exception) {

                    }
                })
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun mergedUsers(uid:String, users: ArrayList<User>, following: ArrayList<User>): ArrayList<User> {
        val items = ArrayList<User>()
        for (u in users){
            val user = u
            for(f in following){
                if(u.uid == f.uid){
                    user.isFollowed = true
                    break
                }
            }
            if(uid == user.uid ){
                items.add(0,user)
            }

            if(uid != user.uid ) items.add(user)
        }
        return items
    }

    private fun loadMyFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(feeds)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    fun likeOrUnlikePost(post: Post){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid,post)
    }

    fun showDeleteDialog(post: Post){
        Utils.dialogDouble(requireContext(),getString(R.string.str_delete_post),object : DialogListener{
            override fun onCallback(isChosen: Boolean) {
                if (isChosen){
                    deletePost(post)
                }
            }
        })
    }
    fun deletePost(post: Post){
        DatabaseManager.deletePost(post,object : DBPostHandler{
            override fun onSuccess(post: Post) {
                loadMyFeeds()
            }

            override fun onError(e: Exception) {

            }

        })
    }

    /*
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener{
        fun scrollToUpload()
    }
}