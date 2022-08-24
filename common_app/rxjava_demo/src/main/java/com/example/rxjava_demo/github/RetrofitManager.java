package com.example.rxjava_demo.github;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    Retrofit retrofit;
    private final static RetrofitManager RETROFIT_MANAGER = new RetrofitManager();

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static RetrofitManager getInstance() {
        return RETROFIT_MANAGER;
    }

    private RetrofitManager() {
        String API_BASE_URL = "https://api.github.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        this.retrofit = builder
                .client(httpClient.build())
                .build();
    }
}
