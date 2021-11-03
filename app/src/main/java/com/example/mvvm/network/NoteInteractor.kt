package com.example.mvvm.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteInteractor {
    suspend fun getNote(): Call<NoteModel> = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NoteAPI::class.java)
        .getNote()



    companion object {
        const val BASE_URL = "https://firebasestorage.googleapis.com/v0/b/course-3bf7b.appspot.com/o/"
    }

}


