package com.example.hazmatapp.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.R
import android.os.Bundle
import com.example.hazmatapp.R
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class ScanningBluetooth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning_bluetooth)
        supportActionBar?.title = "Back" // The tittle display at the top of each activity

        // Initialize buttons
        scan = findViewById(R.id.connect_button)
        userBLEStatus = findViewById(R.id.lel_methane)
    }
}
