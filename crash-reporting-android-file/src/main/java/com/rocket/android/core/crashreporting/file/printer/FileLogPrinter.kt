package com.rocket.android.core.crashreporting.file.printer

import android.app.Application
import android.os.Environment
import android.util.Log
import com.rocket.android.core.crashreporting.file.appendLn
import com.rocket.android.core.crashreporting.file.createFileIfNotExists
import com.rocket.android.core.crashreporting.file.createPathIfNotExists
import com.rocket.core.crashreporting.logger.LogLevel
import com.rocket.core.crashreporting.printer.LogPrinter
import currentDateFormat
import currentDateTimeFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileLogPrinter(application: Application, dispatcher: CoroutineDispatcher) : LogPrinter {
    private var scope =
        CoroutineScope(dispatcher + SupervisorJob())

    private val logFileName: String
    private val logFilePath: String

    init {
        val packageName = application.applicationContext.packageName
        logFilePath = "$packageName-Rocket_logs"

        val currentDate = SimpleDateFormat(currentDateFormat, Locale.getDefault()).format(Date())
        logFileName = "$currentDate.log"
    }

    override
    fun printMessage(tag: String, msg: String, logLevel: LogLevel) {
        val message = "$logLevel/$tag: $msg"

        scope.launch {
            writeToFile(message)
        }
    }

    private fun writeToFile(message: String) {
        try {
            val currentDateTime: String =
                SimpleDateFormat(currentDateTimeFormat, Locale.getDefault()).format(Date())

            getLogFile(logFilePath, logFileName).apply {
                appendLn("$currentDateTime $message")
            }
        } catch (e: IOException) {
            Log.e("FileLogPrinter", e.stackTrace.toString())
        }
    }

    private fun getLogFile(path: String, filename: String): File {
        val dir = getDocumentsDirectory(path)
        return File("${dir.absolutePath}/$filename").apply {
            createFileIfNotExists()
        }
    }

    private fun getDocumentsDirectory(path: String): File {
        return File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path
        ).apply {
            createPathIfNotExists()
        }
    }
}
