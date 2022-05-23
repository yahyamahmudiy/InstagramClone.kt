package com.example.instagramclonekt.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonekt.R
import com.example.instagramclonekt.adapter.SearchAdapter
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.handler.DBFollowHandler
import com.example.instagramclonekt.manager.handler.DBUserHandler
import com.example.instagramclonekt.manager.handler.DBUsersHandler
import com.example.instagramclonekt.model.FirebaseRequest
import com.example.instagramclonekt.model.FirebaseResponse
import com.example.instagramclonekt.model.Notification
import com.example.instagramclonekt.model.User
import com.example.instagramclonekt.network.ApiClient
import com.example.instagramclonekt.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
* In SearchFragment, all registered users can be found by searching keyword and followed.
*/
class SearchFragment : BaseFragment() {
    val TAG = SearchFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    var items = ArrayList<User>()
    var users = ArrayList<User>()

    private lateinit var apiService: ApiService
    private var token = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        apiService = ApiClient.createService(ApiService::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(activity,1))
        val et_search = view.findViewById<EditText>(R.id.et_search)

        et_search.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val keyword = p0.toString().trim()
                usersByKeyword(keyword)
            }

        })

        loadUsers()
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

<<<<<<< HEAD
    private fun loadUsers() {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
                DatabaseManager.loadFollowing(uid, object : DBUsersHandler {
                    override fun onSuccess(following: ArrayList<User>) {
                        items.clear()
                        items.addAll(mergedUsers(uid,users, following))
                        refreshAdapter(items)
                    }

                    override fun onError(e: Exception) {
=======
    fun loadUsers(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadUsers(object :DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                DatabaseManager.loadFollowing(uid,object : DBUsersHandler{
                    override fun onSuccess(following: ArrayList<User>) {
                        items.clear()
                        items.addAll(mergedUsers(uid,users,following))
                        refreshAdapter(items)
                    }

                    override fun onError(e: java.lang.Exception) {
>>>>>>> origin/master

                    }
                })
            }

            override fun onError(e: Exception) {

            }
        })
    }

<<<<<<< HEAD
    private fun mergedUsers(uid:String, users: ArrayList<User>, following: ArrayList<User>): ArrayList<User> {
        val items = ArrayList<User>()
        for (u in users){
            val user = u
            for(f in following){
                if(u.uid == f.uid){
=======
    fun mergedUsers(uid:String,users:ArrayList<User>,following:ArrayList<User>):ArrayList<User>{
        val items = ArrayList<User>()
        for (u in users){
            val user = u
            for (f in following){
                if (u.uid == f.uid){
>>>>>>> origin/master
                    user.isFollowed = true
                    break
                }
            }
<<<<<<< HEAD
            if(uid != user.uid){
=======
            if (uid != user.uid){
>>>>>>> origin/master
                items.add(user)
            }
        }
        return items
    }

<<<<<<< HEAD
    fun followOrUnfollow(to: User) {
=======
    fun followOrUnfollow(to:User){
>>>>>>> origin/master
        val uid = AuthManager.currentUser()!!.uid
        if (!to.isFollowed) {
            followUser(uid, to)
        } else {
            unFollowUser(uid, to)
        }
    }

    private fun sendFollowNotification(token: ArrayList<String>, user: String) {
        Log.d("tokennn", token.toString())
        apiService.sendNotification(FirebaseRequest(
            Notification("Sizga ${user} follow qildi", "Instagram clone"), token))
            .enqueue(object : Callback<FirebaseResponse> {
                override fun onResponse(
                    call: Call<FirebaseResponse>,
                    response: Response<FirebaseResponse>,
                ) {
                    Log.d("response", response.body().toString())
                }

                override fun onFailure(call: Call<FirebaseResponse>, t: Throwable) {
                    Log.d("failure", t.localizedMessage)
                }

            })
    }

    private fun sendUnFollowNotification(token:ArrayList<String>, user: String) {
        Log.d("tokennn", token.toString())
        apiService.sendNotification(FirebaseRequest(
            Notification("Sizdan ${user} unFollow qildi", "Instagram clone"), token))
            .enqueue(object : Callback<FirebaseResponse> {
                override fun onResponse(
                    call: Call<FirebaseResponse>,
                    response: Response<FirebaseResponse>,
                ) {
                    Log.d("response", response.body().toString())
                }

                override fun onFailure(call: Call<FirebaseResponse>, t: Throwable) {
                    Log.d("failure", t.localizedMessage)
                }

            })
    }

    private fun followUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler {
            override fun onSuccess(me: User?) {
                DatabaseManager.followUser(me!!, to, object : DBFollowHandler {
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollowed = true
<<<<<<< HEAD
                        //Log.d("@@@@", to.device_tokens[0])
                        if (to.device_tokens.size != 0){
                            sendFollowNotification(to.device_tokens, me.fullname)
                        } else {
                            Log.d("deviceToken", to.device_tokens.size.toString())
                        }

                        DatabaseManager.storePostsToMyFeed(uid, to)
=======
                        DatabaseManager.storePostsToMyFeed(uid,to)
                    }

                    override fun onError(e: java.lang.Exception) {

                    }

                })
            }

            override fun onError(e: java.lang.Exception) {

            }

        })
    }

    fun unfollowUser(uid: String,to:User){
        DatabaseManager.loadUser(uid,object :DBUserHandler{
            override fun onSuccess(me: User?) {
                DatabaseManager.unFollowUser(me!!,to,object : DBFollowHandler{
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollowed = false
                        DatabaseManager.removePostsToMyFeed(uid,to)
>>>>>>> origin/master
                    }

                    override fun onError(e: Exception) {
                    }
                })
            }
            override fun onError(e: Exception) {

            }
        })
    }

    private fun unFollowUser(uid: String, to: User) {
        DatabaseManager.loadUser(uid, object : DBUserHandler {
            override fun onSuccess(me: User?) {
                DatabaseManager.unFollowUser(me!!, to, object : DBFollowHandler {
                    override fun onSuccess(isFollowed: Boolean) {
                        to.isFollowed = false
                        //Log.d("@@@@", to.device_tokens[0])
                        if (to.device_tokens.size != 0){
                            sendUnFollowNotification(to.device_tokens, me.fullname)
                        } else {
                            Log.d("deviceToken", to.device_tokens.size.toString())
                        }
                        DatabaseManager.removePostsFromMyFeed(uid, to)
                    }

                    override fun onError(e: Exception) {

                    }
                })
            }

            override fun onError(e: Exception) {

            }
        })
    }

}