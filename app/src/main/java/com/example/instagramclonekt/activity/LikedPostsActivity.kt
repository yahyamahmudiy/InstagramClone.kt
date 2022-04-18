package com.example.instagramclonekt.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.LikedPostsAdapter
import com.example.instagramclonekt.fragment.FavouriteFragment
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.handler.DBPostsHandler
import com.example.instagramclonekt.model.Post
import java.lang.Exception

class LikedPostsActivity : BaseActivity() {
    val TAG = FavouriteFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked_posts)

        initViews()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(this,1))

        val iv_back:ImageView = findViewById(R.id.iv_back)
        iv_back.setOnClickListener {
            finish()
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
        showLoading(this)
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