package com.example.hazmatapp.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hazmatapp.Model.Reading
import com.example.hazmatapp.R
import com.example.hazmatapp.ViewModel.SaveReadingViewModel
import java.util.Calendar

class SaveReading : AppCompatActivity() {
    // Instance variables
    private lateinit var name: EditText
    private lateinit var location: EditText
    private lateinit var notes: EditText
    private lateinit var time: Button
    private lateinit var date: Button
    private lateinit var submit: Button
    private var methanePercentage: MutableList<Pair<Int, Double>> = mutableListOf()
    private var temperature: MutableList<Pair<Int, Double>> = mutableListOf()
    private var maxMethane = 0.0
    private var maxTemperature = 0.0
    private lateinit var viewModel: SaveReadingViewModel // Reference to the view model class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_reading)
        supportActionBar?.title = "Back" // The title displayed at the top of each activity

        // Initializes variables
        name = findViewById(R.id.name_input)
        location = findViewById(R.id.location_input)
        notes = findViewById(R.id.notes_input)
        time = findViewById(R.id.event_time)
        date = findViewById(R.id.event_date)
        submit = findViewById(R.id.submit_button)

        // Gets the data passed as extras in the intent of RealTimeReading
        methanePercentage = intent.getSerializableExtra("methaneData") as ArrayList<Pair<Int, Double>>
        temperature = intent.getSerializableExtra("tempData") as ArrayList<Pair<Int, Double>>
        maxMethane = intent.getSerializableExtra("maxMethane") as Double
        maxTemperature = intent.getSerializableExtra("maxTemperature") as Double


        // Initializes viewmodel
        viewModel = ViewModelProvider(this)[SaveReadingViewModel::class.java]

        // Buttons
        date.setOnClickListener {// Used to get the current data(month, day, year) calling the Calendar class
            val currentDate = Calendar.getInstance()
            val currentMonth = currentDate.get(Calendar.MONTH) // Current month
            val currentDay = currentDate.get(Calendar.DAY_OF_MONTH) // Current day
            val currentYear = currentDate.get(Calendar.YEAR) // Current year

            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val formattedDate = String.format("%02d-%02d-%d", month + 1, day, year) // Sets the data in the format (MM:DD:YYYY)
                date.text = formattedDate
            }, currentYear, currentMonth, currentDay).show()
        }
        time.setOnClickListener { // Used to get the current time(hour and minute) calling the Calendar class
            val currentTime = Calendar.getInstance()
            val currentHour = currentTime.get(Calendar.HOUR_OF_DAY) // Current hour
            val currentMinute = currentTime.get(Calendar.MINUTE) // Current min

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
                val formattedTime = String.format("%02d:%02d", hour, minute) // Set the data in the format (HH:MM)
                time.text = formattedTime
            }, currentHour, currentMinute, false).show()
        }
        submit.setOnClickListener {
            createRecord() // Used to create a new record with the readings
        }
    }

    private fun createRecord() {
        val setName = name.text.toString()
        val setLocation = location.text.toString()
        var setNotes = notes.text.toString()
        val setTime = time.text.toString()
        val setDate = date.text.toString()
        val setMethanePercentage = methanePercentage.toString()
        val temperature = temperature.toString() // Will need to get data list of temp/time like above
        val setMaxMethane = "%.2f".format(maxMethane)
        val setMaxTemperature = "%.2f".format(maxTemperature)
        val id = ""

        if(setName.isNullOrBlank() || setLocation.isNullOrBlank() || setTime.isNullOrBlank() || setDate.isNullOrBlank()){
            displayMessage("Enter all required fields")
        }
        else{
            val newReading = Reading(setName, setLocation, setNotes, setDate, setTime, setMethanePercentage, temperature, setMaxMethane, setMaxTemperature, id)
            viewModel.create(newReading) // Passes the new object to the viewmodel to get stored in the database
            displayMessage("Saved")
            finish() // Closes the activity
        }
    }

    private fun displayMessage(message: String){ // Used to display Toast messages
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, message, duration)
        toast.show()
    }
}