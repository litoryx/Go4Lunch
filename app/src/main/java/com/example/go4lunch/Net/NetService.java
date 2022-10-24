package com.example.go4lunch.Net;

import com.example.go4lunch.objetGoogle.PlacesNearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetService {
    @GET("api/place/nearbysearch/json")
    Call<PlacesNearbySearchResponse> getFollowing(@Query("location") String location,
                                                  @Query("radius") int radius,
                                                  @Query("type") String type,
                                                  @Query("key") String key);
}
