package com.example.hazmatapp.View

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.hazmatapp.Model.Reading
import com.example.hazmatapp.R
import com.example.hazmatapp.Util.DialogUtil
import com.example.hazmatapp.Util.DialogListener
import com.example.hazmatapp.ViewModel.CurrentReadingViewModel

class CurrentReading : AppCompatActivity(), DialogListener {

    private lateinit var cTitle: TextView
    private lateinit var cLocation: TextView
    private lateinit var cDate: TextView
    private lateinit var cTime: TextView
    private lateinit var cMaxMethane: TextView // Methane max percentage of current reading
    private lateinit var cMaxTemperature: TextView // Max Temperature of current reading
    private lateinit var cNotes: TextView
    private lateinit var deleteButton: Button
    private lateinit var viewModel: CurrentReadingViewModel // Reference to the view model class
    private lateinit var deletePopUp: DialogUtil
    private var deleteAnswer = false


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
        deleteButton = findViewById(R.id.delete_button)
        viewModel = ViewModelProvider(this)[CurrentReadingViewModel::class.java]
        deletePopUp = DialogUtil("Are you sure that you want to delete the reading?", "Yes", "No")

    }

    override fun onResume() {
        super.onResume()
        val reading = intent.getParcelableExtra<Reading>("reading") // Reading data passed with intent
        deletePopUp.setListener(this)

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
            cMaxMethane.text = reading.maxMethane + "%"
        }
        if (reading != null) {
            cMaxTemperature.text = reading.maxTemperature + "F"
        }
        if (reading != null) {
            cNotes.text = reading.notes
        }
        Log.d("CR", "$deleteAnswer")
        deleteButton.setOnClickListener {
            deletePopUp.show(supportFragmentManager, "DELETE_DIALOG")
        }

    }
    private fun delete(reading: Reading){ // Sends the data of the current reading to the viewmodel to be deleted
        viewModel.delete(reading)
    }

    override fun onYes(flag: Boolean) { // If the user presses yes in the pop up the reading is deleted
        if (flag) {
            val reading = intent.getParcelableExtra<Reading>("reading") // Reading data passed with intent
            if (reading != null) {
                delete(reading)
            }
            finish() // Finishes the activity
        }
    }

}
