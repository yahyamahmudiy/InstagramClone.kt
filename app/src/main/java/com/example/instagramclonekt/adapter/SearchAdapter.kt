package com.example.instagramclonekt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclonekt.R
import com.example.instagramclonekt.fragment.HomeFragment
import com.example.instagramclonekt.fragment.SearchFragment
import com.example.instagramclonekt.model.Post
import com.example.instagramclonekt.model.User
import com.google.android.material.imageview.ShapeableImageView

class SearchAdapter(var fragment:SearchFragment, var items:ArrayList<User>):BaseAdapter() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_search,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user:User = items[position]

        if (holder is UserViewHolder){
            val tv_fullname = holder.tv_fullname
            val tv_email = holder.tv_email
            val iv_profile = holder.iv_profile
            var tv_follow = holder.tv_follow

            tv_fullname.text = user.fullname
            tv_email.text = user.email

            Glide.with(fragment).load(user.userImg)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(iv_profile)

            tv_follow.setOnClickListener {
                if(!user.isFollowed){
                    tv_follow.text = fragment.getString(R.string.str_following)
                    tv_follow.setTextColor(Color.BLACK)
                    tv_follow.setBackgroundResource(R.drawable.textview_rounded_corners)
                }else{
                    tv_follow.text = fragment.getString(R.string.str_follow)
                    tv_follow.setTextColor(Color.WHITE)
                    tv_follow.setBackgroundResource(R.drawable.textview_rounded_corners_blue)
                }
                fragment.followOrUnfollow(user)
            }

            if(!user.isFollowed){
                tv_follow.text = fragment.getString(R.string.str_follow)
                tv_follow.setTextColor(Color.WHITE)
                tv_follow.setBackgroundResource(R.drawable.textview_rounded_corners_blue)
            }else{
                tv_follow.text = fragment.getString(R.string.str_following)
                tv_follow.setTextColor(Color.BLACK)
                tv_follow.setBackgroundResource(R.drawable.textview_rounded_corners)
            }
        }
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_profile:ShapeableImageView
        val tv_fullname:TextView
        val tv_email:TextView
        val tv_follow:TextView

        init {
            iv_profile = view.findViewById(R.id.iv_profile)
            tv_fullname = view.findViewById(R.id.tv_fullname)
            tv_email = view.findViewById(R.id.tv_email)
            tv_follow = view.findViewById(R.id.tv_follow)
        }
    }


}