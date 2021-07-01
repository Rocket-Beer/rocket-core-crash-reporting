package com.rocket.core.crashreporting.logger

interface CrashLogger {
    fun log(
        exception: Throwable,
        map: Map<String, String?> = emptyMap(),
        logLevel: LogLevel = LogLevel.ERROR
    )

    fun log(
        message: String,
        map: Map<String, String?> = emptyMap(),
        logLevel: LogLevel = LogLevel.ERROR
    )
}
