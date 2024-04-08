package com.example.newsfeed

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = resources.getColor(R.color.my_primary)
        window.navigationBarColor = resources.getColor(R.color.my_primary)
        val backgroundImg : LinearLayout = findViewById(R.id.logo)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        backgroundImg.startAnimation(slideAnimation)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

    }
}