package com.example.mvvm.network

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteInteractor {
    fun getData(): Call<NetworkModel> = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build()
        .create(NoteAPI::class.java)
        .getData()


    companion object {
        private const val BASE_URL =
            "https://jsonplaceholder.typicode.com"
    }

}


