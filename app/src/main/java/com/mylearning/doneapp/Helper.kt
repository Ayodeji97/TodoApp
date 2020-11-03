package com.mylearning.doneapp

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.toast(message : String) =
    Toast.makeText(this,  message, Toast.LENGTH_SHORT).show()

fun Context.login() {
    val intent = Intent(this, HomeActivity::class.java).apply {
        // This helps to close other activity : so when the user login into the app...the user cannot see register of login activity except when he logs out
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}