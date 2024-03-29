package com.example.go4lunch.net;

import com.example.go4lunch.objetgoogle.PlaceDetailsResponse;
import com.example.go4lunch.objetgoogle.PlacesNearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetService {
    @GET("api/place/nearbysearch/json")
    Call<PlacesNearbySearchResponse> getFollowing(@Query("location") String location,
                                                  @Query("radius") int radius,
                                                  @Query("type") String type,
                                                  @Query("key") String key);

    @GET("api/place/details/json")
    Call<PlaceDetailsResponse> getStaffFollowing(@Query("place_id") String place_id,
                                                 @Query("key") String key);

}
