package com.example.instagramclonekt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.FavouriteAdapter
import com.example.instagramclonekt.model.Favourite


class FavouriteFragment : BaseFragment() {
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

    private fun loadPosts(): ArrayList<Favourite> {
        val items = ArrayList<Favourite>()

        for (i in 0..2){

        items.add(
            Favourite(
            "https://i.pinimg.com/236x/e7/85/de/e785de451ef377349afdf04952ff0158.jpg",
            "https://i.pinimg.com/236x/2f/c8/7f/2fc87f6520c822fc50a4ee187b6996d7.jpg",
            "https://i.pinimg.com/236x/56/18/23/56182338760063b0e9bd3b4e73b9d02f.jpg",
            "https://i.pinimg.com/236x/af/04/fa/af04fad65ea06a814c8668c373b94348.jpg",
            "https://i.pinimg.com/236x/7f/ea/d2/7fead2696646e29d5e07e9229b961820.jpg",
            "https://i.pinimg.com/236x/21/70/e4/2170e4badf81317f62955b74330d77c8.jpg",
            "https://i.pinimg.com/236x/21/90/c4/2190c4a690a3e021fea0543a09001333.jpg",
            "https://i.pinimg.com/236x/7f/ea/d2/7fead2696646e29d5e07e9229b961820.jpg",
            "https://i.pinimg.com/236x/86/33/df/8633df3ce5789db2df95e12f9eaf6a0c.jpg",
           "https://i.pinimg.com/236x/0b/7e/b0/0b7eb05bd32959665ba26b9ddb0524ac.jpg",
           "https://i.pinimg.com/236x/07/6f/bc/076fbc024c3de238e7ea3131b53483c5.jpg",
           "https://i.pinimg.com/236x/82/d5/a2/82d5a26668c6b6047c8f662b03ca71c8.jpg",
           "https://i.pinimg.com/236x/7d/13/cc/7d13cc4cdd95bb1eae287d657a296803.jpg",
           "https://i.pinimg.com/236x/7c/85/36/7c853640a25bd207eda885c9efa15de4.jpg",
           "https://i.pinimg.com/236x/ba/05/9f/ba059f4bd989b08576562fbcaa17e2a3.jpg",
           "https://i.pinimg.com/236x/55/2e/a5/552ea5b26fb9112ab361467e6a601c83.jpg",
           "https://i.pinimg.com/236x/ce/53/b0/ce53b0ecbaa11a28d84ffdcef22e6653.jpg",
           "https://i.pinimg.com/236x/de/92/a1/de92a16c938ea9b73df819320b4d7ca6.jpg",
           "https://i.pinimg.com/236x/21/70/e4/2170e4badf81317f62955b74330d77c8.jpg",
           "https://i.pinimg.com/236x/60/13/26/601326977372516739e1a7f1b0e19601.jpg",
           "https://i.pinimg.com/236x/de/92/a1/de92a16c938ea9b73df819320b4d7ca6.jpg",
           "https://i.pinimg.com/236x/86/33/df/8633df3ce5789db2df95e12f9eaf6a0c.jpg",
           "https://i.pinimg.com/236x/68/92/5b/68925bb1e7e43a74276dc128f8a279fe.jpg",
           "https://i.pinimg.com/236x/e7/85/de/e785de451ef377349afdf04952ff0158.jpg",
           "https://i.pinimg.com/236x/bf/f5/14/bff514d1ba362d4892e7c0f338dc3183.jpg",
           "https://i.pinimg.com/236x/de/92/a1/de92a16c938ea9b73df819320b4d7ca6.jpg",
           "https://i.pinimg.com/236x/62/58/25/625825e3fb41434d60004d11d28bcdaa.jpg",
           "https://i.pinimg.com/236x/a7/27/8f/a7278f13e7839cd812a69a2e1cb33e74.jpg",
           "https://i.pinimg.com/236x/55/7d/94/557d947eb2177f0ef3a7e78034642da8.jpg"))

        }

        return items
    }

    private fun refreshAdapter(items: ArrayList<Favourite>) {
        val adapter = FavouriteAdapter(this,items)
        recyclerView.adapter = adapter
    }

}