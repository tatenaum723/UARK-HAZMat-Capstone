package com.example.hazmatapp.View


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.R
import com.example.hazmatapp.Util.EmulatorDataListener
import com.example.hazmatapp.Util.EmulatorUtil
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import java.util.Collections

class GraphCapture : AppCompatActivity(), EmulatorDataListener {

    private lateinit var chart1: LineChart
    private lateinit var chart2: LineChart
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var emul: EmulatorUtil
    private var methaneData: MutableList<Pair<Int, Double>> = mutableListOf()
    private var tempData: MutableList<Pair<Int, Double>> = mutableListOf()
    private var maxMethaneData = 0.0 // The max methane percentage from a reading
    private var maxTemperatureData = 0.0 // The max temperature from a reading
    private var isRunning = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_capture)
        supportActionBar?.title = "Back" // The tittle display at the top of each activity

        // Initialize the variables
        startButton = findViewById(R.id.start_button)
        resetButton = findViewById(R.id.reset_button)
        saveButton = findViewById(R.id.save_button)
        emul = EmulatorUtil()
        methaneData = mutableListOf()
        tempData = mutableListOf()

        // Initializes both graphs
        initGraphs()


        // "Hey, I'm interested in receiving updates from you. Whenever you have new data available, please let me know by calling the onDataUpdate method."
        emul.setListener(this)

        // On-click methods for buttons
        startButton.setOnClickListener {
            startReading()
        }
        resetButton.setOnClickListener {
            resetData()
        }
        saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun initGraphs() {
        // Graph 1
        chart1 = findViewById(R.id.chart1)
        chart1.description.isEnabled = true
        chart1.description.text = "Time"
        chart1.description.textColor = Color.WHITE
        chart1.description.textSize = 12f
        chart1.description.xOffset= 135f
        chart1.description.yOffset=-30f
        chart1.setTouchEnabled(true)
        chart1.dragDecelerationFrictionCoef = 0.9f
        chart1.isDragEnabled = true
        chart1.setScaleEnabled(true)
        chart1.setDrawGridBackground(false)
        chart1.isHighlightPerDragEnabled = true
        chart1.setBackgroundColor(getResources().getColor(R.color.grey_blue))
        chart1.setViewPortOffsets(100f, 100f, 100f, 100f) // Add padding

        val l1 = chart1.legend
        l1.isEnabled = false

        val xAxis1 = chart1.xAxis
        xAxis1.position = XAxis.XAxisPosition.BOTTOM
        xAxis1.textSize = 10f
        xAxis1.setDrawAxisLine(false)
        xAxis1.setDrawGridLines(true)
        xAxis1.setDrawLabels(true)
        xAxis1.textColor = Color.WHITE
        xAxis1.setCenterAxisLabels(true)
        xAxis1.granularity = 1f

        val leftAxis1 = chart1.axisLeft
        leftAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis1.textColor = Color.WHITE // Set axis label color to white
        leftAxis1.setDrawGridLines(true)
        leftAxis1.gridColor = Color.WHITE // Set grid line color to white
        leftAxis1.isGranularityEnabled = true
        leftAxis1.axisMinimum = 0f
        leftAxis1.axisMaximum = 101f
        leftAxis1.yOffset = -9f
        leftAxis1.textSize = 10f // Increase text size
        leftAxis1.setDrawLabels(true)

        val rightAxis1 = chart1.axisRight
        rightAxis1.isEnabled = false

        // Graph 2
        chart2 = findViewById(R.id.chart2)
        chart2.description.isEnabled = true
        chart2.description.text = "Time"
        chart2.description.textColor = Color.WHITE
        chart2.description.textSize = 12f
        chart2.description.xOffset= 135f
        chart2.description.yOffset=-30f
        chart2.setTouchEnabled(true)
        chart2.dragDecelerationFrictionCoef = 0.9f
        chart2.isDragEnabled = true
        chart2.setScaleEnabled(true)
        chart2.setDrawGridBackground(false)
        chart2.isHighlightPerDragEnabled = true
        chart2.setBackgroundColor(getResources().getColor(R.color.grey_blue))
        chart2.setViewPortOffsets(100f, 100f, 100f, 100f) // Add padding

        val l2 = chart2.legend
        l2.isEnabled = false

        val xAxis2 = chart2.xAxis
        xAxis2.position = XAxis.XAxisPosition.BOTTOM
        xAxis2.textSize = 10f
        xAxis2.setDrawAxisLine(false)
        xAxis2.setDrawGridLines(false)
        xAxis2.textColor = Color.WHITE
        xAxis2.setCenterAxisLabels(true)
        xAxis2.granularity = 1f

        val leftAxis2 = chart2.axisLeft
        leftAxis2.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis2.textColor = Color.WHITE
        leftAxis2.setDrawGridLines(true)
        leftAxis2.gridColor = Color.WHITE
        leftAxis2.isGranularityEnabled = true
        leftAxis2.axisMinimum = 32f
        leftAxis2.axisMaximum = 140f
        leftAxis2.yOffset = -9f
        leftAxis2.textSize = 10f
        leftAxis2.setDrawLabels(true)

        val rightAxis2 = chart2.axisRight
        rightAxis2.isEnabled = false
    }


    private fun resetData() {
        // Stop the emulator if it's running
        if (emul.isRunning) {
            emul.stop()
        }
        startButton.setBackgroundResource(R.drawable.blue_button) // Changes color of start button when reading stops
        resetButton.setBackgroundResource(R.drawable.main_menu_buttons) // Changes color of reset button when reading stops
        saveButton.setBackgroundResource(R.drawable.main_menu_buttons) // Changes color of save button when reading stops

        // Create a new instance of EmulatorUtil
        emul = EmulatorUtil()
        emul.setListener(this)

        // Clear the data lists
        methaneData.clear()
        tempData.clear()

        // Clear the charts
        chart1.clear()
        chart2.clear()

        // Reset the chart data to null to ensure no old data is being held.
        chart1.data = null
        chart2.data = null

        // Reinitialize the charts to their default state
        initGraphs()

        // Reset the isRunning flag and the startButton text
        isRunning = false
        startButton.text = "Start"

        // Since you're modifying UI elements, you might want to ensure this is run on the UI thread,
        // especially if resetData() could be called from a non-UI thread at any point.
        runOnUiThread {
            chart1.invalidate() // Refreshes the chart
            chart2.invalidate()
        }
    }


    private fun saveData() {
        if (methaneData.isNotEmpty() && tempData.isNotEmpty()) {
            val intent = Intent(this, SaveReading::class.java)
            intent.putExtra("methaneData", ArrayList(methaneData))
            intent.putExtra("tempData", ArrayList(tempData))
            intent.putExtra("maxMethane", maxMethaneData)
            intent.putExtra("maxTemperature", maxTemperatureData)
            startActivity(intent)
            resetData()
        } else {
            displayMessage("No data to save.")
        }
    }

    private fun startReading() { // Starts the emulator if it is not running already
        if(isRunning){
            emul.stop() // Stops reading
            startButton.text = "Start"
            startButton.setBackgroundResource(R.drawable.main_menu_buttons) // Changes color of start button when reading stops
            resetButton.setBackgroundResource(R.drawable.blue_button) // Changes color of reset button when reading stops
            saveButton.setBackgroundResource(R.drawable.blue_button) // Changes color of save button when reading stops

        }
        else if(methaneData.isNotEmpty() && tempData.isNotEmpty()){
            displayMessage("Save or Reset data")
        }
        else{
            emul.startEmulation() // Starts reading
            startButton.text = "Stop"
        }
    }

    // Updates the UI when the emulator generates the data
    override fun onDataUpdate(methanePercent: Double, tempFahrenheit: Double) {
        runOnUiThread {
            // Update the UI with the new data
            methaneData.add(Pair(methaneData.size, methanePercent)) // Add new methane data
            tempData.add(Pair(tempData.size, tempFahrenheit)) // Add new temp data

            // Update the datasets with new data points
            updateGraphData(chart1, methaneData)
            updateGraphData(chart2, tempData)
        }
    }

    // Function to update the graph with new data
    private fun updateGraphData(chart: LineChart, data: MutableList<Pair<Int, Double>>) {
        val values = ArrayList<Entry>()

        // Find the maximum Int value
        var maxInt = Int.MIN_VALUE
        data.forEach { pair ->
            maxInt = maxOf(maxInt, pair.first)
        }

        // Set the view range on the graph to 5
        chart.setVisibleXRangeMaximum(5f)
        // Move the view to the last 5 data points
        chart.moveViewToX(data.size.toFloat() - 5)

        // Convert data to Entry objects
        data.forEachIndexed { index, pair ->
            values.add(Entry(pair.first.toFloat(), pair.second.toFloat()))
        }

        // Sort the entries by x value
        Collections.sort(values, EntryXComparator())

        // Create a dataset with the new data
        val dataSet = LineDataSet(values, "DataSet")
        dataSet.lineWidth = 3f
        dataSet.setDrawCircles(true)
        dataSet.fillAlpha = 65
        dataSet.color = getResources().getColor(R.color.bright_light_blue)
        dataSet.setDrawCircleHole(false)
        dataSet.setCircleColors(Color.WHITE)

        // Create a data object with the data sets
        val lineData = LineData(dataSet)

        // Display Plot points
        dataSet.setDrawValues(true)
        lineData.setValueTextColor(getResources().getColor(R.color.bright_light_blue))
        lineData.setValueTextSize(10f)

        // Set data to the chart
        chart.data = lineData

        // Notify the chart that the data has changed
        chart.notifyDataSetChanged()
        chart.invalidate()
    }


    // After the RTR is over, it gets the lists with data from the emulator class to the class here thanks to the listener
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
        Log.d("RTR", "$methaneData")
        Log.d("RTR", "$tempData")
    }

    override fun onRunning(flag: Boolean) {
        isRunning = flag
    }

    override fun onTimeUpdate(time: Int) { // Updates the current time of the reading
        runOnUiThread {
//            title.text = "Running.."
//            timer.text = "Time: $time"
        }
    }

    private fun displayMessage(message: String){ // Used to display Toast messages
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, message, duration)
        toast.show()
    }

}