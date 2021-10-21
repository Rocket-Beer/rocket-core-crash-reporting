package com.rocket.android.core.crashreporting.file.printer

import LogFormat.currentDateFormat
import LogFormat.currentDateTimeFormat
import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import com.rocket.android.core.crashreporting.file.appendLn
import com.rocket.android.core.crashreporting.file.createFileIfNotExists
import com.rocket.android.core.crashreporting.file.createPathIfNotExists
import com.rocket.android.core.data.permissions.Permissions
import com.rocket.android.core.data.permissions.di.CoreDataProvider
import com.rocket.core.crashreporting.logger.LogLevel
import com.rocket.core.crashreporting.printer.LogPrinter
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileLogPrinter(application: Application, dispatcher: CoroutineDispatcher) : LogPrinter {
    private var scope = CoroutineScope(dispatcher + SupervisorJob())
    private val logFileName: String
    private val logFilePath: String
    private val resolver: ContentResolver = application.applicationContext.contentResolver
    private val permissions: Permissions by lazy { CoreDataProvider(context = application.applicationContext).provideCorePermissions }


    init {
        val packageName = application.applicationContext.packageName
        logFilePath = "$packageName-Rocket_logs"

        val currentDate = SimpleDateFormat(currentDateFormat, Locale.getDefault()).format(Date())
        logFileName = "$currentDate.log"
    }

    override fun printMessage(tag: String, msg: String, logLevel: LogLevel) {
        val message = "$logLevel/$tag: $msg"
        scope.launch(Dispatchers.Default) {
            writeFile(message)
        }
    }

    private fun writeFile(message: String) {
        try {
            val currentDateTime: String = SimpleDateFormat(currentDateTimeFormat, Locale.getDefault()).format(Date())
            readFile(logFilePath, logFileName, message).apply {
                appendLn("$currentDateTime $message")
            }
        } catch (e: IOException) {
            Log.e("FileLogPrinter", e.stackTrace.toString())
        }
    }

    private fun readFile(path: String, filename: String, message: String): File {
        val dir = getDocumentsDirectory(path, filename, message)
        return File("${dir?.absolutePath}/$filename").apply {
            createFileIfNotExists()
        }
    }

    private fun getDocumentsDirectory(path: String, filename: String, message: String): File? {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getDocumentFromMediaStore(filename, path, message)
        } else {
            getDocumentFromEnvironment(path)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getDocumentFromMediaStore(filename: String, path: String, message: String): File? {

        val relativePath = (Environment.DIRECTORY_DOCUMENTS).plus("/").plus(path)
        val collectionUri = MediaStore.Files.getContentUri("external")
        val contentValues = ContentValues().apply {

            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
            put(MediaStore.Files.FileColumns.IS_PENDING, 1)
        }

        val uri = resolver.insert(collectionUri, contentValues)

        scope.launch(Dispatchers.IO) {

            uri?.let { uri ->
                resolver.openOutputStream(uri).use { out -> out?.write(message.toByteArray()) }
                contentValues.clear()
                contentValues.put(MediaStore.Files.FileColumns.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }
        }

//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
//            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
//        }
//        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        return uri?.toFile()
    }

    @Suppress("DEPRECATION")
    private fun getDocumentFromEnvironment(path: String) =
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path
        ).apply {
            createPathIfNotExists()
        }

//    override fun checkPermissionStatus(): Boolean {
//
//        var hasPermissions = false
//
//        scope.launch(Dispatchers.Default) {
//            permissions.checkSinglePermission(permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE).fold(
//                ifLeft = { error ->
//                    Log.d("Rocket-Beer", "Permissions Denied: $error")
//                    hasPermissions = false
//                },
//                ifRight = {
//                    Log.d("Rocket-Beer", "Permissions Granted")
//                    hasPermissions = true
//                }
//            )
//        }
//        return hasPermissions
}


