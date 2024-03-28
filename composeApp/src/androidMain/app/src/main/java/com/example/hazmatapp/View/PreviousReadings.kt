package com.example.hazmatapp.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hazmatapp.Model.ReadingAdapter
import com.example.hazmatapp.R
import com.example.hazmatapp.ViewModel.PreviousReadingsViewModel

class PreviousReadings : AppCompatActivity(), ReadingAdapter.OnReadingClickListener {

    private lateinit var viewModel: PreviousReadingsViewModel
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_readings)
        supportActionBar?.title = "Back" // The title displayed at the top of each activity

        // Initializes variables
        viewModel = ViewModelProvider(this)[PreviousReadingsViewModel::class.java]
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        // Gets all the readings and displays them as cards in the recycler view
        viewModel.getAllReadings()
        viewModel.getReadingList().observe(this) { readingList -> // Sets up an observer for the recycler
            val adapter = ReadingAdapter(readingList, this) // Pass 'this' as the listener
            recycler.adapter = adapter
        }
    }

    override fun onReadingClick(position: Int) {
        // Handle item click here
        Log.d("PR", "Clicked on position: $position")
    }
}
