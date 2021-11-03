package com.example.mvvm.network

import retrofit2.http.GET
import retrofit2.Call

interface NoteAPI {
    @GET("note.json")
    suspend fun getNote(): Call<NoteModel>
}