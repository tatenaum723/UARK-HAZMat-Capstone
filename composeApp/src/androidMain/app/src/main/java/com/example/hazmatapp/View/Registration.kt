package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hazmatapp.R

class Registration : AppCompatActivity() {

    // Instance variables
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        name = findViewById(R.id.name_input)
        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        confirm = findViewById(R.id.confirm_input)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {

        }
    }
}