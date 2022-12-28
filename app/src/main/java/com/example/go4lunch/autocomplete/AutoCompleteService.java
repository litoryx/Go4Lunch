package com.example.go4lunch.autocomplete;

import com.example.go4lunch.objetGoogle.PlacesNearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AutoCompleteService {

    @GET("api/place/autocomplete/json")
    Call<PlacesAutocompleteResponse> getPredictions(@Query("input") String input,
                                                  @Query("location")String location,
                                                  @Query("radius") int radius,
                                                  @Query("types") String types,
                                                  @Query("key") String key);
}
