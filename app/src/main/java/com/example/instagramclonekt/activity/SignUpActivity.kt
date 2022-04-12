package com.example.instagramclonekt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagramclonekt.R

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
            finish()
        }
        tv_signin.setOnClickListener {
            finish()
        }
    }
}