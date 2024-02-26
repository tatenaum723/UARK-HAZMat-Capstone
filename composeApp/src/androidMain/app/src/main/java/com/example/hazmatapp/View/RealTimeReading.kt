package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.hazmatapp.R
import com.example.hazmatapp.Util.EmulatorUtil

class RealTimeReading : AppCompatActivity() {

    // Instance variables
    private lateinit var lelBar: ProgressBar
    private lateinit var lelNum: TextView
    private lateinit var volBar: ProgressBar
    private lateinit var volNum: TextView
    private lateinit var startButton: Button
    private lateinit var emul: EmulatorUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time_reading)

        // Initializes the variables
        lelBar = findViewById(R.id.lel_bar)
        lelNum = findViewById(R.id.lel_text)
        volBar = findViewById(R.id.vol_bar)
        volNum = findViewById(R.id.vol_text)
        startButton = findViewById(R.id.start_button)
        emul = EmulatorUtil()

        // On-click methods for buttons
        startButton.setOnClickListener {
            getData()
        }

    }

    private fun getData() {
        emul.startEmulation(60)
    }
}