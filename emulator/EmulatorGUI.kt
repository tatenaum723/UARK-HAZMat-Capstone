import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class EmulatorGUI : JFrame(), ActionListener {
    private val textArea = JTextArea()
    private var emulatorLogic: EmulatorLogic? = null
    private val buttonPanel = JPanel(FlowLayout(FlowLayout.CENTER, 10, 10))

    private val startButton = JButton("Start").apply { addActionListener(this@EmulatorGUI) }
    private val stopButton = JButton("Stop").apply { addActionListener(this@EmulatorGUI) }
    private val resumeButton = JButton("Resume").apply { addActionListener(this@EmulatorGUI) }
    private val refreshButton = JButton("Refresh").apply { addActionListener(this@EmulatorGUI) }
    private val saveButton = JButton("Save").apply { addActionListener(this@EmulatorGUI) }

    init {
        createUI()
    }

	private fun createUI() {
		title = "Emulator GUI"
		setSize(500, 400)
		setLocationRelativeTo(null)
		defaultCloseOperation = EXIT_ON_CLOSE
		layout = BorderLayout()

		val scrollPane = JScrollPane(textArea)
		add(scrollPane)

		// Set preferred size for buttons
		listOf(startButton, stopButton, resumeButton, refreshButton, saveButton).forEach {
			it.preferredSize = Dimension(100, 40)
		}

		// Add buttons to the panel
		buttonPanel.add(startButton)
		buttonPanel.add(refreshButton)
		buttonPanel.add(saveButton)

		// Add button panel to the frame
		add(buttonPanel, BorderLayout.SOUTH)

		// Create an instance of EmulatorLogic with the updateUI lambda
		emulatorLogic = EmulatorLogic { message -> updateUI(message) }
		emulatorLogic?.setInitialMessage()
	}


    // This method updates the GUI's text area
    fun updateUI(message: String) {
        SwingUtilities.invokeLater {
            textArea.append("$message\n")
        }
    }

    fun setEmulatorLogic(emulatorLogic: EmulatorLogic) {
        this.emulatorLogic = emulatorLogic
    }

    override fun actionPerformed(e: ActionEvent) {
        when (e.actionCommand) {
            "Start" -> {
                // Reset the EmulatorLogic instance if it's not null
                emulatorLogic?.resetEmulation()
                emulatorLogic?.startEmulation()
                // Show the "Stop" button
                replaceButton(stopButton)
            }
            "Stop" -> {
                // Pause the emulation
				SwingUtilities.invokeLater {
					updateUI("----Paused [DEBUG]----")
				}
                emulatorLogic?.stopEmulation()
                // Show the "Resume" button
                replaceButton(resumeButton)
            }
            "Resume" -> {
                // Resume the emulation
                emulatorLogic?.startEmulation()
                // Show the "Stop" button
                replaceButton(stopButton)
            }
			"Refresh" -> {
				// Dispose of the current JFrame
				dispose()
				// Reset the emulator logic's readings values
				emulatorLogic?.resetEmulation()
				// Create a new instance of EmulatorGUI
				val newGUI = EmulatorGUI()
				newGUI.isVisible = true
			}
            "Save" -> {
                emulatorLogic?.saveReadings()
            }
        }
    }

	private fun replaceButton(newButton: JButton) {
		// Remove the current button
		buttonPanel.removeAll()
		// Add the new button
		buttonPanel.add(newButton)
		// Add the refresh and save buttons
		buttonPanel.add(refreshButton)
		buttonPanel.add(saveButton)
		// Make the previous button invisible
		when (newButton) {
			startButton -> {
				stopButton.isVisible = false
				resumeButton.isVisible = false
			}
			stopButton -> {
				startButton.isVisible = false
				resumeButton.isVisible = false
			}
			resumeButton -> {
				startButton.isVisible = false
				stopButton.isVisible = false
			}
		}
		// Make the new button visible
		newButton.isVisible = true
		// Refresh the layout
		buttonPanel.revalidate()
		buttonPanel.repaint()
	}

}
