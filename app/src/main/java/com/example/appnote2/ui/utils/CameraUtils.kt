package com.example.appnote2.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun createImageFile(context: Context): Uri {
    val file = File(
        context.cacheDir,
        "photo_${System.currentTimeMillis()}.jpg"
    )

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}