package com.example.hazmatapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.hazmatapp.R
import com.example.hazmatapp.Util.DialogListener
import com.example.hazmatapp.Util.DialogUtil
import com.example.hazmatapp.ViewModel.MainMenuViewModel
import com.example.hazmatapp.ViewModel.PreviousReadingsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainMenu : AppCompatActivity(), DialogListener {

    // Instance variables
    private lateinit var readingButton: Button
    private lateinit var graphButton: Button
    private lateinit var viewButton: Button
    private lateinit var bluetoothButton: Button
    private lateinit var instructionsButton: Button
    private lateinit var logoutButton: Button
    private lateinit var username_text: TextView
    private lateinit var logoutPopUp: DialogUtil
    private lateinit var viewModel: MainMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Initialize variables
        readingButton = findViewById(R.id.button1)
        graphButton = findViewById(R.id.button2)
        viewButton = findViewById(R.id.button3)
        bluetoothButton = findViewById(R.id.button4)
        instructionsButton = findViewById(R.id.button5)
        logoutButton = findViewById(R.id.button6)
        username_text = findViewById(R.id.username_text)
        logoutPopUp = DialogUtil("Are you sure that you want to logout?", "Yes", "No")
        viewModel = ViewModelProvider(this)[MainMenuViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUsername().observe(this) { username -> // Observer for the live data object
            username_text.text = username // Displays the name of the user with live data
        }
        logoutPopUp.setListener(this)

        // Button actions
        readingButton.setOnClickListener {
            val intent = Intent(this, RealTimeReading::class.java)
            startActivity(intent)
        }
        graphButton.setOnClickListener {
            val intent = Intent(this, GraphCapture::class.java)
            startActivity(intent)
        }
        viewButton.setOnClickListener {
            val intent = Intent(this, PreviousReadings::class.java)
            startActivity(intent)
        }
        bluetoothButton.setOnClickListener {
            val intent = Intent(this, ScanningBluetooth::class.java)
            startActivity(intent)
        }
        instructionsButton.setOnClickListener {
            val intent = Intent(this, InstructionsScreen::class.java)
            startActivity(intent)
        }
        logoutButton.setOnClickListener {
            logoutPopUp.show(supportFragmentManager, "DELETE_DIALOG")
        }
    }

    override fun onYes(flag: Boolean) {
        if(flag){
            finish() // Terminates the activity if the user presses yes in the pop up screen
        }
    }

}