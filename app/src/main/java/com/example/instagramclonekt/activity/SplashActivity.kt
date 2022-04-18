package com.example.instagramclonekt.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.instagramclonekt.R
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.manager.PrefsManager
import com.example.instagramclonekt.utils.Logger
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

/*
   In SplashActivity user can visit to SignInActivity or MainActivity
     */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    val TAG = javaClass.simpleName.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.navigationBarColor = resources.getColor(R.color.white)
        setContentView(R.layout.activity_splash)

        initViews()
    }

    private fun initViews() {
        countDownTimer()
        loadFCMToken()
    }

    fun countDownTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                if (AuthManager.isSignedIn()){
                    callMainActivity(this@SplashActivity)
                }else{
                    callSignInActivity(this@SplashActivity)
                }

            }
        }.start()
    }

     fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logger.d(TAG, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            // Save it in locally to use later
            val token = task.result
            Logger.d("@@@", token.toString())
            PrefsManager(this).storeDeviceToken(token.toString())
        })
    }

}