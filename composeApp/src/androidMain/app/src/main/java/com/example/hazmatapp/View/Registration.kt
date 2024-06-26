package com.example.hazmatapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hazmatapp.ViewModel.RegistrationViewModel
import com.example.hazmatapp.Model.User
import com.example.hazmatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {

    // Instance variables
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirm: EditText
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth // Instance of firebase authenticator
    private lateinit var viewModel: RegistrationViewModel // Reference to the view model class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.title = "Back" // The title displayed at the top of each activity

        name = findViewById(R.id.name_input)
        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        confirm = findViewById(R.id.confirm_input)
        registerButton = findViewById(R.id.register_button)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        // OnClick methods
        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() { // Method user to register a new users using firebase authenticator
        val userName = name.text.toString()
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()
        val confirmPassword = confirm.text.toString()

        if(!userName.isNullOrEmpty() && !userEmail.isNullOrEmpty() && !userPassword.isNullOrEmpty() && !confirmPassword.isNullOrEmpty()){
            if(userPassword == confirmPassword){
                auth.createUserWithEmailAndPassword(userEmail, userPassword)  // Creates the user
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val id = auth.currentUser?.uid.toString() // Unique user id generated by Firestore
                            Log.d("Registration", "User ID: $id")
                            val email = auth.currentUser?.email.toString() // User email

                            val newUser = User(id, userName, email) // New user object to add the user data into
                            addUser(newUser) // Adds the new user object into the database
                            displayMessage("Registration successful")
                            finish() // The activity is finished/ terminated and user goes back to l/r menu
                        } else {
                            displayMessage("Error creating account")
                        }
                    }
            }
            else{
                displayMessage("Passwords do not match")
            }
        }
        else{
            displayMessage("Enter all required fields")
        }
    }

    private fun addUser(user: User) {
        viewModel.add(user)
    }

    private fun displayMessage(message: String){ // Used to display Toast messages
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, message, duration)
        toast.show()
    }
}