package com.rocket.core.crashreporting.logger

import java.io.File

interface CrashLogger {
    fun log(
        exception: Throwable,
        map: Map<String, String?> = emptyMap(),
        logLevel: LogLevel = LogLevel.ERROR,
        file: File? = null
    )

    fun log(
        message: String,
        map: Map<String, String?> = emptyMap(),
        logLevel: LogLevel = LogLevel.DEBUG,
        file: File? = null
    )
}
