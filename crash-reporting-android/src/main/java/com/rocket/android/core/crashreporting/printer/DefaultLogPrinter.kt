package com.rocket.android.core.crashreporting.printer

import android.util.Log
import com.rocket.core.crashreporting.logger.LogLevel

class DefaultLogPrinter: LogPrinter {
    override
    fun printMessage(tag: String, msg: String, logLevel: LogLevel) {
        when (logLevel) {
            LogLevel.DEBUG -> Log.d(tag, msg)
            LogLevel.INFO -> Log.i(tag, msg)
            LogLevel.WARN -> Log.w(tag, msg)
            LogLevel.ERROR -> Log.e(tag, msg)
        }
    }
}