package com.example.appnote2.ui.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun uriToFile(uri: Uri, context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val mimeType = context.contentResolver.getType(uri) ?: "application/octet-stream"

    val extension = when (mimeType) {
        "audio/mpeg" -> ".mp3"
        "audio/mp4" -> ".m4a"
        "audio/3gpp" -> ".3gp"
        "audio/wav" -> ".wav"
        "image/jpeg" -> ".jpg"
        "image/png" -> ".png"
        else -> ".bin"
    }

    val file = File.createTempFile("upload_", extension, context.cacheDir)
    val outputStream = FileOutputStream(file)

    inputStream?.copyTo(outputStream)

    outputStream.close()
    inputStream?.close()

    return file
}
