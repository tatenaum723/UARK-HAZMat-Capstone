package com.example.hazmatapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.hazmatapp.Model.FirebaseRepository
import com.example.hazmatapp.Model.User

class RegistrationViewModel: ViewModel() {
    private val repository = FirebaseRepository()  // Reference to the repository

    // Passes the data of the user created in the registration screen into the database
    fun add(user: User){
        repository.addUser(user)
    }
}