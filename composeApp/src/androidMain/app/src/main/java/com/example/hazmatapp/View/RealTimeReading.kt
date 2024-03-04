package com.example.hazmatapp.View

import EmulatorDataListener
import EmulatorUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
    private var lelData: MutableList<Pair<Int, Double>> = mutableListOf()
    private var volData: MutableList<Pair<Int, Double>> = mutableListOf()
    private var isRunning = false


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
        lelData = mutableListOf()
        volData = mutableListOf()

        // "Hey, I'm interested in receiving updates from you. Whenever you have new data available, please let me know by calling the onDataUpdate method."
        emul.setListener(this)

        // On-click methods for buttons
        startButton.setOnClickListener {
            getData()
        }
        resetButton.setOnClickListener {
            emul.resetData()
            lelNum.text = "0"
            volNum.text = "0"
        }
        saveButton.setOnClickListener {
            saveData()
        }

    }

    private fun saveData() { // Sends the data to the SaveReading class to create record
        if (lelData.isNotEmpty() && volData.isNotEmpty()) {
            val intent = Intent(this, SaveReading::class.java)
            intent.putExtra("lelData", ArrayList(lelData))
            intent.putExtra("volData", ArrayList(volData))
            startActivity(intent)
        } else {
            displayMessage("No data to save.")
        }
    }


    private fun getData() { // Starts the emulator if it is not running already
        if(isRunning){
            displayMessage("Reading in process!")
        }
        else if(lelData.isNotEmpty() && volData.isNotEmpty()){
            displayMessage("Save or Reset First!")
        }
        else{
            emul.startEmulation(15)
        }
    }

    // Updates the UI when the emulalator generates the data
    override fun onDataUpdate(lel: Double, vol: Double) {
        runOnUiThread {
            lelNum.text = "$lel"
            volNum.text = "$vol"
        }
    }

    // After the RTR is over, it gets the lists with data from the emulator class to the class here thanks to the listener
    override fun onDoneReading(lelReadings: MutableList<Pair<Int, Double>>, volReadings: MutableList<Pair<Int, Double>>) {
        lelData = lelReadings
        volData = volReadings
        Log.d("RTR", "$lelData")
        Log.d("RTR", "$volData")
    }

    override fun onRunning(flag: Boolean) {
        isRunning = flag
    }

    private fun displayMessage(message: String){ // Used to display Toast messages
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, message, duration)
        toast.show()
    }
}