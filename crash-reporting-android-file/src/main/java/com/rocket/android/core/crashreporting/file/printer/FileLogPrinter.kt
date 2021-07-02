package com.rocket.android.core.crashreporting.file.printer

import android.app.Application
import android.os.Environment
import com.rocket.android.core.crashreporting.printer.LogPrinter
import com.rocket.core.crashreporting.logger.LogLevel
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileLogPrinter(private val application: Application) : LogPrinter {
    private val FILE_NAME: String
    private val FILE_PATH: String

    init {
        val packageName = application.applicationContext.packageName
        FILE_PATH = "$packageName-Rocket_logs"

        val currentDate: String =
            SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        FILE_NAME = "$currentDate.log"
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
            val file = getLogFile(FILE_PATH, FILE_NAME)
            with(FileOutputStream(file, true)) {
                write(message.toByteArray())
                write("\n".toByteArray())
                flush()
                close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
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

    private fun isExternalStorageReadOnly(): Boolean =
        Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()

    private fun isExternalStorageAvailable(): Boolean =
        Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}