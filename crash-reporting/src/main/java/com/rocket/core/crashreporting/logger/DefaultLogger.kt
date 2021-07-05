package com.rocket.core.crashreporting.logger

import com.rocket.core.crashreporting.printer.LogPrinter

class DefaultLogger(private val debuggable: Boolean = false, private val printer: LogPrinter) :
    CrashLogger {

    override fun log(message: String, map: Map<String, String?>, logLevel: LogLevel) {
        if (debuggable) {
            printer.printMessage(
                tag = "DefaultLogger",
                msg = "$message - $map",
                logLevel
            )
        }
    }

    override fun log(exception: Throwable, map: Map<String, String?>, logLevel: LogLevel) {
        if (debuggable) {
            printer.printMessage(
                tag = "DefaultLogger",
                msg = "${exception.message} - $map",
                logLevel
            )
        }
    }
}
