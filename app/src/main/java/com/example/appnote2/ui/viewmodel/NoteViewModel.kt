package com.example.appnote2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnote2.data.model.Note
import com.example.appnote2.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class NoteViewModel : ViewModel() {

    private val repository = NoteRepository()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    fun loadNotes() {
        viewModelScope.launch {
            try {
                val response = repository.getNotes()
                if (response.isSuccessful) {
                    _notes.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun createNote(
        title: RequestBody,
        description: RequestBody,
        image: MultipartBody.Part?,
        audio: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            try {
                repository.createNote(title, description, image, audio)
                loadNotes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateNote(
        id: Int,
        title: RequestBody,
        description: RequestBody,
        image: MultipartBody.Part?,
        audio: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            try {
                repository.updateNote(id, title, description, image, audio)
                loadNotes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteNote(id)
                loadNotes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}