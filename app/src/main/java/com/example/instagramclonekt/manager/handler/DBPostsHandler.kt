package com.example.instagramclonekt.manager.handler

import com.example.instagramclonekt.model.Post
import com.example.instagramclonekt.model.User
import java.lang.Exception

interface DBPostsHandler{
    fun onSuccess(posts: ArrayList<Post>)
    fun onError(e: Exception)
}