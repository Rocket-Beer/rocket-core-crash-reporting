package com.rocket.android.core.sampleapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rocket.android.core.crashreporting.file.di.CoreAndroidFileCrashProvider
import com.rocket.android.core.data.permissions.Permissions
import com.rocket.android.core.data.permissions.di.CoreDataProvider
import com.rocket.core.crashreporting.logger.LogLevel
import com.rocket.core.crashreporting.printer.LogPrinter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var fileLogPrinter: LogPrinter
    private var scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val permissions: Permissions by lazy { CoreDataProvider(context = application.applicationContext).provideCorePermissions }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initFileLogPrinter()
    }


    private fun initView() {
        val mainExceptionButton = findViewById<Button>(R.id.force_main_exception_button)
        val backgroundExceptionButton = findViewById<Button>(R.id.force_background_exception_button)

        mainExceptionButton.setOnClickListener {
            scope.launch {
                val hasPermissions = checkPermissionStatus()
                if (hasPermissions) {
                    startFileLogPrinter()
                }
            }
        }

        backgroundExceptionButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                throw Exception("background thread custom exception")
            }
        }
    }

    private suspend fun checkPermissionStatus(): Boolean {

        var hasPermissions = false
        permissions.checkSinglePermission(permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE).fold(
            ifLeft = { error ->
                Log.d("Rocket-Beer", "Permissions Denied: $error")
                hasPermissions = false
            },
            ifRight = {
                Log.d("Rocket-Beer", "Permissions Granted")
                hasPermissions = true
            }
        )
        return hasPermissions
    }

    private fun initFileLogPrinter() {
        fileLogPrinter = CoreAndroidFileCrashProvider.getInstance(application).run { logPrinter }
    }

    private fun startFileLogPrinter() {

        fileLogPrinter.printMessage(
            "FileLogReporter ",
            "throwable.stackTraceToString()",
            logLevel = LogLevel.ERROR
        )
    }
}
