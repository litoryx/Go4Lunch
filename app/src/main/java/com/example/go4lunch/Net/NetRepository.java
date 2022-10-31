package com.example.go4lunch.Net;

import android.util.Log;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.objetGoogle.CatFactsResponse;
import com.example.go4lunch.objetGoogle.Place;
import com.example.go4lunch.objetGoogle.PlaceDetailsResponse;
import com.example.go4lunch.objetGoogle.PlacesNearbySearchResponse;

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

    private final Map<Integer, CatFactsResponse> alreadyFetchedResponses = new HashMap<>();

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


    public LiveData<Place> fetchStaffFollowing(String fields, String place_id){
        MutableLiveData<Place> nearby = new MutableLiveData<>();

        netService = NetServiceRetrofit.getnetStaffService();

        Call<PlaceDetailsResponse> call = netService.getStaffFollowing(fields, place_id, BuildConfig.MAPS_API_KEY);

        call.enqueue(new Callback<PlaceDetailsResponse>() {

            @Override
            public void onResponse(@NonNull Call<PlaceDetailsResponse> call, @NonNull Response<PlaceDetailsResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    nearby.setValue(response.body().getStaff());
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


    public LiveData<List<Place>> getMapLiveData(int page) {
        MutableLiveData<List<Place>> catFactsMutableLiveData = new MutableLiveData<>();

        // Check in our cache if we already queried and stored the response

        CatFactsResponse response = alreadyFetchedResponses.get(page);

        if (response != null) {
            // We already have the response (because we already queried this page in the past) ! No need to call the api !
            catFactsMutableLiveData.setValue(response.getCatFacts());
        } else {
            // First time this page is queried, let's call the server ('enqueue()' makes the request on another thread)...
            netService.getListOfCats(page).enqueue(new Callback<CatFactsResponse>() {
                @Override
                public void onResponse(@NonNull Call<CatFactsResponse> call, @NonNull Response<CatFactsResponse> response) {
                    if (response.body() != null) {
                        // ... and once we have the result, we store it in our Map for potential future use !
                        alreadyFetchedResponses.put(page, response.body());

                        // Publish the result to the LiveData, we can use 'setValue()' instead of 'postValue()'
                        // because Retrofit goes back to the Main Thread once the query is finished !
                        catFactsMutableLiveData.setValue(response.body().getCatFacts());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CatFactsResponse> call, @NonNull Throwable t) {
                    catFactsMutableLiveData.setValue(null);
                }
            });
        }

        return catFactsMutableLiveData;
    }
}
