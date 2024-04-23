package com.example.hazmatapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hazmatapp.Model.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainMenuViewModel: ViewModel() {
    private val repository = FirebaseRepository()

    fun getUsername(): LiveData<String> {
        return repository.getUsername()
    }
}