package com.example.appnote2.data.repository

import com.example.appnote2.data.dao.NoteDao
import com.example.appnote2.data.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    fun getNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    fun getNoteById(id: Int): Flow<Note> = noteDao.getNoteById(id)

    suspend fun insert(note: Note) = noteDao.insertNote(note)

    suspend fun delete(note: Note) = noteDao.deleteNote(note)
}