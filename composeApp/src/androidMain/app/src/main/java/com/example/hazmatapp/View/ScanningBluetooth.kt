package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hazmatapp.R
import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


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
        scan = findViewById(R.id.scan_button)
        userBLEStatus = findViewById(R.id.bluetooth_status)
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

    }
    override fun onStart() {
        super.onStart()

        //Works now
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