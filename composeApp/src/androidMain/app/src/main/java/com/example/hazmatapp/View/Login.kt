package com.example.hazmatapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hazmatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    // Instance variables
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Back" // The title displayed at the top of each activity

        email = findViewById(R.id.email_input)
        password = findViewById(R.id.password_input)
        loginButton = findViewById(R.id.login_button)
        auth = Firebase.auth

        // OnClick methods
        loginButton.setOnClickListener {
            loginUser()
            finish() // Terminates activity
        }

    }

    private fun loginUser() {
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        // If all fields are entered tries to log in the user
        if(!userEmail.isNullOrEmpty() && !userPassword.isNullOrEmpty()){
            auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userEmail = user?.email.toString()
                        val intent = Intent(this, MainMenu::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        displayMessage("LOGIN FAILED")
                    }
                }
        }
        else{
            displayMessage("ENTER ALL FIELDS TO LOGIN USER")
        }
    }

    private fun displayMessage(message: String){ // Used to display Toast messages
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, message, duration)
        toast.show()
    }
}