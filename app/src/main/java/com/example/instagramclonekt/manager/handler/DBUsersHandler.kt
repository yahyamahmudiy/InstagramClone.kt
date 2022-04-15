package com.example.instagramclonekt.manager.handler

import com.example.instagramclonekt.model.User
import java.lang.Exception

interface DBUsersHandler {
    fun onSuccess(users: ArrayList<User>)
    fun onError(e: Exception)
}