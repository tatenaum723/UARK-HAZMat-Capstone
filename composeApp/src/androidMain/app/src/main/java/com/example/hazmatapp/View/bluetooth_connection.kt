package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hazmatapp.R
import android.bluetooth.BluetoothAdapter
import android.widget.Button
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
//New Stuff
import com.example.hazmatapp.Data.ConnectionState
//ViewModel Specific stuff
import androidx.lifecycle.ViewModelProvider
import com.example.hazmatapp.ViewModel.MethaneViewModel
import android.util.Log
import android.widget.EditText
import android.widget.Toast


//TODO: Need this entry point for dependency injection to work
//You must manually enable bluetooth permissions for the app to query to turn on bluetooth
@AndroidEntryPoint
class bluetooth_connection : AppCompatActivity() {

    //inject the bluetooth adapter
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter

    //Declare button here
    private lateinit var connect: Button
    private lateinit var userBLEStatus: TextView
    private lateinit var viewModel: MethaneViewModel

    //Tie the connection state in the view the that of the ViewModel
    //Basically the error is taht viewModel must be initialized before connection state can be assigned
    //val bleConnectionState = viewModel.connectionState

    private var connectionEstablished = false

    //TODO: This class needs a way to update the values on the screen from the data the ViewModel is getting from bluetooth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_connection)

        // Initialize buttons
        connect = findViewById(R.id.connect_button)
        userBLEStatus = findViewById(R.id.lel_methane)
        //Initialize ViewModel
        viewModel = ViewModelProvider(this)[MethaneViewModel::class.java]

        val bleConnectionState = viewModel.connectionState

        //Scan Button Click Actions
        connect.setOnClickListener {
            //scan.text = "Stop Scan"
            if (connectionEstablished == false && bleConnectionState == ConnectionState.Uninitialized) {
                viewModel.initializeConnection()
                connect.text = "Disconnect"
                connectionEstablished = true
            }
            else if (connectionEstablished == true) {
                connect.text = "Connect"
                connectionEstablished = false
            }
        }

    }
    override fun onStart() {
        super.onStart()
        val bleConnectionState = viewModel.connectionState
        if(bleConnectionState == ConnectionState.Disconnected){
            viewModel.reconnect()
        }


    }
    override fun onStop() {
        super.onStop()
        val bleConnectionState = viewModel.connectionState
        if(bleConnectionState == ConnectionState.Connected){
            viewModel.disconnect()
        }
    }


}