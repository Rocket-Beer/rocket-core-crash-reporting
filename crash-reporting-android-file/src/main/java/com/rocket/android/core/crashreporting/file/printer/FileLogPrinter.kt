package com.rocket.android.core.crashreporting.file.printer

import android.app.Application
import android.os.Environment
import android.util.Log
import com.rocket.android.core.crashreporting.printer.LogPrinter
import com.rocket.core.crashreporting.logger.LogLevel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileLogPrinter(private val application: Application) : LogPrinter {
    private val logFileName: String
    private val logFilePath: String

    init {
        val packageName = application.applicationContext.packageName
        logFilePath = "$packageName-Rocket_logs"

        val currentDate: String =
            SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        logFileName = "$currentDate.log"
    }

    override
    fun printMessage(tag: String, msg: String, logLevel: LogLevel) {
        val currentDateTime: String =
            SimpleDateFormat("yyyy-MM-dd HH:mm.sss", Locale.getDefault()).format(Date())

        val message = "$currentDateTime $logLevel/$tag: $msg"
        writeToFile(message)
    }

    private fun writeToFile(message: String) {
        try {
            val file = getLogFile(logFilePath, logFileName)
            with(FileOutputStream(file, true)) {
                write(message.toByteArray())
                write("\n".toByteArray())
                flush()
                close()
            }
        } catch (e: IOException) {
            Log.e("FileLogPrinter", e.stackTrace.toString())
        }
    }

    private fun getLogFile(path: String, filename: String): File {
        val dir = getDocumentsDirectory(path)
        val file = File("${dir.absolutePath}/$filename")

        if (!file.exists()) file.createNewFile()

        return file
    }

    private fun getDocumentsDirectory(path: String): File {
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path
        )

        if (!dir.exists()) dir.mkdirs()

        return dir
    }
}
