package com.example.instagramclonekt.manager

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.example.instagramclonekt.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View
import androidx.annotation.ColorInt

class FirebaseConfig(var ll: LinearLayout, var tv: TextView) {
    var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 68
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun applyConfig() {
        val bg_color = remoteConfig.getString("main_background_color")
        val bg_color2 = remoteConfig.getString("second_background_color")
        val font_size = remoteConfig.getLong("welcome_font_size")
        val text = remoteConfig.getString("welcome_text")

        Log.d("@@@", "applyConfig: $bg_color")
        Log.d("@@@", "applyConfig: $bg_color2")
        Log.d("@@@", "applyConfig: $font_size")
        Log.d("@@@", "applyConfig: $text")

        this.ll.backgroundGradientDrawable(Color.parseColor(bg_color), Color.parseColor(bg_color2))
        this.tv.text = text
        this.tv.textSize = font_size.toFloat()

    }
    fun View.backgroundGradientDrawable(@ColorInt startColor: Int, @ColorInt endColor: Int): Unit {
        val h = this.height.toFloat()
        val shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.paint.shader =
            LinearGradient(0f, 0f, 0f, h, startColor, endColor, Shader.TileMode.REPEAT)
        this.background = shapeDrawable
    }

    fun updateConfig() {
        remoteConfig.fetch(0).addOnCompleteListener {
            if (it.isSuccessful) {
                remoteConfig.activate()
                applyConfig()
            } else {
                Log.d("@@@", "updateConfig: Fetch Failed")
            }
        }
    }
}