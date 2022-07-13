package com.example.instagramclonekt.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.instagramclonekt.manager.handler.AuthHandler
import com.example.instagramclonekt.R
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.FirebaseConfig
import com.example.instagramclonekt.manager.PrefsManager
import com.example.instagramclonekt.manager.handler.DBUserHandler
import com.example.instagramclonekt.model.User
import com.example.instagramclonekt.utils.DeepLink
import com.example.instagramclonekt.utils.Extensions.toast
import java.lang.Exception

/*
   In SignIn Activity user can login using email, password
     */
class SignInActivity : BaseActivity() {
    val TAG = SignInActivity::class.java
    lateinit var et_email: EditText
    lateinit var et_password: EditText
    lateinit var tvInsta: TextView
    lateinit var llBack: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViews()
    }

    fun initViews() {
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        tvInsta = findViewById(R.id.tvInsta)
        llBack = findViewById(R.id.llBack)

        val btn_signin = findViewById<Button>(R.id.b_signin)

        btn_signin.setOnClickListener {
            FirebaseConfig(llBack,tvInsta).updateConfig()
            //val email = et_email.text.toString().trim()
            //val password = et_password.text.toString().trim()
            /*if (email.isNotEmpty() && password.isNotEmpty())
                firebaseSignIn(email, password)*/
            //val str1 = reverse(email)
            //val str2 = reverse(password)
        }

        val tv_signup = findViewById<TextView>(R.id.tv_signin)

        tv_signup.setOnClickListener {
            callSignUpActivity()
        }

        val tvLink = findViewById<TextView>(R.id.tv_link)

        //DeepLink.retrieveLink(intent, tvLink)
        FirebaseConfig(llBack,tvInsta).applyConfig()
    }

    fun firebaseSignIn(email: String, password: String) {
        showLoading(this)
        AuthManager.signIn(email, password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                dismissLoading()
                toast(getString(R.string.str_signin_success))
                storeDeviceTokenToUser()
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signin_failed))
            }

        })
    }

    private fun storeDeviceTokenToUser() {
        val deviceToken = PrefsManager(this).loadDeviceToken()
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.addMyDeviceToken(uid, deviceToken, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                callMainActivity(context)
            }

            override fun onError(e: Exception) {
                callMainActivity(context)
            }

        })
    }

    fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun reverse(string: String):String{
        val chars:CharArray = string.toCharArray()

        var l = 0
        var h = string.length -1
        while (l<h){
            val c = chars[l]
            chars[l] = chars[h]
            chars[h] = c
            l++; h--;
        }
        return String(chars)
    }
}