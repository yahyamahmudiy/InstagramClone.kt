package com.example.instagramclonekt.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.initialfirebaseapp.manager.AuthHandler
import com.example.instagramclonekt.R
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.utils.Extensions.toast
import java.lang.Exception

/*
   In SignIn Activity user can login using email, password
     */
class SignInActivity : BaseActivity() {
    val TAG = SignInActivity::class.java
    lateinit var et_email:EditText
    lateinit var et_password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViews()
    }

     fun initViews() {
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        val btn_signin = findViewById<Button>(R.id.b_signin)
        btn_signin.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty())
                firebaseSignIn(email,password)
        }
        val tv_signup = findViewById<TextView>(R.id.tv_signin)
        tv_signup.setOnClickListener {
            callSignUpActivity()
        }
     }

     fun firebaseSignIn(email:String,password:String){
         showLoading(this)
         AuthManager.signIn(email,password,object :AuthHandler{
             override fun onSuccess(uid: String) {
                 dismissLoading()
                 toast(getString(R.string.str_signin_success))
                 callMainActivity(context)
             }

             override fun onError(exception: Exception?) {
                 dismissLoading()
                 toast(getString(R.string.str_signin_failed))
             }

         })
     }

     fun callSignUpActivity() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
     }
}