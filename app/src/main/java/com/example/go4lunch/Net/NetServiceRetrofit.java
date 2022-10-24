package com.example.go4lunch.Net;

import com.example.go4lunch.Net.NetService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetServiceRetrofit {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static NetService getnetService(){return retrofit.create(NetService.class);}
}
