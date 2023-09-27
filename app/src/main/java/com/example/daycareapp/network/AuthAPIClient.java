package com.example.daycareapp.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthAPIClient {
    public static String BASE_URL = "http://20.219.162.197:8092/api/";
    public static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    //.readTimeout(45,TimeUnit.SECONDS)
                    //.writeTimeout(45,TimeUnit.SECONDS)
                    .build();
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}