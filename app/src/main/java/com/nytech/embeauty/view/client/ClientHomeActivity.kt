package com.nytech.embeauty.view.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nytech.embeauty.R

class ClientHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(R.layout.activity_client_home)
    }
}