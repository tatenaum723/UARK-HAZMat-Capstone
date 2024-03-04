package com.example.hazmatapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.hazmatapp.Model.FirebaseRepository
import com.example.hazmatapp.Model.Methane

class SaveReadingViewModel: ViewModel() {
    private val repository = FirebaseRepository()  // Reference to the repository

    fun create(newRecord: Methane){
        repository.addReading(newRecord)
    }
}