package com.mylearning.doneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.mylearning.doneapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    // auth object
    private lateinit var mAuth: FirebaseAuth

    // Binding class
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_login)

//        subject to changes to fragment
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // init the auth object
        mAuth = FirebaseAuth.getInstance()

        binding.fragmentSignUpUserSignInTv.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        // Get the user input
        binding.fragmentSignUpBtn.setOnClickListener {
            val email = binding.fragmentSignUpEmailEt.text.toString().trim()
            val password = binding.fragmentSignUpPasswordEt.text.toString().trim()

            // check if email is empty
            if (email.isEmpty()) {
                binding.fragmentSignUpEmailEt.error = "Email Required"
                binding.fragmentSignUpEmailEt.requestFocus()
                return@setOnClickListener
            }

            // Check for invalid email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.fragmentSignUpEmailEt.error = "Invalid Email Address"
                binding.fragmentSignUpEmailEt.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                binding.fragmentSignUpPasswordEt.error = "6 character password required"
                binding.fragmentSignUpPasswordEt.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)

        } // end of button

        val view = binding.root
        setContentView(view)

    }

    private fun loginUser(email: String, password: String) {

        // progress bar is use because connection to firebase is an internet connection process
        binding.progressBar.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                binding.progressBar.visibility = View.INVISIBLE
                if(task.isSuccessful) {
                    login()
                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }

            }

    }


    override fun onStart() {
        super.onStart()

        // like a conditional statement to check if the a user has already sign in before
        mAuth.currentUser?.let {
            login()
        }
    }
}