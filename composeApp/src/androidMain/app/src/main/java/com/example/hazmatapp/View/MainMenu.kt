package com.example.hazmatapp.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.R
import com.example.hazmatapp.Util.DialogListener
import com.example.hazmatapp.Util.DialogUtil
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
    private lateinit var auth: FirebaseAuth
    private lateinit var logoutPopUp: DialogUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Initialize buttons
        readingButton = findViewById(R.id.button1)
        graphButton = findViewById(R.id.button2)
        viewButton = findViewById(R.id.button3)
        bluetoothButton = findViewById(R.id.button4)
        instructionsButton = findViewById(R.id.button5)
        logoutButton = findViewById(R.id.button6)
        username_text = findViewById(R.id.username_text)
        auth = Firebase.auth
        logoutPopUp = DialogUtil("Are you sure that you want to logout?", "Yes", "No")

        // Retrieve the username from the Intent and sets up the username on the menu screen
        val currentUser = auth.currentUser?.email
        username_text.text = currentUser.toString()

    }

    override fun onResume() {
        super.onResume()
        val currentUser = auth.currentUser?.email
        username_text.text = currentUser.toString()
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
            val intent = Intent(this, testBluetooth::class.java)
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

    override fun onBackPressed() {
        super.onBackPressed()
        // Nothing happens if user presses back button
    }


}