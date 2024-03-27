package com.example.hazmatapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hazmatapp.R

// The first activity that shows up after the splash screen. It shows the Login/ Registration Menu
class EntryMenu : AppCompatActivity() {

    // Instance variables
    private lateinit var login: Button
    private lateinit var registration: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_menu)

        // Initializes variables
        login = findViewById(R.id.login_button)
        registration = findViewById(R.id.register_button)

        // OnClick methods
        login.setOnClickListener {
            intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        registration.setOnClickListener { // Launches the Registration activity when the button is pressed
            intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
}
