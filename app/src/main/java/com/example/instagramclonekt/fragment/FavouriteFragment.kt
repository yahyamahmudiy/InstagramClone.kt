package com.example.instagramclonekt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.FavouriteAdapter
import com.example.instagramclonekt.adapter.HomeAdapter
import com.example.instagramclonekt.model.Post


class FavouriteFragment : Fragment() {
    val TAG = FavouriteFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(activity,1))

        refreshAdapter(loadPosts())
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = FavouriteAdapter(this,items)
        recyclerView.adapter = adapter
    }

    private fun loadPosts(): ArrayList<Post> {
        val items = ArrayList<Post>()

        return items
    }
}