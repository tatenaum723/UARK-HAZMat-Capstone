package com.example.hazmatapp.View

import EmulatorDataListener
import EmulatorUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.hazmatapp.R

class RealTimeReading : AppCompatActivity(), EmulatorDataListener{

    // Instance variables
    private lateinit var lelBar: ProgressBar
    private lateinit var lelNum: TextView
    private lateinit var volBar: ProgressBar
    private lateinit var volNum: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var emul: EmulatorUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time_reading)

        // Initializes the variables
        lelBar = findViewById(R.id.lel_bar)
        lelNum = findViewById(R.id.lel_number)
        volBar = findViewById(R.id.vol_bar)
        volNum = findViewById(R.id.vol_number)
        startButton = findViewById(R.id.start_button)
        resetButton = findViewById(R.id.reset_button)
        saveButton = findViewById(R.id.save_button)
        emul = EmulatorUtil()

        // "Hey, I'm interested in receiving updates from you. Whenever you have new data available, please let me know by calling the onDataUpdate method."
        emul.setListener(this)

        // On-click methods for buttons
        startButton.setOnClickListener {
            getData()
        }

    }

    private fun getData() {
        emul.startEmulation(20)
    }

    // Updates the UI when the emulalator generates the data
    override fun onDataUpdate(lel: Double, vol: Double) {
        runOnUiThread {
            lelNum.text = "$lel"
            volNum.text = "$vol"
        }
    }
}