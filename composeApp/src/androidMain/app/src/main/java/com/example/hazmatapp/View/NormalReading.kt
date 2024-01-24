package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import com.example.hazmatapp.R

class NormalReading : AppCompatActivity() {

    // Instance variables
    private lateinit var bar: ProgressBar
    private lateinit var startButton: Button
    private lateinit var saveButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_reading)

        // Initializes the variables
        bar = findViewById(R.id.progress_bar)
        startButton = findViewById(R.id.start_button)
        saveButton = findViewById(R.id.save_button)

        // Buttons
        startButton.setOnClickListener {

        }
        saveButton.setOnClickListener {

        }
    }
}