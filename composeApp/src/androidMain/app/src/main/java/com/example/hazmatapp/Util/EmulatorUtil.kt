import android.util.Log
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

interface EmulatorDataListener {
    fun onRunning(flag: Boolean)
    fun onDataUpdate(lel: Double, vol: Double)
    fun onTimeUpdate(time: Int)
    fun onDoneReading(lelReadings: MutableList<Pair<Int, Double>>, volReadings: MutableList<Pair<Int, Double>>)
}

class EmulatorUtil {
    private lateinit var timer: Timer
    private var currentVolume = Random.nextDouble(0.01, 1.0)
    private var currentLEL: Double = 0.0
    private var isInTCMode = false
    private var lelReadings = mutableListOf<Pair<Int, Double>>()
    private var volReadings = mutableListOf<Pair<Int, Double>>()
    private var listener: EmulatorDataListener? = null
    private var isRunning = false


    fun startEmulation() {
        toggleFlag() // Changes status of flag
        timer = Timer()

        val task = object : TimerTask() {
            var secondsPassed = 0

            override fun run(){
                if (isRunning) {
                    val changeBias = calculateChangeBias(currentVolume)
                    currentVolume += Random.nextDouble(-0.08, 0.2) * changeBias
                    currentVolume = max(currentVolume, 0.00015)

                    currentLEL = min(calculateLEL(currentVolume), 100.0)

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
                    //Log.d("Emulator", "$logEntry")

                    updateTime(secondsPassed)
                    secondsPassed++
                    updateData(currentLEL, currentVolume) // Updates the data with the listener
                    lelReadings.add(Pair(secondsPassed, currentLEL))
                    volReadings.add(Pair(secondsPassed, currentVolume))

                }
                else{
                    stop() // Stops the reading
                }
            }
        }

        Log.d("Emulator", "Starting in Catalytic mode")
        timer.scheduleAtFixedRate(task, 0, 1000)

    }

    fun stop(){ // Stops the reading
        Log.d("Emulator", "Cancelling/Purging")
        timer.cancel()
        timer.purge()
        currentVolume = 0.0 // Resets the variable
        currentLEL = 0.0 // Resets the variable
        setData(lelReadings, volReadings) // Informs the listener that there was a change in the data
        toggleFlag() // Changes status of flag
    }

    private fun calculateLEL(volume: Double): Double = (volume / 5.0) * 100

    private fun calculateChangeBias(currentVolume: Double): Double = when {
        currentVolume < 1.0 -> 1.25
        currentVolume < 2.0 -> 1.1
        else -> 0.95
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)


    // Used to set the listener to the instance of the RealTimeReading class. This instance is the object that will receive updates from the EmulatorUtil class.
    fun setListener(listener: EmulatorDataListener) {
        this.listener = listener
    }

    private fun updateTime(time: Int){ // Sends the time updates to the RTR
        listener?.onTimeUpdate(time)
    }
    // Inside the task's run() method, updateData() should notify listeners
    private fun updateData(lel: Double, vol: Double) {
        val formattedLEL = lel.format(4)
        val formattedVOL = vol.format(4)

        // Invoking the onDataUpdate method of the object that is currently registered as the listener (RealTimeReading class).
        listener?.onDataUpdate(formattedLEL.toDouble(), formattedVOL.toDouble())
    }

    private fun setData(lelData: MutableList<Pair<Int, Double>>, volData: MutableList<Pair<Int, Double>>){
        listener?.onDoneReading(lelData, volData)
    }

    fun resetData(){ // Clears the data from the lists
        lelReadings.clear()
        volReadings.clear()
        listener?.onDoneReading(lelReadings, volReadings)
    }

    private fun toggleFlag(){ // Keeps track if the emulator is running "isRunning = true" or not
        isRunning = !isRunning
        Log.d("Emul","isRunning: $isRunning")
        listener?.onRunning(isRunning) // Listener notifies of change in value
    }
}
