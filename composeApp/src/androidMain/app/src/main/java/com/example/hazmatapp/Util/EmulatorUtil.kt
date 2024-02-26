package com.example.hazmatapp.Util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class EmulatorUtil {
    private var timer = Timer()
    private var currentVolume = Random.nextDouble(0.01, 1.0)
    private var isInTCMode = false

    fun startEmulation(duration: Int) {
        val task = object : TimerTask() {
            var secondsPassed = 0

            override fun run() {
                if (secondsPassed < duration) {
                    val changeBias = calculateChangeBias(currentVolume)
                    currentVolume += Random.nextDouble(-0.08, 0.2) * changeBias
                    currentVolume = max(currentVolume, 0.00015)

                    val currentLEL = min(calculateLEL(currentVolume), 100.0)

                    if (!isInTCMode && currentLEL > 60) {
                        Log.d("Emulator","Switching to Thermal Conductivity (TC) Mode")
                        isInTCMode = true
                    } else if (isInTCMode && currentLEL < 50) {
                        Log.d("Emulator","Switching to Catalytic mode")
                        isInTCMode = false
                    }

                    if (isInTCMode) currentVolume = min(currentVolume, 100.0)

                    val logEntry =
                        """{"time":${secondsPassed + 1},"volumePercent":${currentVolume.format(4)},"lelPercent":${currentLEL.format(
                            4
                        )}}"""
                    Log.d("Emulator", "$logEntry")

                    secondsPassed++
                } else {
                    timer.cancel()
                    timer.purge()
                }
            }
        }

        Log.d("Emulator", "Starting in Catalytic mode")
        timer.scheduleAtFixedRate(task, 0, 1000)
    }

    private fun calculateLEL(volume: Double): Double = (volume / 5.0) * 100

    private fun calculateChangeBias(currentVolume: Double): Double = when {
        currentVolume < 1.0 -> 1.25
        currentVolume < 2.0 -> 1.1
        else -> 0.95
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
