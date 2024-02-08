For some reason, I can't get a .bat or .sh file to compile the kotlin correctly,
but the terminal works just fine. I've honestly spent far more time than I should've 
trying to get this to work to no sucess. So for now, in order to run the emulator, we will
need to use these two lines:

kotlinc emulator.kt -include-runtime -d emulator.jar

java -jar emulator.jar


Note: Not sure why, but kotlin takes a long time to compile (even a hello world script)
This could just be an issue with my system, but if it takes long to compile the emulator, just be patient