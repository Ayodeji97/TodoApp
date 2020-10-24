package com.mylearning.doneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.mylearning.doneapp.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    // auth object
    private lateinit var mAuth : FirebaseAuth

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)

        // initialise the auth
        mAuth = FirebaseAuth.getInstance()



        // subject to changes sooner
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        binding.fragmentSignUpUserSignInTv.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
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

            registerUser(email, password)
        }

        val view = binding.root
        setContentView(view)



    }

    private fun registerUser(email : String, password : String) {
        // creating a user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                // successful registration
                if (task.isSuccessful) {
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java).apply {
                        // This helps to close other activity : so when the user login into the app...the user cannot see register of login activity except when he logs out
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }

            }

    }
}