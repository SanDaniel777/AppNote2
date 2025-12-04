package com.example.appnote2.data.repository

import com.example.appnote2.data.remote.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody

class NoteRepository {

    suspend fun getNotes() =
        RetrofitInstance.api.getNotes()

    suspend fun createNote(
        title: RequestBody,
        description: RequestBody,
        image: MultipartBody.Part?,
        audio: MultipartBody.Part?
    ) = RetrofitInstance.api.createNote(title, description, image, audio)

    suspend fun updateNote(
        id: Int,
        title: RequestBody,
        description: RequestBody,
        image: MultipartBody.Part?,
        audio: MultipartBody.Part?
    ) = RetrofitInstance.api.updateNote(id, title, description, image, audio)

    suspend fun deleteNote(id: Int) =
        RetrofitInstance.api.deleteNote(id)
}