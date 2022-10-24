package com.example.go4lunch;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetRepository {

    public LiveData<List<Place>> fetchRestFollowing(String location){

        MutableLiveData<List<Place>> nearby = new MutableLiveData<>();

        // 2.2 - Get a Retrofit instance and the related endpoints
        NetService netService = NetService.retrofit.create(NetService.class);

        // 2.3 - Create the call on Github API
        Call<PlacesNearbySearchResponse> call = netService.getFollowing(location, 1500,"restaurant",BuildConfig.MAPS_API_KEY);
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
