package com.rocket.android.core.sampleapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val mainExceptionButton = findViewById<Button>(R.id.force_main_exception_button)
        val backgroundExceptionButton = findViewById<Button>(R.id.force_background_exception_button)

        mainExceptionButton.setOnClickListener {
            throw Exception("main thread custom exception")
        }

        backgroundExceptionButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                throw Exception("background thread custom exception")
            }
        }
    }
}
