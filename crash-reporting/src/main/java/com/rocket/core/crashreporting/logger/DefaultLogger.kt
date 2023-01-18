package com.rocket.core.crashreporting.logger

import LogFormat.dateTimeFilenameFormat
import com.rocket.core.crashreporting.printer.LogPrinter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DefaultLogger(private val debuggable: Boolean = false, private val printer: LogPrinter) :
    CrashLogger {

    override fun log(message: String, map: Map<String, String?>, logLevel: LogLevel, logPath: File?) {
        if (debuggable) {
            printer.printMessage(
                tag = "DefaultLogger",
                msg = "$message - $map",
                logLevel = logLevel
            )
        }

        logPath?.let {
            logToFile(logPath, logLevel, "$message - $map")
        }
    }

    override fun log(
        exception: Throwable,
        map: Map<String, String?>,
        logLevel: LogLevel,
        logPath: File?,
    ) {
        if (debuggable) {
            printer.printMessage(
                tag = "DefaultLogger",
                msg = "${exception.message} - $map",
                logLevel = logLevel
            )
        }

        logPath?.let {
            logToFile(logPath, logLevel, "${exception.message} - $map")
        }
    }

    private fun logToFile(
        logPath: File,
        logLevel: LogLevel,
        logText: String,
    ) {
        val logFile = if (!logPath.isFile) {
            val currentDateTime: String =
                SimpleDateFormat(dateTimeFilenameFormat, Locale.getDefault()).format(Date())
            File(logPath.path + "/" + currentDateTime + ".log")
        } else {
            logPath
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
