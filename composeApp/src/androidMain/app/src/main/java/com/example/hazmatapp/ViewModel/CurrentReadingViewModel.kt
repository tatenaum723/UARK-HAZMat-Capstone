package com.example.hazmatapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.hazmatapp.Model.FirebaseRepository
import com.example.hazmatapp.Model.Reading

class CurrentReadingViewModel: ViewModel() {
    private val repository = FirebaseRepository()

    fun delete(reading: Reading){
        repository.deleteReading(reading)
    }

}