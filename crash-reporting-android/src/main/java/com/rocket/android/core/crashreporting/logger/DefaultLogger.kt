package com.rocket.android.core.crashreporting.logger

import android.util.Log
import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.crashreporting.logger.LogLevel

class DefaultLogger(private val debuggable: Boolean = false) : CrashLogger {

    override fun log(message: String, map: Map<String, String?>, logLevel: LogLevel) {
        if (debuggable) {
            val tag = "DefaultLogger"
            val msg = "$message - $map"

            when (logLevel) {
                LogLevel.DEBUG -> Log.d(tag, msg)
                LogLevel.INFO -> Log.i(tag, msg)
                LogLevel.WARN -> Log.w(tag, msg)
                LogLevel.ERROR -> Log.e(tag, msg)
            }
        }
    }

    override fun log(exception: Throwable, map: Map<String, String?>, logLevel: LogLevel) {
        if (debuggable) {
            val tag = "DefaultLogger"
            val msg = "${exception.message} - $map"

            when (logLevel) {
                LogLevel.DEBUG -> Log.d(tag, msg)
                LogLevel.INFO -> Log.i(tag, msg)
                LogLevel.WARN -> Log.w(tag, msg)
                LogLevel.ERROR -> Log.e(tag, msg)
            }
        }
    }
}
