@echo off

echo Compiling Kotlin...
kotlinc emulator.kt EmulatorGUI.kt EmulatorLogic.kt -include-runtime -d emulator.jar

echo Running Application...
java -jar emulator.jar

pause
