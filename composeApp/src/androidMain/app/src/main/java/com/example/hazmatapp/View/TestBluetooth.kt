package com.example.hazmatapp.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hazmatapp.R

class TestBluetooth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_bluetooth)
        supportActionBar?.title = "Back" // The tittle display at the top of each activity


    }
}