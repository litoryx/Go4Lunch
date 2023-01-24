package com.example.go4lunch.net;

import android.util.Log;

import com.example.go4lunch.BuildConfig;

import com.example.go4lunch.objetgoogle.Place;
import com.example.go4lunch.objetgoogle.PlaceDetailsResponse;
import com.example.go4lunch.objetgoogle.PlacesNearbySearchResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetRepository {

    NetService netService;
    Map<String, List<Place>> mCache = new HashMap<>();

    public NetRepository(NetService netService) {
        this.netService = netService;
    }

    public LiveData<List<Place>> fetchRestFollowing(String location){

        MutableLiveData<List<Place>> nearby = new MutableLiveData<>();

        // 2.2 - Get a Retrofit instance and the related endpoints
        NetService netService = NetServiceRetrofit.getnetService();

        if(mCache.get(location) != null){
            nearby.setValue(mCache.get(location));
        }else {

            // 2.3 - Create the call on Github API
            Call<PlacesNearbySearchResponse> call = netService.getFollowing(location, 1500, "restaurant", BuildConfig.MAPS_API_KEY);
            // 2.4 - Start the call
            call.enqueue(new Callback<PlacesNearbySearchResponse>() {

                @Override
                public void onResponse(@NonNull Call<PlacesNearbySearchResponse> call, @NonNull Response<PlacesNearbySearchResponse> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        nearby.setValue(response.body().getPlaces());
                        mCache.put(location, response.body().getPlaces());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PlacesNearbySearchResponse> call, @NonNull Throwable t) {
                    nearby.setValue(null);
                    Log.d("NetRepository", "Failure getfollowing");
                }
            });
        }
        return nearby;
    }



    public LiveData<Place> fetchRestDetailFollowing(String place_id){

        MutableLiveData<Place> nearby = new MutableLiveData<>();

        netService = NetServiceRetrofit.getnetStaffService();

        Call<PlaceDetailsResponse> call = netService.getStaffFollowing(place_id, BuildConfig.MAPS_API_KEY);

        call.enqueue(new Callback<PlaceDetailsResponse>() {

            @Override
            public void onResponse(@NonNull Call<PlaceDetailsResponse> call, @NonNull Response<PlaceDetailsResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    nearby.setValue(response.body().getStaff());
                    Log.d("placeDetail","recup réussi");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceDetailsResponse> call, @NonNull Throwable t) {
                nearby.setValue(null);
                Log.d("NetStaffRepository","Failure getStafffollowing");
            }
        });
        return nearby;
    }

}
