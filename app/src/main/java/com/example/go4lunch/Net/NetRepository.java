package com.example.go4lunch.Net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.objetGoogle.Place;
import com.example.go4lunch.objetGoogle.PlaceDetailsResponse;
import com.example.go4lunch.objetGoogle.PlacesNearbySearchResponse;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;

import java.util.Collections;
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


    public LiveData<Place> fetchRestDetailFollowing(String place_id){
        MutableLiveData<Place> nearby = new MutableLiveData<>();

        netService = NetServiceRetrofit.getnetStaffService();

        Call<PlaceDetailsResponse> call = netService.getStaffFollowing(place_id, BuildConfig.MAPS_API_KEY);

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

    //Cette fonction est le code pris sur Google platform pour normalement faire appel au placephoto via le place_id du place, mais comme tu peux le voir beaucoup de rouge,
    //alors est ce une autre methode pour récupérer les images que tu connaitrais ?
    public void recupModifPhotoGoogle(List<Place> places, String place_Id, ImageView imageView){
        //En ai-je besoin de cette ligne
        final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);

        final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(place_Id, fields);
        //places c'est quoi comme objet ce serai super que google nous le disent
        places.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            final Place place = response.getPlace();


            final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
            if (metadata == null || metadata.isEmpty()) {
                return;
            }
            final PhotoMetadata photoMetadata = metadata.get(0);


            final String attributions = photoMetadata.getAttributions();


            final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(500) // Optional.
                    .setMaxHeight(300) // Optional.
                    .build();
            places.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap bitmap = fetchPhotoResponse.getBitmap();
                imageView.setImageBitmap(bitmap);
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    final ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                    // TODO: Handle error with given status code.
                }
            });
        });

    }
}
