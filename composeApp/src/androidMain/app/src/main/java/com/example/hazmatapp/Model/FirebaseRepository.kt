package com.example.hazmatapp.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRepository {

    private val database = Firebase.database.reference // Database reference
    private lateinit var auth: FirebaseAuth // Firebase authenticator reference
    private val usersPath = database.child("users") // Creates a parent node in the JSON file
    private val readingsPath = usersPath.child("readings") // Creates a child node under the users parent node




    fun addReading(reading: Methane){ // Adds a reading to the database

    }

    fun deleteReading(id: String){ // Deletes a reading from the database

    }

}