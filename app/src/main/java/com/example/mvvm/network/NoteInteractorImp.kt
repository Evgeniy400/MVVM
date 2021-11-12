package com.example.mvvm.network

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteInteractorImp : NoteInteractor {
    private val apiRequest = Retrofit.Builder()
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

    override fun getData(success: (NetworkModel) -> Unit, failure: () -> Unit) {
        apiRequest.getData().enqueue(
            object : Callback<NetworkModel> {
                override fun onResponse(
                    call: Call<NetworkModel>,
                    response: Response<NetworkModel>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { success(it) } ?: failure()
                    } else {
                        failure()
                    }
                }

                override fun onFailure(call: Call<NetworkModel>, t: Throwable) {
                    failure()
                }
            })
    }


    companion object {
        private const val BASE_URL =
            "https://jsonplaceholder.typicode.com"
    }

}


