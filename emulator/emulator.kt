import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//
//Note: the sensor package apparently has two modes: Catalytic, and Thermal Conductivity (TC) mode.
//The mode itself does not necessarily impact the way that Volume% and LEL% are emulated (or calculated), yet it technically impacts how readings are recorded, 
//I figured it would be a nice and simple touch to emulate the package's response to when it switches modes (based off the the recommended parameters in the documentation.
//Summary: In Catalytic mode Volume% has a max value of 5%, LEL% as a max value of 100%
//		   In Thermal Conductivity (TC) mode, Volume% can reach 100%, but LEL% = 5% for any Volume% >= 5%
//Based on how this emulator currently works, it is unlikely you will see the sensor package actually swap back to catalytic mode, but the functionality is still there.
//
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//
//Emulator is supposed to mimic a typical scenario where a first-responder is actively searching for the methane concentration's location with the  
//drone-mounted package, so logically, as volume% and LEL% get higher, the emulator becomes more 'accurate' at symoblizing that the drone is getting closer 
//to the concentration zone and is following a specific direction (emulator less likely to generate negative values as current volume% and LEL% are higher)
//
//In other words, emulator is made to assume that first-responder/user realizes the correct direction of concentration once realizing volume% and LEL% are increasing
//
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

fun main() {
    val duration = 120 // seconds
    val timer = Timer()
    //val readings = mutableListOf<Pair<Double, Double>>()
    var isInTCMode = false // Track if we're in TC mode
    var currentVolume = Random.nextDouble(0.01, 1.0) // Start at moderately realistic value, but significantly below explosive range
    val fileName = generateFileName() // Dynamically generated file name based on date and time. Note that data is still obtainable in the event of a crash.
    val file = File(fileName)

    val task = object : TimerTask() {
        var secondsPassed = 0

        override fun run() {
            if (secondsPassed < duration) {
                val changeBias = calculateChangeBias(currentVolume)
                currentVolume += Random.nextDouble(-0.08, 0.2) * changeBias
                currentVolume = max(currentVolume, 0.00015) // Ensure volume stays above atmospheric baseline with is around 0.00015-0.00017
                val currentLEL = min(calculateLEL(currentVolume), 100.0) // LEL is capped at 100%

                // Mode switching logic
                if (!isInTCMode && currentLEL > 60) {
                    println("Switching to Thermal Conductivity (TC) Mode")
                    isInTCMode = true
                } else if (isInTCMode && currentLEL < 50) {
                    println("Switching to Catalytic mode")
                    isInTCMode = false
                }

                // Max volume for TC mode
                if (isInTCMode) currentVolume = min(currentVolume, 100.0)

                val logEntry = """{"time":${secondsPassed + 1},"volumePercent":${currentVolume.format(4)},"lelPercent":${currentLEL.format(4)}}"""
                println(logEntry)
                appendLogEntry(file, logEntry)

                secondsPassed++
            } else {
                timer.cancel()
                timer.purge()
            }
        }
    }

    println("Starting in Catalytic mode")
    timer.scheduleAtFixedRate(task, 0, 1000)
}

fun calculateLEL(volume: Double): Double = (volume / 5.0) * 100

fun calculateChangeBias(currentVolume: Double): Double = when {
    currentVolume < 1.0 -> 1.25
    currentVolume < 2.0 -> 1.1
    else -> 0.95
}

fun appendLogEntry(file: File, logEntry: String) {
    file.appendText("$logEntry,\n")
}

fun generateFileName(): String {
    val dateFormat = SimpleDateFormat("MM-dd-yyyy_HH-mm-ss")
    return "sensor_readings_${dateFormat.format(Date())}.json"
}

fun Double.format(digits: Int) = "%.${digits}f".format(this)