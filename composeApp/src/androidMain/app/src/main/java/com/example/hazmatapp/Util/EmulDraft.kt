
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class EmulDraft(private val updateUI: (String) -> Unit) {
    private var timer: Timer? = null
    private var isTimerRunning = false
    private var isPaused = false

    private val LELreadings = mutableListOf<Pair<Int, Double>>()
    private val VOLreadings = mutableListOf<Pair<Int, Double>>()
    private var isInTCMode = false
    private var currentVolume = Random.nextDouble(0.01, 1.0)
    private var secondsPassed = 0


    fun startEmulation(duration: Int = 30) {
        if (!isTimerRunning) {
            isInTCMode = false
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    if (!isPaused) {
                        if (secondsPassed < duration) {
                            performEmulationStep()
                            secondsPassed++
                        } else {
                            stopEmulation()
                            SwingUtilities.invokeLater {
                                updateUI("Simulation completed.")
                            }
                        }
                    }
                }
            }, 0, 1000)
            isTimerRunning = true
            isPaused = false
        }
    }

    fun stopEmulation() {
        timer?.cancel()
        timer?.purge()
        isTimerRunning = false
        isPaused = true
    }

    private fun performEmulationStep() {
        val changeBias = calculateChangeBias(currentVolume)
        currentVolume += Random.nextDouble(-0.08, 0.2) * changeBias
        currentVolume = max(currentVolume, 0.00015)
        val currentLEL = min(calculateLEL(currentVolume), 100.0)

        if (!isInTCMode && currentLEL > 60) {
            isInTCMode = true
            SwingUtilities.invokeLater {
                updateUI("----Switching to Thermal Conductivity (TC) Mode----")
            }
        } else if (isInTCMode && currentLEL < 50) {
            isInTCMode = false
            SwingUtilities.invokeLater {
                updateUI("----Switching to Catalytic mode----")
            }
        }

        if (isInTCMode) currentVolume = min(currentVolume, 100.0)

        val logMessage =
            "Time [seconds]: ${secondsPassed + 1}, Volume%: ${currentVolume.format(4)}, LEL%: ${currentLEL.format(4)}"
        SwingUtilities.invokeLater {
            updateUI(logMessage)
        }
        LELreadings.add(Pair(secondsPassed + 1, currentLEL))
        VOLreadings.add(Pair(secondsPassed + 1, currentVolume))


    }

    private fun calculateLEL(volume: Double): Double = (volume / 5.0) * 100

    private fun calculateChangeBias(currentVolume: Double): Double = when {
        currentVolume < 1.0 -> 1.25
        currentVolume < 2.0 -> 1.1
        else -> 0.95
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}