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
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.HomeAdapter
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.handler.DBPostsHandler
import com.example.instagramclonekt.model.Post
import java.lang.Exception
import java.lang.RuntimeException


class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private var listener:HomeListener? = null
    lateinit var iv_camera:ImageView
    lateinit var recyclerView: RecyclerView
    var feeds = ArrayList<Post>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    fun initViews(view: View) {
        iv_camera = view.findViewById(R.id.iv_camera)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(activity,1))

        iv_camera.setOnClickListener {
            listener!!.scrollToUpload()
        }

        loadMyFeeds()
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this,items)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadMyFeeds() {
        showLoading(requireActivity())

        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid,object :DBPostsHandler{
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

    /*
     * This interface is created for communication with UploadFragment
     */
    interface HomeListener{
        fun scrollToUpload()
    }
}