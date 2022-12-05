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

    public static NetService getnetService(){return retrofit.create(NetService.class);}

    public static NetService getnetStaffService(){return retrofit.create(NetService.class);}

    public static NetService getCatApi() {return retrofit.create(NetService.class);}
}
