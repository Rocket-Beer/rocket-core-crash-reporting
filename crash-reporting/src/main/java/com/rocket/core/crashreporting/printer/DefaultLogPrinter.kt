package com.rocket.core.crashreporting.printer

import com.rocket.core.crashreporting.logger.LogLevel
import currentDateTimeFormat
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
