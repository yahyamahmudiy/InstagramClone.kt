package com.example.instagramclonekt.manager

import com.example.instagramclonekt.manager.handler.*
import com.example.instagramclonekt.model.Post
import com.example.instagramclonekt.model.User
import com.google.firebase.firestore.FirebaseFirestore

object DatabaseManager {
    private var USER_PATH = "users"
    private var POST_PATH = "posts"
    private var FEED_PATH = "feeds"
    private var FOLLOWING_PATH = "following"
    private var FOLLOWERS_PATH = "followers"

    private var database = FirebaseFirestore.getInstance()

    fun storeUser(user: User, handler: DBUserHandler) {
        database.collection(USER_PATH).document(user.uid).set(user).addOnSuccessListener {
            handler.onSuccess()
        }.addOnFailureListener {
            handler.onError(it)
        }
    }

    fun loadUser(uid: String, handler: DBUserHandler) {
        database.collection(USER_PATH).document(uid).get().addOnSuccessListener {
            if (it.exists()) {
                val fullname: String? = it.getString("fullname")
                val email: String? = it.getString("email")
                val userImg: String? = it.getString("userImg")

                val user = User(fullname!!, email!!, userImg!!)
                user.uid = uid
                handler.onSuccess(user)
            } else {
                handler.onSuccess(null)
            }
        }.addOnFailureListener {
            handler.onError(it)
        }
    }

    fun updateUserImage(image: String) {
        val uid = AuthManager.currentUser()!!.uid
        database.collection(USER_PATH).document(uid).update("userImg", image)
    }

    fun loadUsers(handler: DBUsersHandler) {
        database.collection(USER_PATH).get().addOnCompleteListener {
            val users = ArrayList<User>()
            if (it.isSuccessful) {
                for (document in it.result!!) {
                    val uid = document.getString("uid")
                    val fullname = document.getString("fullname")
                    val email = document.getString("email")
                    val userImg = document.getString("userImg")
                    val user = User(fullname!!, email!!, userImg!!)
                    user.uid = uid!!
                    users.add(user)
                }
                handler.onSuccess(users)
            } else {
                handler.onError(it.exception!!)
            }
        }
    }

    fun storePosts(post: Post,handler:DBPostHandler){
        val referance = database.collection(USER_PATH).document(post.uid).collection(POST_PATH)
        val id = referance.document().id
        post.id = id

        referance.document(post.id).set(post).addOnSuccessListener {
            handler.onSuccess(post)
        }.addOnFailureListener {
            handler.onError(it)
        }
    }

    fun loadPosts(uid: String,handler: DBPostsHandler){
        val reference = database.collection(USER_PATH).document(uid).collection(POST_PATH)
        reference.get().addOnCompleteListener {
            val posts = ArrayList<Post>()
            if (it.isSuccessful){
                for (document in it.result){
                    val id = document.getString("id")
                    val caption = document.getString("caption")
                    val postImg = document.getString("postImg")
                    val fullname = document.getString("fullname")
                    val userImg = document.getString("userImg")
                    val currentDate = document.getString("currentDate")

                    val post = Post(id!!,caption!!,postImg!!)
                    post.uid = uid
                    post.currentDate = currentDate!!
                    post.fullname = fullname!!
                    post.userImg = userImg!!
                    posts.add(post)
                }
                handler.onSuccess(posts)
            }else{
                handler.onError(it.exception!!)
            }
        }
    }

    fun loadFeeds(uid: String,handler: DBPostsHandler){
        val reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH).orderBy("currentDate")
        reference.get().addOnCompleteListener {
            val posts = ArrayList<Post>()
            if (it.isSuccessful){
                for (document in it.result){
                    val id = document.getString("id")
                    val caption = document.getString("caption")
                    val postimg = document.getString("postImg")
                    val fullname = document.getString("fullname")
                    val userImg = document.getString("userImg")
                    val currentDate = document.getString("currentDate")

                    val post = Post(id!!,caption!!,postimg!!)
                    post.uid = uid
                    post.currentDate = currentDate!!
                    post.fullname = fullname!!
                    post.userImg = userImg!!
                    posts.add(post)
                }
                handler.onSuccess(posts)
            }else{
                handler.onError(it.exception!!)
            }
        }
    }

    fun storeFeeds(post: Post,handler:DBPostHandler){
        val reference = database.collection(USER_PATH).document(post.uid).collection(FEED_PATH)

        reference.document(post.id).set(post).addOnSuccessListener {
            handler.onSuccess(post)
        }.addOnFailureListener {
            handler.onError(it)
        }
    }

    fun followUser(me:User,to:User,handler: DBFollowHandler){
        //User(To) is in my following
        database.collection(USER_PATH).document(me.uid).collection(FOLLOWING_PATH).document(to.uid)
            .set(to).addOnSuccessListener {
                //User(Me) is in his/her followers
                database.collection(USER_PATH).document(to.uid).collection(FOLLOWERS_PATH)
                    .document(me.uid)
                    .set(me).addOnSuccessListener {
                        handler.onSuccess(true)
                    }.addOnFailureListener {
                        handler.onError(it)
                    }
            }.addOnFailureListener {
                handler.onError(it)
            }
    }

    fun unFollowUser(me:User,to:User,handler: DBFollowHandler){
        //User(To) is in my following
        database.collection(USER_PATH).document(me.uid).collection(FOLLOWING_PATH).document(to.uid)
            .delete().addOnSuccessListener {
                //User(Me) is in his/her followers
                database.collection(USER_PATH).document(to.uid).collection(FOLLOWERS_PATH)
                    .document(me.uid)
                    .delete().addOnSuccessListener {
                        handler.onSuccess(true)
                    }.addOnFailureListener {
                        handler.onError(it)
                    }
            }.addOnFailureListener {
                handler.onError(it)
            }
    }

}