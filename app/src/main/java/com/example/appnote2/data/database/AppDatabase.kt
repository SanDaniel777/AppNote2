package com.example.appnote2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appnote2.data.dao.NoteDao
import com.example.appnote2.data.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}