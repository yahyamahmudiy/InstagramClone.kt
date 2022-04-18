package com.example.instagramclonekt.manager.handler

import com.example.instagramclonekt.model.Post
import com.example.instagramclonekt.model.User
import java.lang.Exception

interface DBFollowHandler{
    fun onSuccess(isFollowed: Boolean)
    fun onError(e: Exception)
}