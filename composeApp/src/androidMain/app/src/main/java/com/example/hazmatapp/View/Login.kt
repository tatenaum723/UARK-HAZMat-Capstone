package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hazmatapp.R

class Login : AppCompatActivity() {

    // Instance variables
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_button)

        // OnClick methods
        loginButton.setOnClickListener {

        }

    }
}