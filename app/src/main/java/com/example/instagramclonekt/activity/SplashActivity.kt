package com.example.instagramclonekt.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.instagramclonekt.R

/*
   In SplashActivity user can visit to SignInActivity or MainActivity
     */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    val TAG = SplashActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.navigationBarColor = resources.getColor(R.color.bottom)
        setContentView(R.layout.activity_splash)

        initViews()
    }

    private fun initViews() {
        countDownTImer()
    }

    private fun countDownTImer() {
        object : CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                callSignInActivity()
            }
        }.start()
    }

    private fun callSignInActivity() {
        val intent = Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}