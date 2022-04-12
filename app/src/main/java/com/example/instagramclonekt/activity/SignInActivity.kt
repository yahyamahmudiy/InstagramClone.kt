package com.example.instagramclonekt.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagramclonekt.R

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
            callMainActivity()
        }
        val tv_signup = findViewById<TextView>(R.id.tv_signin)
        tv_signup.setOnClickListener {
            callSignUpActivity()
        }
     }

     fun callSignUpActivity() {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
     }

     fun callMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
     }

}