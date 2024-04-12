package com.example.hazmatapp.Util

import android.util.Log
import java.util.Timer
import java.util.TimerTask
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

interface EmulatorDataListener {
    fun onRunning(flag: Boolean)
    fun onDataUpdate(methanePercent: Double, tempFahrenheit: Double)
    fun onTimeUpdate(time: Int)
    fun onDoneReading(
        methaneReadings: MutableList<Pair<Int, Double>>,
        tempReadings: MutableList<Pair<Int, Double>>,
        maxMethane: Double,
        maxTemperature: Double)
}

class EmulatorUtil {
    private lateinit var timer: Timer
    private var currentTemperatureC = Random.nextDouble(20.0, 22.0) // Starting in a normal room temperature range in Celsius
    private var currentMethanePercent = 0.0 // Starting methane concentration
    private var isInTCMode = true // Always in TC Mode
    private var methaneReadings = mutableListOf<Pair<Int, Double>>()
    private var tempReadings = mutableListOf<Pair<Int, Double>>()
    private var listener: EmulatorDataListener? = null
    private var isRunning = false
    var maxMethane = 0.0 // Keeps track of the max methane percentage found during the reading
    var maxTemperature = 0.0 // Keeps track of the max temperature found during the reading

    fun startEmulation() {
        toggleFlag()
        timer = Timer()
        val task = object : TimerTask() {
            var secondsPassed = 0

            override fun run() {
                if (isRunning && isInTCMode) {
                    // Simulate environmental temperature change with relation to methane concentration
                    val tempChangeBias = Random.nextDouble(-0.05, 0.05)
                    currentTemperatureC += tempChangeBias
                    val tempFahrenheit = celsiusToFahrenheit(currentTemperatureC)
                    // Simulate methane percentage change, as if moving closer or further from a concentrated area
                    val methaneChangeBias = calculateChangeBias(currentMethanePercent)
                    currentMethanePercent += Random.nextDouble(-2.0, 5.0) * methaneChangeBias
                    currentMethanePercent = min(max(currentMethanePercent, 0.0), 100.0) // Ensure within 0-100%

                    val logEntry = """{"time":${secondsPassed + 1},"temperatureF":${tempFahrenheit.format(2)},"methanePercent":${currentMethanePercent.format(2)}}"""
                    Log.d("Emulator", logEntry)

                    // Check if currentMethanePercentage is greater than maxMethane
                    if(currentMethanePercent > maxMethane){
                        maxMethane = currentMethanePercent
                    }
                    // Check if tempFahrenheit is greater than maxTemperature
                    if(tempFahrenheit > maxTemperature){
                        maxTemperature = tempFahrenheit
                    }

                    updateTime(secondsPassed)
                    secondsPassed++
                    updateData(currentMethanePercent, tempFahrenheit)
                    methaneReadings.add(Pair(secondsPassed, currentMethanePercent))
                    tempReadings.add(Pair(secondsPassed, tempFahrenheit))
                } else {
                    stop()
                }
            }
        }
        Log.d("Emulator", "Starting in TC mode")
        timer.scheduleAtFixedRate(task, 0, 1000)
    }

    fun stop() {
        timer.cancel()
        timer.purge()
        toggleFlag()
        setData(methaneReadings, tempReadings, maxMethane, maxTemperature) // Sets the data in the RTR variables with same name
    }

    private fun celsiusToFahrenheit(celsius: Double): Double = (celsius * 9 / 5) + 32

    private fun calculateChangeBias(methanePercent: Double): Double = when {
        methanePercent < 20.0 -> 1.2 // Simulating moving towards higher concentration
        methanePercent < 40.0 -> 1.1
        else -> 0.9 // Simulating moving away from higher concentration
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    fun setListener(listener: EmulatorDataListener) {
        this.listener = listener
    }

    private fun updateTime(time: Int) {
        listener?.onTimeUpdate(time)
    }

    private fun updateData(methanePercent: Double, tempFahrenheit: Double) {
        listener?.onDataUpdate(methanePercent, tempFahrenheit)
    }

    private fun setData(
        methaneData: MutableList<Pair<Int, Double>>,
        tempData: MutableList<Pair<Int, Double>>,
        maxMethane: Double,
        maxTemperature: Double
    ) {
        listener?.onDoneReading(methaneData, tempData, maxMethane, maxTemperature)
    }

    private fun toggleFlag() {
        isRunning = !isRunning
        listener?.onRunning(isRunning)
    }

    fun resetData() {
        methaneReadings.clear()
        tempReadings.clear()
        listener?.onDoneReading(methaneReadings, tempReadings, maxMethane, maxTemperature)
    }
}
