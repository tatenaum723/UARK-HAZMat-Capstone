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


import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.result.contract.ActivityResultContracts


//Must import the recycler view and edit APIs



/* probably going to need a tailored version for our app
import kotlinx.android.synthetic.main.activity_main.scan_results_recycler_view
import org.jetbrains.anko.alert
import //Timber.log.//Timber*/

//TODO: Need this entry point for dependency injection to work but causes errors here
@AndroidEntryPoint
class ScanningBluetooth : AppCompatActivity() {

    //inject the bluetooth adapter
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter

    //Declare button here
    private lateinit var scan: Button

    private var isScanning = false


    //Function overrides for the bluetooth scanning activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning_bluetooth)

        // Initialize buttons
        scan = findViewById(R.id.scan_button)
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
        //TODO: fix this here becuase it causes page crashes
        showBluetoothDialog()
    }
    private var isBluetoothDialogAlreadyShown = false
    private fun showBluetoothDialog(){
        if(!bluetoothAdapter.isEnabled){
            if(!isBluetoothDialogAlreadyShown){
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startBluetoothIntentForResult.launch(enableBluetoothIntent)
                isBluetoothDialogAlreadyShown = true
            }
        }
    }

    private val startBluetoothIntentForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            isBluetoothDialogAlreadyShown = false
            if(result.resultCode != Activity.RESULT_OK){
                showBluetoothDialog()
            }
        }

}