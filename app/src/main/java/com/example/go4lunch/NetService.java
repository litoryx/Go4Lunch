package com.example.go4lunch;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetService {
    @GET("?keyword=cruise" +
            "  &location={restGived}" +
            "  &radius=1500" +
            "  &type=restaurant" +
            "  &key=AIzaSyD1gUR0fmB0GVyO0UKPLV1snMAY9KqUTjw")
    Call<List<Restaurant>> getFollowing(@Path("restGived") String restGived);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
