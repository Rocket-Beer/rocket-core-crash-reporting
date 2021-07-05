package com.rocket.core.crashreporting.printer

import com.rocket.core.crashreporting.logger.LogLevel

interface LogPrinter {
    fun printMessage(tag: String, msg: String, logLevel: LogLevel)
}
