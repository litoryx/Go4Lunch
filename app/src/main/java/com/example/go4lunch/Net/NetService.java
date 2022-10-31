package com.example.go4lunch.Net;

import com.example.go4lunch.objetGoogle.CatFactsResponse;
import com.example.go4lunch.objetGoogle.PlaceDetailsResponse;
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

    @GET("facts")
    Call<CatFactsResponse> getListOfCats(@Query("page") int page);

    @GET("api/place/details/json")
    Call<PlaceDetailsResponse> getStaffFollowing(@Query("fields") String fields,
                                                 @Query("place_id") String place_id,
                                                 @Query("key") String key);
}
