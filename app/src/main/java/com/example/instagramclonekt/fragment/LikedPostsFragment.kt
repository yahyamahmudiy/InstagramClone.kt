package com.example.instagramclonekt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.LikedPostsAdapter
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.handler.DBPostsHandler
import com.example.instagramclonekt.model.Post
import java.lang.Exception

class LikedPostsFragment : BaseFragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_liked_posts, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(requireContext(),1))

        val iv_back: ImageView = view.findViewById(R.id.iv_back)
        iv_back.setOnClickListener {
            findNavController().popBackStack()
        }

        refreshAdapter(loadPosts())
        loadLikedFeeds()
    }

    fun likeOrUnlikePost(post: Post){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid,post)

        loadLikedFeeds()
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = LikedPostsAdapter(this,items)
        recyclerView.adapter = adapter
    }

    fun loadLikedFeeds(){
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadLikedFeeds(uid,object : DBPostsHandler {
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }

        })
    }

    private fun loadPosts(): ArrayList<Post> {
        val items = ArrayList<Post>()

        return items
    }
}