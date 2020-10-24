package com.mylearning.doneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mylearning.doneapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    // Binding class
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)

//        subject to changes to fragment
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.fragmentSignUpUserSignInTv.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        val view = binding.root
        setContentView(view)




    }
}