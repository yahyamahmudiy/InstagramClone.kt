package com.example.instagramclonekt.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagramclonekt.manager.handler.AuthHandler
import com.example.instagramclonekt.R
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.DatabaseManager
import com.example.instagramclonekt.manager.PrefsManager
import com.example.instagramclonekt.manager.handler.DBUserHandler
import com.example.instagramclonekt.model.User
import com.example.instagramclonekt.utils.Extensions.toast

/*
   In SignUp Activity user can sign up using fullname, email, password
     */
class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.toString()
    lateinit var et_fullname:EditText
    lateinit var et_email:EditText
    lateinit var et_password:EditText
    lateinit var et_confirm:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_confirm = findViewById(R.id.et_confirm)
        val b_signup:Button = findViewById(R.id.b_signup)
        val tv_signin:TextView = findViewById(R.id.tv_signin)

        b_signup.setOnClickListener {
            val fullname = et_fullname.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            val deviceToken = PrefsManager(this).loadDeviceToken()!!
            if (email.isNotEmpty() && password.isNotEmpty()){
                val user = User(fullname,email,password,"")
                user.device_tokens.add(deviceToken)
                firebaseSignUp(user)
            }
        }
        tv_signin.setOnClickListener {
            finish()
        }
    }

    fun firebaseSignUp(user: User){
        showLoading(this)
        AuthManager.signUp(user.email,user.password,object : AuthHandler {
            override fun onSuccess(uid: String) {
                dismissLoading()
                user.uid = uid
                storeUserToDB(user)
                toast("Signed up successfully")
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signup_failed))
            }

        })
    }

    private fun storeUserToDB(user: User){

        DatabaseManager.storeUser(user, object: DBUserHandler {
            override fun onSuccess(user: User?) {
                dismissLoading()
                callMainActivity(this@SignUpActivity)
            }

            override fun onError(e: Exception) {

            }
        })
    }

}