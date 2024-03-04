package com.example.hazmatapp.Model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRepository {

    private val database = Firebase.database.reference // Database reference
    private lateinit var auth: FirebaseAuth // Firebase authenticator reference
    private val usersPath = database.child("users") // Creates a parent node in the JSON file
    private val readingsPath = usersPath.child("readings") // Creates a child node under the users parent node

    fun addUser(user: User) {
        val documentReference =
            user.id?.let { usersPath.child(it) } // Creates path to new user child using firebase authenticator id

        documentReference?.setValue(user)?.addOnSuccessListener {
            Log.d("Firestore", "User added successfully with ID: $user.id")
        }?.addOnFailureListener { e ->
            Log.w("Firestore", "Error adding user", e)
        }
    }
    fun addReading(reading: Methane){
        auth = Firebase.auth
        val currentUserID = auth.currentUser?.uid
        Log.d("FR", "$currentUserID")
    }

}