package com.example.appnote2.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnote2.data.database.DatabaseProvider
import com.example.appnote2.data.model.Note
import com.example.appnote2.data.repository.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    val notes: StateFlow<List<Note>>

    init {
        val db = DatabaseProvider.getDatabase(application)
        val noteDao = db.noteDao()
        repository = NoteRepository(noteDao)

        notes = repository.getNotes()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }
}