package com.example.hazmatapp.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.R
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


//New Stuff

//TODO: Need this entry point for dependency injection to work
//You must manually enable bluetooth permissions for the app to query to turn on bluetooth
@AndroidEntryPoint
class ScanningBluetooth : AppCompatActivity() {

    //inject the bluetooth adapter
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter

    //Declare button here
    private lateinit var scan: Button
    private lateinit var userBLEStatus: TextView



    private var isScanning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning_bluetooth)

        // Initialize buttons
        scan = findViewById(R.id.connect_button)
        userBLEStatus = findViewById(R.id.lel_methane)


        //Scan Button Click Actions
        scan.setOnClickListener {
            //scan.text = "Stop Scan"
            if (isScanning == false) {
                scan.text = "STOP SCAN"
                isScanning = true
            }
            else if (isScanning == true) {
                scan.text = "START SCAN"
                isScanning = false
            }
        }

        supportActionBar?.title = "Back" // The tittle display at the top of each activity
    }
    override fun onStart() {
        super.onStart()

        //Prompt the user to turn on Bluetooth if it's off
        showBluetoothDialog()

    }
    private var isBluetoothDialogAlreadyShown = false
    private fun showBluetoothDialog(){
        if(bluetoothAdapter?.isEnabled == false){
            userBLEStatus.text = "Bluetooth is OFF"
            if(!isBluetoothDialogAlreadyShown){
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startBluetoothIntentForResult.launch(enableBluetoothIntent)
                isBluetoothDialogAlreadyShown = true
            }
        }else{
            userBLEStatus.text = "Bluetooth is ON"
        }
    }

   private val startBluetoothIntentForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            isBluetoothDialogAlreadyShown = false
            userBLEStatus.text = "Bluetooth is ON"
            if(result.resultCode != Activity.RESULT_OK){
                showBluetoothDialog()
            }
        }

}