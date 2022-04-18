package com.example.instagramclonekt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclonekt.R
import com.example.instagramclonekt.fragment.FavouriteFragment
import com.example.instagramclonekt.model.Favourite

class FavouriteAdapter(var fragment: FavouriteFragment, var items:ArrayList<Favourite>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_favourite,parent,false)
        return FavouriteViewHolder(view)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = items[position]

        if (holder is FavouriteViewHolder) {
            val iv_karenne = holder.iv_karenne
            val iv_karenne_liked1 = holder.iv_karenne_liked1
            val iv_karenne_liked2 = holder.iv_karenne_liked2
            val iv_karenne_liked3 = holder.iv_karenne_liked3
            val iv_kairo_d = holder.iv_kairo_d
            val iv_zackjohn = holder.iv_zackjohn
            val iv_joshua_photo = holder.iv_joshua_photo
            val iv_kairo_d1 = holder.iv_kairo_d1
            val iv_craig_love = holder.iv_craig_love
            val iv_craig_love_liked1 = holder.iv_craig_love_liked1
            val iv_craig_love_liked2 = holder.iv_craig_love_liked2
            val iv_craig_love_liked3 = holder.iv_craig_love_liked3
            val iv_craig_love_liked4 = holder.iv_craig_love_liked4
            val iv_craig_love_liked5 = holder.iv_craig_love_liked5
            val iv_craig_love_liked6 = holder.iv_craig_love_liked6
            val iv_craig_love_liked7 = holder.iv_craig_love_liked7
            val iv_craig_love_liked8 = holder.iv_craig_love_liked8
            val iv_maxjabson = holder.iv_maxjabson
            val iv_zackjohn1 = holder.iv_zackjohn1
            val iv_mispoter_photo = holder.iv_mispoter_photo
            val iv_maxjabson1 = holder.iv_maxjabson1
            val iv_craig_love1 = holder.iv_craig_love1
            val iv_martinit_photo = holder.iv_martinit_photo
            val iv_karenne1 = holder.iv_karenne1
            val iv_martini = holder.iv_martini
            val iv_maxjabson11 = holder.iv_maxjabson11
            val iv_maxjobson_liked1 = holder.iv_maxjobson_liked1
            val iv_maxjobson_liked2 = holder.iv_maxjobson_liked2
            val iv_maxjobson_liked3 = holder.iv_maxjobson_liked3

            Glide.with(fragment).load(user.img1).into(iv_karenne)
            Glide.with(fragment).load(user.img2).into(iv_karenne_liked1)
            Glide.with(fragment).load(user.img3).into(iv_karenne_liked2)
            Glide.with(fragment).load(user.img4).into(iv_karenne_liked3)
            Glide.with(fragment).load(user.img5).into(iv_kairo_d)
            Glide.with(fragment).load(user.img6).into(iv_zackjohn)
            Glide.with(fragment).load(user.img7).into(iv_joshua_photo)
            Glide.with(fragment).load(user.img8).into(iv_kairo_d1)
            Glide.with(fragment).load(user.img9).into(iv_craig_love)
            Glide.with(fragment).load(user.img10).into(iv_craig_love_liked1)
            Glide.with(fragment).load(user.img11).into(iv_craig_love_liked2)
            Glide.with(fragment).load(user.img12).into(iv_craig_love_liked3)
            Glide.with(fragment).load(user.img13).into(iv_craig_love_liked4)
            Glide.with(fragment).load(user.img14).into(iv_craig_love_liked5)
            Glide.with(fragment).load(user.img15).into(iv_craig_love_liked6)
            Glide.with(fragment).load(user.img16).into(iv_craig_love_liked7)
            Glide.with(fragment).load(user.img17).into(iv_craig_love_liked8)
            Glide.with(fragment).load(user.img18).into(iv_maxjabson)
            Glide.with(fragment).load(user.img19).into(iv_zackjohn1)
            Glide.with(fragment).load(user.img20).into(iv_mispoter_photo)
            Glide.with(fragment).load(user.img21).into(iv_maxjabson1)
            Glide.with(fragment).load(user.img22).into(iv_craig_love1)
            Glide.with(fragment).load(user.img23).into(iv_martinit_photo)
            Glide.with(fragment).load(user.img24).into(iv_karenne1)
            Glide.with(fragment).load(user.img25).into(iv_martini)
            Glide.with(fragment).load(user.img26).into(iv_maxjabson11)
            Glide.with(fragment).load(user.img27).into(iv_maxjobson_liked1)
            Glide.with(fragment).load(user.img28).into(iv_maxjobson_liked2)
            Glide.with(fragment).load(user.img29).into(iv_maxjobson_liked3)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view){
        val iv_karenne:ImageView
        val iv_karenne_liked1:ImageView
        val iv_karenne_liked2:ImageView
        val iv_karenne_liked3:ImageView
        val iv_kairo_d:ImageView
        val iv_zackjohn:ImageView
        val iv_joshua_photo:ImageView
        val iv_kairo_d1:ImageView
        val iv_craig_love:ImageView
        val iv_craig_love_liked1:ImageView
        val iv_craig_love_liked2:ImageView
        val iv_craig_love_liked3:ImageView
        val iv_craig_love_liked4:ImageView
        val iv_craig_love_liked5:ImageView
        val iv_craig_love_liked6:ImageView
        val iv_craig_love_liked7:ImageView
        val iv_craig_love_liked8:ImageView
        val iv_maxjabson:ImageView
        val iv_zackjohn1:ImageView
        val iv_mispoter_photo:ImageView
        val iv_maxjabson1:ImageView
        val iv_craig_love1:ImageView
        val iv_martinit_photo:ImageView
        val iv_karenne1:ImageView
        val iv_martini:ImageView
        val iv_maxjabson11:ImageView
        val iv_maxjobson_liked1:ImageView
        val iv_maxjobson_liked2:ImageView
        val iv_maxjobson_liked3:ImageView

        init {
            iv_karenne = view.findViewById(R.id.iv_karenne)
            iv_karenne_liked1 = view.findViewById(R.id.iv_karenne_liked1)
            iv_karenne_liked2 = view.findViewById(R.id.iv_karenne_liked2)
            iv_karenne_liked3 = view.findViewById(R.id.iv_karenne_liked3)
            iv_kairo_d = view.findViewById(R.id.iv_kairo_d)
            iv_zackjohn = view.findViewById(R.id.iv_zackjohn)
            iv_joshua_photo = view.findViewById(R.id.iv_joshua_photo)
            iv_kairo_d1 = view.findViewById(R.id.iv_kairo_d1)
            iv_craig_love = view.findViewById(R.id.iv_craig_love)
            iv_craig_love_liked1 = view.findViewById(R.id.iv_craig_love_liked1)
            iv_craig_love_liked2 = view.findViewById(R.id.iv_craig_love_liked2)
            iv_craig_love_liked3 = view.findViewById(R.id.iv_craig_love_liked3)
            iv_craig_love_liked4 = view.findViewById(R.id.iv_craig_love_liked4)
            iv_craig_love_liked5 = view.findViewById(R.id.iv_craig_love_liked5)
            iv_craig_love_liked6 = view.findViewById(R.id.iv_craig_love_liked6)
            iv_craig_love_liked7 = view.findViewById(R.id.iv_craig_love_liked7)
            iv_craig_love_liked8 = view.findViewById(R.id.iv_craig_love_liked8)
            iv_maxjabson = view.findViewById(R.id.iv_maxjabson)
            iv_zackjohn1 = view.findViewById(R.id.iv_zackjohn1)
            iv_mispoter_photo = view.findViewById(R.id.iv_mispoter_photo)
            iv_maxjabson1 = view.findViewById(R.id.iv_maxjabson1)
            iv_craig_love1 = view.findViewById(R.id.iv_craig_love1)
            iv_martinit_photo = view.findViewById(R.id.iv_martinit_photo)
            iv_karenne1 = view.findViewById(R.id.iv_karenne1)
            iv_martini = view.findViewById(R.id.iv_martini)
            iv_maxjabson11 = view.findViewById(R.id.iv_maxjabson11)
            iv_maxjobson_liked1 = view.findViewById(R.id.iv_maxjobson_liked1)
            iv_maxjobson_liked2 = view.findViewById(R.id.iv_maxjobson_liked2)
            iv_maxjobson_liked3 = view.findViewById(R.id.iv_maxjobson_liked3)
        }
    }
}