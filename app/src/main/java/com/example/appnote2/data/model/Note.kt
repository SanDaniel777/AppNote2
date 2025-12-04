package com.example.appnote2.data.model

data class Note(
    val id: Int = 0,
    val title: String,
    val description: String,
    val imagePath: String? = null,
    val audioPath: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)