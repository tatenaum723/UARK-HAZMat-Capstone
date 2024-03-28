package com.example.hazmatapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hazmatapp.Model.FirebaseRepository
import com.example.hazmatapp.Model.Reading

class PreviousReadingsViewModel: ViewModel() {
    private val repository = FirebaseRepository()  // Reference to the repository

    fun getAllReadings(){
        repository.getAllReadings()
    }

    fun getReadingList(): MutableLiveData<List<Reading>> {  // Gets the taskList from the repository
        return repository.readingsList
    }
}