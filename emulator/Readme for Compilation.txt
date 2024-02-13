Still trying to get the run.bat to work properly, to no avail. For now, compile manually:

kotlinc emulator.kt EmulatorGUI.kt EmulatorLogic.kt -include-runtime -d emulator.jar

java -jar emulator.jar
