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

@AndroidEntryPoint
class TestBluetooth : AppCompatActivity() {

    //inject the bluetooth adapter
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter

    //Declare button here
    private lateinit var scan: Button
    private lateinit var userBLEStatus: TextView

    private var isScanning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_bluetooth)
        supportActionBar?.title = "Back" // The tittle display at the top of each activity

        // Initialize buttons
        scan = findViewById(R.id.test_button)
        userBLEStatus = findViewById(R.id.bluetooth_status)

        showBluetoothDialog()
        //Scan Button Click Actions
        scan.setOnClickListener {
            //Prompt the user to turn on Bluetooth if it's off
            showBluetoothDialog()
        }

    }

    override fun onStart() {
        super.onStart()
        if(bluetoothAdapter?.isEnabled == false){
            userBLEStatus.text = "Bluetooth disabled"
        }else{
            userBLEStatus.text = "Bluetooth ready to pair"
        }
        scan.text = "Test Bluetooth"


    }
    private var isBluetoothDialogAlreadyShown = false
    private fun showBluetoothDialog(){
        if(bluetoothAdapter?.isEnabled == false){
            userBLEStatus.text = "Bluetooth disabled"
            if(!isBluetoothDialogAlreadyShown){
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startBluetoothIntentForResult.launch(enableBluetoothIntent)
                isBluetoothDialogAlreadyShown = true
            }
        }else{
            userBLEStatus.text = "Bluetooth ready to pair"
        }
    }

    private val startBluetoothIntentForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            isBluetoothDialogAlreadyShown = false
            userBLEStatus.text = "Bluetooth ready to pair"
            if(result.resultCode != Activity.RESULT_OK){
                showBluetoothDialog()
            }
        }
}
