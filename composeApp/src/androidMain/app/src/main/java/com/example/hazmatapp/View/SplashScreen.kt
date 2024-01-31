package com.example.hazmatapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.hazmatapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, Menu::class.java))
            finish()
        }, SPLASH_DELAY)
    }
    companion object {
        private const val SPLASH_DELAY: Long = 3000 // Splash screen delay in milliseconds
    }
}