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

        // Launches the MainActivity after showing the splash screen for a short time
        Handler().postDelayed({
            startActivity(Intent(this, EntryMenu::class.java))
            finish()
        }, SPLASH_DELAY)
    }
    companion object { // Sets delay time of splash screen
        private const val SPLASH_DELAY: Long = 3000 // Splash screen delay in milliseconds
    }
}