package com.example.appnote2.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnote2.data.model.Note
import com.example.appnote2.data.repository.NoteRepository
import com.example.appnote2.ui.utils.uriToFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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

    fun createNote(title: String, description: String, imageUri: Uri?, context: Context) {
        viewModelScope.launch {
            try {
                val titleBody = title.toRequestBody("text/plain".toMediaType())
                val descriptionBody = description.toRequestBody("text/plain".toMediaType())

                val imagePart: MultipartBody.Part? = imageUri?.let { uri ->
                    val file = uriToFile(uri, context)
                    val requestFile = file.asRequestBody("image/jpeg".toMediaType())

                    MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        requestFile
                    )
                }

                val response = repository.createNote(
                    titleBody,
                    descriptionBody,
                    imagePart,
                    null
                )

                // ✅ AQUÍ ESTÁ LA CLAVE
                if (response.isSuccessful) {
                    println("✅ NOTA GUARDADA CORRECTAMENTE")
                    loadNotes()
                } else {
                    println("❌ ERROR AL GUARDAR: ${response.code()}")
                    println("❌ ERROR BODY: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                println("🔥 EXCEPCIÓN: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun createNote(
        title: String,
        description: String,
        imageUri: Uri?,
        audioUri: Uri?,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val titleBody = title.toRequestBody("text/plain".toMediaType())
                val descriptionBody = description.toRequestBody("text/plain".toMediaType())

                // 📸 Imagen
                val imagePart: MultipartBody.Part? = imageUri?.let { uri ->
                    val file = uriToFile(uri, context)
                    val requestFile = file.asRequestBody("image/*".toMediaType())

                    MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        requestFile
                    )
                }

                // 🎤 Audio
                val audioPart: MultipartBody.Part? = audioUri?.let { uri ->
                    val file = uriToFile(uri, context)
                    val requestFile = file.asRequestBody("audio/*".toMediaType())

                    MultipartBody.Part.createFormData(
                        "audio",
                        file.name,
                        requestFile
                    )
                }

                repository.createNote(
                    titleBody,
                    descriptionBody,
                    imagePart,
                    audioPart
                )

                loadNotes()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}