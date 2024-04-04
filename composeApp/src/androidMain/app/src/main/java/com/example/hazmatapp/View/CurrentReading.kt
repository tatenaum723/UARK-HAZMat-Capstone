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
    private lateinit var cMaxMethane: TextView // Methane max percentage of current reading
    private lateinit var cMaxTemperature: TextView // Max Temperature of current reading
    private lateinit var cNotes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_reading)
        supportActionBar?.title = "Back" // The title displayed at the top of each activity

        // Initializes variables
        cTitle = findViewById(R.id.title)
        cLocation = findViewById(R.id.location_data)
        cDate = findViewById(R.id.date_data)
        cTime = findViewById(R.id.time_data)
        cMaxMethane = findViewById(R.id.max_methane_data)
        cMaxTemperature = findViewById(R.id.max_temperature_data)
        cNotes = findViewById(R.id.notes_data)

    }

    override fun onResume() {
        super.onResume()
        val reading = intent.getParcelableExtra<Reading>("reading") // Reading data passed with intent

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
            cMaxMethane.text = reading.maxMethane
        }
        if (reading != null) {
            cMaxTemperature.text = reading.maxTemperature
        }
        if (reading != null) {
            cNotes.text = reading.notes
        }

    }

}
