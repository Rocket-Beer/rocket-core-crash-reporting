package com.rocket.core.crashreporting.logger

import LogFormat.currentDateTimeFormat
import com.rocket.core.crashreporting.printer.LogPrinter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class DefaultLogger(private val debuggable: Boolean = false, private val printer: LogPrinter) :
    CrashLogger {

    override fun log(message: String, map: Map<String, String?>, logLevel: LogLevel, file: File?) {
        if (debuggable) {
            printer.printMessage(
                tag = "DefaultLogger",
                msg = "$message - $map",
                logLevel = logLevel
            )
        }

        file?.let {
            logToFile(file, logLevel, "$message - $map")
        }
    }


    override fun log(
        exception: Throwable,
        map: Map<String, String?>,
        logLevel: LogLevel,
        file: File?
    ) {
        if (debuggable) {
            printer.printMessage(
                tag = "DefaultLogger",
                msg = "${exception.message} - $map",
                logLevel = logLevel
            )
        }

        file?.let {
            logToFile(file, logLevel, "${exception.message} - $map")
        }
    }

    private fun logToFile(
        file: File,
        logLevel: LogLevel,
        logText: String
    ) {
        val logFile = if (!file.isFile) {
            val currentDateTime: String =
                SimpleDateFormat(currentDateTimeFormat, Locale.getDefault()).format(Date())
            File(file.path + "/" + currentDateTime + ".log")
        } else {
            file
        }

        if (!logFile.exists()) {
            try {
                logFile.createNewFile()
            } catch (e: IOException) {
                printer.printMessage(
                    tag = "DefaultLogger",
                    msg = e.message ?: "Can't create log file",
                    logLevel = logLevel
                )
            }
        }
        try {
            val buf = BufferedWriter(FileWriter(logFile, true))
            buf.append("DefaultLogger ${logLevel.name}")
            buf.newLine()
            buf.append(logText)
            buf.newLine()
            buf.close()
        } catch (e: IOException) {
            printer.printMessage(
                tag = "DefaultLogger",
                msg = e.message ?: "Can't write log file",
                logLevel = logLevel
            )
        }
    }
}
