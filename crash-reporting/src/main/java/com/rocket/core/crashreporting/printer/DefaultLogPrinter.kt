package com.rocket.core.crashreporting.printer

import LogFormat.currentDateTimeFormat
import com.rocket.core.crashreporting.logger.LogLevel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DefaultLogPrinter : LogPrinter {
    override
    fun printMessage(tag: String, msg: String, logLevel: LogLevel) {
        val currentDateTime: String =
            SimpleDateFormat(currentDateTimeFormat, Locale.getDefault()).format(Date())

        println("$currentDateTime $logLevel/$tag: $msg")
    }
}
