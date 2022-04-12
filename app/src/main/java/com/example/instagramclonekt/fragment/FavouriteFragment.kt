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
        items.add(Post("https://i.pinimg.com/236x/e2/c6/f8/e2c6f8e3f98a1ffe81d2c85f96bd048a.jpg"))
        items.add(Post("https://i.pinimg.com/236x/ca/a5/ab/caa5ab719d1f777db347b250abf62748.jpg"))
        items.add(Post("https://i.pinimg.com/564x/0a/6a/a6/0a6aa64732767db0c5a6b1068719adb0.jpg"))
        return items
    }
}