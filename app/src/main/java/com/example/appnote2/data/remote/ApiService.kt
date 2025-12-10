package com.example.appnote2.data.remote

import com.example.appnote2.data.model.Note
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @GET("notes")
    suspend fun getNotes(): Response<List<Note>>

    @Multipart
    @POST("notes")
    suspend fun createNote(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part audio: MultipartBody.Part?
    ): Response<Note>

    @Multipart
    @PUT("notes/{id}")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part audio: MultipartBody.Part?
    ): Response<Note>

    @DELETE("notes/{id}")
    suspend fun deleteNote(
        @Path("id") id: Int
    ): Response<Unit>
}
