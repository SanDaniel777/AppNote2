package com.example.appnote2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val imagePath: String?,   // ruta de foto (opcional)
    val audioPath: String?,   // ruta de audio (opcional)
    val timestamp: Long = System.currentTimeMillis()
)