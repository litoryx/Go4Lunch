package com.example.go4lunch.Net;

import com.example.go4lunch.Net.NetService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetServiceRetrofit {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final Gson gson = new GsonBuilder().setLenient().create();
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static final String BASE_URL = "https://catfact.ninja/";
    private static final Retrofit retrofit3 = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static NetService getnetService(){return retrofit.create(NetService.class);}

    public static NetService getnetStaffService(){return retrofit2.create(NetService.class);}

    public static NetService getCatApi() {
        return retrofit.create(NetService.class);
    }
}
