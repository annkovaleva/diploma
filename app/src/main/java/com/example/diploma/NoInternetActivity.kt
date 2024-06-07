package com.example.diploma.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.diploma.MainActivity
import com.example.diploma.R
import com.example.diploma.utils.NetworkUtils

class NoInternetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        findViewById<Button>(R.id.button_retry).setOnClickListener {
            if (NetworkUtils.isInternetAvailable(this)) {
                // Restart main activity if internet is available
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                // Show a message or handle the situation
            }
        }
    }
}
