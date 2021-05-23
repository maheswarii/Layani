package com.yps.layani.admin.api

import retrofit2.Retrofit

object ApiService {
    private val TAG = "--ApiService"

    private const val BASE_URL = "https://164f92fa7be0.ngrok.io/"

    fun loginApiCall() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ApiWorker.gsonConverter)
        .client(ApiWorker.client)
        .build()
        .create(ListApi::class.java)!!
}