package com.example.hazmatapp.View

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.Model.Reading
import com.example.hazmatapp.R

class CurrentReading : AppCompatActivity() {

    private lateinit var cTitle: TextView
    private lateinit var cLocation: TextView
    private lateinit var cDate: TextView
    private lateinit var cTime: TextView
    private lateinit var cLEL: TextView
    private lateinit var cVOL: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_reading)
        supportActionBar?.title = "Back" // The title displayed at the top of each activity

        // Initializes variables
        cTitle = findViewById(R.id.title)
        cLocation = findViewById(R.id.location_data)
        cDate = findViewById(R.id.date_data)
        cTime = findViewById(R.id.time_data)
        cLEL = findViewById(R.id.max_lel_data)
        cVOL = findViewById(R.id.max_vol_data)

    }

    override fun onResume() {
        super.onResume()
        val reading = intent.getParcelableExtra<Reading>("reading") // Reading data passed with intent
        //val lelPercentageMax = maxLEL(reading)

        // Sets the data from the current reading into the view elements
        if (reading != null) {
            cTitle.text = reading.name
        }
        if (reading != null) {
            cLocation.text = reading.location
        }
        if (reading != null) {
            cDate.text = reading.date
        }
        if (reading != null) {
            cTime.text = reading.time
        }
        if (reading != null) {
            //LEL.text = lelPercentageMax.toString()
        }

    }

    private fun maxLEL(reading: Reading?): Double {
        var maxLEL = 0.0
        val lelDataString = reading?.methanePercentage
        if (lelDataString != null) {
            val lelData = lelDataString.split(",").map {
                val (key, value) = it.split(":")
                Pair(key.toInt(), value.toDouble())
            }
            for (pair in lelData) {
                if (pair.second > maxLEL) {
                    maxLEL = pair.second
                }
            }
        }
        return maxLEL
    }

}
