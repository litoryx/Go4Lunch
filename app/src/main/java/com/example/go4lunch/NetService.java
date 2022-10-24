package com.example.go4lunch;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetService {
    @GET("api/place/nearbysearch/json")
    Call<PlacesNearbySearchResponse> getFollowing(@Query("location") String location,
                                                  @Query("radius") int radius,
                                                  @Query("type") String type,
                                                  @Query("key") String key);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
