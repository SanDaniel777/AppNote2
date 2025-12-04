package com.example.appnote2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnote2.data.model.Note
import com.example.appnote2.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {

    private val repository = NoteRepository()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    fun loadNotes() {
        viewModelScope.launch {
            val response = repository.getNotes()
            if (response.isSuccessful) {
                _notes.value = response.body() ?: emptyList()
            }
        }
    }
}