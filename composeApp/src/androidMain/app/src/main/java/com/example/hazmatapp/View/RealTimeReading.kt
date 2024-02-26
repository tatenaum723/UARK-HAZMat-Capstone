package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.hazmatapp.R

class RealTimeReading : AppCompatActivity() {

    // Instance variables
    private lateinit var lelBar: ProgressBar
    private lateinit var lelNum: TextView
    private lateinit var volBar: ProgressBar
    private lateinit var volNum: TextView
    private lateinit var startButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time_reading)

        // Initializes the variables
        lelBar = findViewById(R.id.lel_bar)
        lelNum = findViewById(R.id.lel_bar_number)
        volBar = findViewById(R.id.vol_bar)
        volNum = findViewById(R.id.vol_bar_number)
        startButton = findViewById(R.id.start_button)

        // On-click methods for buttons
        startButton.setOnClickListener {
            getData()
        }

    }

    private fun getData() {
        TODO("Not yet implemented")
    }
}