package com.example.hazmatapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.hazmatapp.View.NormalReading
import com.example.hazmatapp.R

class MainActivity : AppCompatActivity() {

    // Instance variables
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)

        // Button actions
        button1.setOnClickListener {
            val intent = Intent(this, NormalReading::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener {
            Log.d("Main", "Clicking b2")
        }
        button3.setOnClickListener {
            Log.d("Main", "Clicking b3")
        }
        button4.setOnClickListener {
            Log.d("Main", "Clicking b4")
        }
        button5.setOnClickListener {
            Log.d("Main", "Clicking b5")
        }
    }
}