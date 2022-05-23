package com.example.instagramclonekt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclonekt.R
import com.example.instagramclonekt.fragment.HomeFragment
import com.example.instagramclonekt.fragment.ProfileFragment
import com.example.instagramclonekt.model.Post
import com.example.instagramclonekt.utils.Utils
import com.google.android.material.imageview.ShapeableImageView

class ProfileAdapter(var fragment:ProfileFragment, var items:ArrayList<Post>):BaseAdapter() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_profile,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post:Post = items[position]

        if (holder is PostViewHolder){
            val iv_post = holder.iv_post
            val tv_caption = holder.tv_caption

            tv_caption.text = post.caption
<<<<<<< HEAD
            tv_caption.visibility = View.GONE

=======
>>>>>>> origin/master
            setViewHeight(iv_post)
            Glide.with(fragment).load(post.postImg).into(iv_post)
        }
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_post:ShapeableImageView
        val tv_caption:TextView

        init {
            iv_post = view.findViewById(R.id.iv_post)
            tv_caption = view.findViewById(R.id.tv_caption)
        }
    }

    /**
     * Set ShapeableImageView height as screen width
     */
    private fun setViewHeight(view: View) {
        val params: ViewGroup.LayoutParams = view.getLayoutParams()
        params.height = Utils.screenSize(fragment.requireActivity().application).width / 3
        view.setLayoutParams(params)
    }


}