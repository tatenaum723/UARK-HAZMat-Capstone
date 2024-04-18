package com.example.hazmatapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.R
import com.example.hazmatapp.Util.EmulatorDataListener
import com.example.hazmatapp.Util.EmulatorUtil
import kotlin.properties.Delegates

class RealTimeReading : AppCompatActivity(), EmulatorDataListener {

    // Instance variables
    private lateinit var title: TextView
    private lateinit var timer: TextView
    private lateinit var methaneBar: ProgressBar // Methane progress bar
    private lateinit var methaneNum: TextView // Number inside progress bar
    private lateinit var tempBar: ProgressBar // Temperature progress bar
    private lateinit var tempNum: TextView // Number inside progress bar
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var emul: EmulatorUtil
    private var methaneData: MutableList<Pair<Int, Double>> = mutableListOf()
    private var tempData: MutableList<Pair<Int, Double>> = mutableListOf()
    private var maxMethaneData = 0.0 // The max methane percentage from a reading
    private var maxTemperatureData = 0.0 // The max temperature from a reading

    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_time_reading)
        supportActionBar?.title = "Back" // The tittle display at the top of each activity

        // Initializes the variables
        title = findViewById(R.id.title)
        timer = findViewById(R.id.time)
        methaneBar = findViewById(R.id.lel_bar)
        methaneBar.progress = 0 // Sets the starting value of the progress bar
        methaneNum = findViewById(R.id.lel_number)
        tempBar = findViewById(R.id.vol_bar)
        tempBar.progress = 0 // Sets the starting value of the progress bar
        tempNum = findViewById(R.id.vol_number)
        startButton = findViewById(R.id.start_button)
        resetButton = findViewById(R.id.reset_button)
        saveButton = findViewById(R.id.save_button)
        emul = EmulatorUtil()
        methaneData = mutableListOf()
        tempData = mutableListOf()
        emul.setListener(this) // This activity will get updates from the listener

        // On-click methods for buttons
        startButton.setOnClickListener {
            startReading()
        }
        resetButton.setOnClickListener {
            resetReading()
        }
        saveButton.setOnClickListener {
            saveReading()
        }

    }

    private fun resetReading(){
        emul.resetData()
        title.text = "Real-Time Reading"
        timer.text = ""
        methaneBar.progress = 0
        tempBar.progress = 0
        methaneNum.text = "0"
        tempNum.text = "0"
    }

    private fun saveReading() { // Sends the data to the SaveReading class to create record
        if (methaneData.isNotEmpty() && tempData.isNotEmpty()) {
            val intent = Intent(this, SaveReading::class.java)
            intent.putExtra("methaneData", ArrayList(methaneData))
            intent.putExtra("tempData", ArrayList(tempData))
            intent.putExtra("maxMethane", maxMethaneData)
            intent.putExtra("maxTemperature", maxTemperatureData)

            startActivity(intent)
            resetReading() // Reset the numbers on the screen
        } else {
            displayMessage("No data to save.")
        }
    }

    private fun startReading() { // Starts the emulator if it is not running already
        if(isRunning){
            emul.stop() // Stops reading
            startButton.text = "Start"
            title.text = "Done"

        }
        else if(methaneData.isNotEmpty() && tempData.isNotEmpty()){
            displayMessage("SAVE OR RESET CURRENT DATA")
        }
        else{
            emul.startEmulation() // Starts reading
            startButton.text = "Stop"
        }
    }

    // Updates the UI when the emulator generates the data
    override fun onDataUpdate(methanePercent: Double, tempFahrenheit: Double) {
        runOnUiThread {
            methaneBar.progress = methanePercent.toInt()
            tempBar.progress = tempFahrenheit.toInt()
            methaneNum.text = String.format("%.2f", methanePercent) + "%"
            tempNum.text = String.format("%.2f", tempFahrenheit) + "F"
        }
    }

    // After the RTR is over, it gets the data from the emulator class to the class here thanks to the listener
    override fun onDoneReading(
        methaneReadings: MutableList<Pair<Int, Double>>,
        tempReadings: MutableList<Pair<Int, Double>>,
        maxMethane: Double,
        maxTemperature: Double
    ) {
        methaneData = methaneReadings
        tempData = tempReadings
        maxMethaneData = maxMethane
        maxTemperatureData = maxTemperature

    }

    override fun onRunning(flag: Boolean) {
        isRunning = flag
    }

    override fun onTimeUpdate(time: Int) { // Updates the current time of the reading
        runOnUiThread {
            title.text = "Running.."
            timer.text = "Time: $time"
        }
    }

    private fun displayMessage(message: String){ // Used to display Toast messages
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, message, duration)
        toast.show()
    }
}