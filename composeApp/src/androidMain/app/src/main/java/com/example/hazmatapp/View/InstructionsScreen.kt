package com.example.hazmatapp.View

import android.os.Bundle
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.R

class InstructionsScreen : AppCompatActivity() {
    private lateinit var expandableListView: ExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions_screen)
        supportActionBar?.title = "Back"

        expandableListView = findViewById(R.id.expandableListView)
        setupInstructions()
    }

    private fun setupInstructions() {
        val titleList = listOf("Getting Started", "Conducting and Saving a Reading", "Viewing Previous Readings", "Testing Bluetooth Connection")
        val detailList = listOf(
            listOf( // Getting Started
                "How do I get started?" to "Connect to the sensor using Bluetooth and ensure you are signed in to your account."
            ),
            listOf( // Conducting and Saving a Reading
                "How do I start a reading?" to "In the real-time or graph reading menu you may start a reading after a connection is made, once you press the “Start” button you will be shown the Methane LEL % and Temperature in Fahrenheit. After you are finished with the reading you have to press the “Stop” to terminate reading, if you are pleased with the reading you may hit the “Save” button to save it and if you are dissatisfied you may reset the reading by pressing the “Reset” button and hit “Start” again.",
                "How do I interpret a reading?" to "Normally for methane, a concentration of 0-5% by volume in air is considered too lean to ignite. When methane's concentration reaches 5%, it hits its LEL, making it capable of igniting. For readability purposes we scaled it to a 0-100% range so when the concentration for Methane by volume is at 2.5% the app shows 50% and if the volume is really at 5% then the app shows 100% and alerts the user there is potential for ignition.",
                "How do I save a reading?" to "After tapping on the “Save” button you are given a menu with different fields, you may label the reading with a specific name, then enter a location, time, and date according to what fits best. For time the application uses 24-Hour time for simplicity. Once you have finished with the above fields you may add any additional notes pertinent to the reading like who conducted the reading or other information. Then you may hit the “Save” button to send your reading to the database.",
                "Which reading should I choose?" to "The choice is really up to whatever you prefer the Real-Time Reading is quick and easy to read but doesn’t show you the plot points for each second. The Graph Reading is more complex to read in the moment but you can see each plot point for the entire reading and see at which time within the reading you were closest to the highest concentration of Methane."
            ),
            listOf( // Viewing Previous Readings
                "What is this page for?" to "The Previous Readings menu is great for taking a quick look at the Locations and Times of your readings as well as the Maximum Temperatures and Methane percentages within the readings which is helpful if you are trying to check what the Maximum measurements were for any of the locations or if you conducted readings at the same location multiple times.",
                "Can I delete an old reading if I don't need it anymore?" to "Yes, though we don’t recommend it, if you mistakenly save a reading with bad data then you may delete it from the previous readings by selecting the reading and hitting the “Delete” button.",
                "Is there a way to view our previous readings for better analysis?" to "Yes, go to the website for the application and login and there you will be able to conduct a much more in-depth analysis for investigatory or research reasons."
            ),
            listOf( // Testing Bluetooth Connection
                "What does this page do?" to "The Test Connection page checks if the phone has a Bluetooth adapter and whether it is enabled and can scan for the sensor package. It prompts the user to enable Bluetooth if its turned off.",
                "How do I test the connection?" to "When you open the screen it will display the current Bluetooth status of your device as “Bluetooth disabled” or “Ready to pair”. If Bluetooth is disabled and you wish to use the Bluetooth-dependent features of the app, you must turn it on by pressing the “Test Bluetooth” button and clicking enable in the dialog box or enable it manually through your phone's settings. At any time you can return to the “Test Bluetooth Connection” page to test if your phone can make Bluetooth connections."
            )
        )

        val listAdapter = SimpleExpandableListAdapter(
            this,
            titleList.map { hashMapOf("GROUP_NAME" to it) },
            R.layout.instructions_text_item,
            arrayOf("GROUP_NAME"),
            intArrayOf(R.id.titleItem),
            detailList.map { it.map { detail ->
                hashMapOf("CHILD_TYPE" to detail.first, "CHILD_TEXT" to detail.second)
            }},
            R.layout.instructions_text_item,
            arrayOf("CHILD_TYPE", "CHILD_TEXT"),
            intArrayOf(R.id.questionItem, R.id.descriptionItem)
        )

        expandableListView.setAdapter(listAdapter)
    }
}
