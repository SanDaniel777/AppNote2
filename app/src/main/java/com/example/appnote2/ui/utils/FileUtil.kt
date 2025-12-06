package com.example.appnote2.ui.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun uriToFile(uri: Uri, context: Context): File {
    val inputStream: InputStream? =
        context.contentResolver.openInputStream(uri)

    val file = File.createTempFile(
        "upload_",
        ".jpg",
        context.cacheDir
    )

    val outputStream = FileOutputStream(file)

    inputStream?.copyTo(outputStream)

    outputStream.close()
    inputStream?.close()

    return file
}

fun uriToMultipart(uri: Uri, fieldName: String): MultipartBody.Part {
    val file = uriToFile(uri)
    val requestFile =
        file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(
        fieldName,
        file.name,
        requestFile
    )
}