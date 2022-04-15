package com.example.instagramclonekt.manager.handler

import com.example.instagramclonekt.model.User
import java.lang.Exception

interface DBUserHandler {
    fun onSuccess(user: User? = null)
    fun onError(e: Exception)
}