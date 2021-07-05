package com.rocket.android.core.crashreporting.file

import java.io.File
import java.nio.charset.Charset

internal fun File.appendLn(message: String, charset: Charset = Charsets.UTF_8) {
    appendText(message, charset)
    appendText("\n", charset)
}

internal fun File.createFileIfNotExists() {
    if (!exists()) createNewFile()
}

internal fun File.createPathIfNotExists() {
    if (!exists()) mkdirs()
}