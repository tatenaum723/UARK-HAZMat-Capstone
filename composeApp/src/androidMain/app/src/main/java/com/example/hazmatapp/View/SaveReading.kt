package com.example.hazmatapp.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hazmatapp.R
import java.util.Calendar

class SaveReading : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var location: EditText
    private lateinit var time: Button
    private lateinit var date: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_reading)

        name = findViewById(R.id.name_input)
        location = findViewById(R.id.location_input)
        time = findViewById(R.id.event_time)
        date = findViewById(R.id.event_date)

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


        // Buttons
        time.setOnClickListener { // Used to get the current time(hour and minute) calling the Calendar class
            val currentTime = Calendar.getInstance()
            val currentHour = currentTime.get(Calendar.HOUR) // Current hour
            val currentMinute = currentTime.get(Calendar.MINUTE) // Current min

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ // Set the data in the format (HH:MM)
                    _, hour, minute ->
                time.text = "$hour:$minute"
            }, currentHour, currentMinute, false).show()
        }
    }
}