package com.example.rxjava_demo.github

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager private constructor() {
    var retrofit: Retrofit

    init {
        val API_BASE_URL = "https://api.github.com/"
        val httpClient = OkHttpClient.Builder()
        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create()
            )
        retrofit = builder
            .client(httpClient.build())
            .build()
    }

    companion object {
        val instance = RetrofitManager()
    }
}