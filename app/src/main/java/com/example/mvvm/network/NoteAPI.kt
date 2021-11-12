package com.example.mvvm.network

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface NoteAPI {
    @GET("/posts/1")
    fun getData(): Call<NetworkModel>
}

