import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val gui = EmulatorGUI()
        val emulatorLogic = EmulatorLogic { message ->
            gui.updateUI(message) // Pass the message to the GUI for update
        }
        gui.setEmulatorLogic(emulatorLogic) // Set the EmulatorLogic instance in EmulatorGUI
        gui.isVisible = true
    }
}
