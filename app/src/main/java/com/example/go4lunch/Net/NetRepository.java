package com.example.go4lunch.Net;

import android.util.Log;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.objetGoogle.Place;
import com.example.go4lunch.objetGoogle.PlacesNearbySearchResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetRepository {

    NetService netService;

    public NetRepository(NetService netService) {
        this.netService = netService;
    }

    public LiveData<List<Place>> fetchRestFollowing(String location){

        MutableLiveData<List<Place>> nearby = new MutableLiveData<>();

        // 2.2 - Get a Retrofit instance and the related endpoints
        NetService netService = NetServiceRetrofit.getnetService();

        // 2.3 - Create the call on Github API
        Call<PlacesNearbySearchResponse> call = netService.getFollowing(location, 1500,"restaurant", BuildConfig.MAPS_API_KEY);
        // 2.4 - Start the call
        call.enqueue(new Callback<PlacesNearbySearchResponse>() {

            @Override
            public void onResponse(@NonNull Call<PlacesNearbySearchResponse> call, @NonNull Response<PlacesNearbySearchResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    nearby.setValue(response.body().getPlaces());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesNearbySearchResponse> call, @NonNull Throwable t) {
                nearby.setValue(null);
                Log.d("NetRepository","Failure getfollowing");
            }
        });
        return nearby;
    }

}
