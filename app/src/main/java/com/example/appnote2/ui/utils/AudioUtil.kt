package com.example.appnote2.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun createAudioFile(context: Context): Uri {
    val file = File(
        context.getExternalFilesDir(null),
        "audio_${System.currentTimeMillis()}.m4a"
    )

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}