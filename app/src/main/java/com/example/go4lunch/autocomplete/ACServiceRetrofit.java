package com.example.go4lunch.autocomplete;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ACServiceRetrofit {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static AutoCompleteService getACService(){return retrofit.create(AutoCompleteService.class);}
}
