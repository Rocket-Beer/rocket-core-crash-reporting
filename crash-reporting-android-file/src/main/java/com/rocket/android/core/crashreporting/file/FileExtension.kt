package com.rocket.android.core.crashreporting.file

import android.util.Log
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

internal suspend fun File.appendLn(message: String, charset: Charset = Charsets.UTF_8) {
    appendText(message, charset)
    appendText("\n", charset)
}

internal fun File.createFileIfNotExists() {
    if (!exists()) {
        try {
            createNewFile()
        } catch (e: IOException) {
            Log.e("createFileIfNotExists", e.message.toString())
        }
    }
}

internal fun File.createPathIfNotExists() {
    if (!exists()) mkdirs()
}
