package com.example.hazmatapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.hazmatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Menu : AppCompatActivity() {

    // Instance variables
    private lateinit var readingButton: Button
    private lateinit var graphButton: Button
    private lateinit var viewButton: Button
    private lateinit var bluetoothButton: Button
    private lateinit var instructionsButton: Button
    private lateinit var username_text: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Initialize buttons
        readingButton = findViewById(R.id.button1)
        graphButton = findViewById(R.id.button2)
        viewButton = findViewById(R.id.button3)
        bluetoothButton = findViewById(R.id.button4)
        instructionsButton = findViewById(R.id.button5)
        username_text = findViewById(R.id.username_text)
        auth = Firebase.auth

        // Retrieve the username from the Intent and sets up the username on the menu screen
        val username = intent.getStringExtra("EMAIL")
        username_text.text = username


        // Button actions
        readingButton.setOnClickListener {
            val intent = Intent(this, NormalReading::class.java)
            startActivity(intent)
        }
        graphButton.setOnClickListener {
            Log.d("Main", "Clicking b2")
        }
        viewButton.setOnClickListener {
            Log.d("Main", "Clicking b3")
        }
        bluetoothButton.setOnClickListener {
            val intent = Intent(this, bluetooth_connection::class.java)
            startActivity(intent)
        }
        instructionsButton.setOnClickListener {
            Log.d("Main", "Clicking b5")
            val intent = Intent(this, ScanningBluetooth::class.java)
            startActivity(intent)
        }
    }
}