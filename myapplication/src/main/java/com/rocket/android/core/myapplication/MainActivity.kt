package com.rocket.android.core.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.rocket.android.core.crashreporting.file.printer.FileLogPrinter
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var fileLogPrinter: FileLogPrinter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val text = findViewById<EditText>(R.id.text)
        val button = findViewById<Button>(R.id.button)

        fileLogPrinter = FileLogPrinter(application, Dispatchers.IO)
        button.setOnClickListener {
            // fileLogPrinter.printMessage("TAG", text.text.toString(), LogLevel.DEBUG)
            throw IndexOutOfBoundsException("ostiassss")
        }
    }
}
