package com.example.hazmatapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.hazmatapp.Model.FirebaseRepository
import com.example.hazmatapp.Model.Reading

class SaveReadingViewModel: ViewModel() {
    private val repository = FirebaseRepository()  // Reference to the repository

    fun create(newRecord: Reading){
        repository.addReading(newRecord)
    }
}