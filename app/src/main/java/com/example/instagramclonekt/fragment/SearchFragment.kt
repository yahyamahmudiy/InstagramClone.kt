package com.example.instagramclonekt.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.SearchAdapter
import com.example.instagramclonekt.model.User

/*
* In SearchFragment, all registered users can be found by searching keyword and followed.
*/
class SearchFragment : BaseFragment() {
    val TAG = SearchFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    var items = ArrayList<User>()
    var users = ArrayList<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(activity,1))
        val et_search = view.findViewById<EditText>(R.id.et_search)
        et_search.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val keyword = p0.toString().trim()
                usersByKeyword(keyword)
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })

        loadUsers()
        refreshAdapter(items)
    }

    fun refreshAdapter(items:ArrayList<User>){
        val adapter = SearchAdapter(this,items)
        recyclerView.adapter = adapter
    }

    private fun usersByKeyword(keyword: String) {
        if (keyword.isEmpty())
            refreshAdapter(items)

        users.clear()
        for (user in items)
            if (user.fullname.toLowerCase().startsWith(keyword.toLowerCase()))
                users.add(user)

        refreshAdapter(users)
    }

    fun loadUsers():ArrayList<User>{
        items = ArrayList()

        items.add(User("Xurshid","shamsun.com@gmail.com"))
        items.add(User("Begzod","shamsun.com@gmail.com"))
        items.add(User("Sherzod","shamsun.com@gmail.com"))
        items.add(User("Firdavs","shamsun.com@gmail.com"))
        items.add(User("Xurshid","shamsun.com@gmail.com"))
        items.add(User("Begzod","shamsun.com@gmail.com"))
        items.add(User("Sherzod","shamsun.com@gmail.com"))
        items.add(User("Firdavs","shamsun.com@gmail.com"))

        return items
    }

}