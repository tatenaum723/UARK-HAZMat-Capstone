package com.example.hazmatapp.View
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hazmatapp.Model.ReadingAdapter
import com.example.hazmatapp.R
import com.example.hazmatapp.ViewModel.PreviousReadingsViewModel

class PreviousReadings : AppCompatActivity(), ReadingAdapter.OnReadingClickListener {

    private lateinit var title: TextView
    private lateinit var viewModel: PreviousReadingsViewModel
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_readings)
        supportActionBar?.title = "Back" // The title displayed at the top of each activity

        // Initializes variables
        title = findViewById(R.id.readings_title)
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(this)[PreviousReadingsViewModel::class.java]
        viewModel.getAllReadings()
        viewModel.getReadingList() // Gets all the readings and displays them as cards in the recycler view
            .observe(this) { readingList -> // Sets up an observer for the recycler
                val adapter = ReadingAdapter(readingList, this) // Pass 'this' as the listener
                recycler.adapter = adapter
            }
        viewModel.getUsername().observe(this) { setUsername ->
            title.text = "$setUsername's readings"
        }

    }

    override fun onReadingClick(position: Int) {
        val clickedReading = viewModel.getReadingList().value?.get(position)
        if (clickedReading != null) {
            val intent = Intent(this, CurrentReading::class.java)
            intent.putExtra("reading", clickedReading) // Passes the reading's data to the activity
            startActivity(intent)
        }
    }
}
