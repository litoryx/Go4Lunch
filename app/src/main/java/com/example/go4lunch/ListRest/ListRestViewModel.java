package com.example.go4lunch.ListRest;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ListRestViewModel extends ViewModel {
    NetRepository mNetRepository;
    UserRepository mUserRepository;
    LiveData<List<Place>> mListCurrent;
    LocationRepository mLocationRepository;
    PermissionChecker mPermissionChecker;

    private final MediatorLiveData<String> mGpsMessageLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<Integer> mCountUserRest = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> mHasGpsPermissionLiveData = new MutableLiveData<>();

    public ListRestViewModel(NetRepository netRepository, LocationRepository locationRepository, UserRepository userRepository) {
        mNetRepository = netRepository;
        mLocationRepository = locationRepository;
        mUserRepository = userRepository;

        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();
        mGpsMessageLiveData.addSource(locationLiveData, location ->
                combine(location, mHasGpsPermissionLiveData.getValue()));
        mGpsMessageLiveData.addSource(mHasGpsPermissionLiveData, hasGpsPermission ->
                combine(locationLiveData.getValue(), hasGpsPermission)
        );

    }
    //J'ai quand meme le nombre d'utilisateur dans un restaurant qui ont cliqué à faire .

        //PS pourrait-tu regarder ceci si c'est bon pour mGpsMassageLiveData et mListCurrent d'après la logique ca devrait être bon,
        // mais je me demande si on ne peut pas passer une seule variable et que le Mediator soit celui qui est envoyé au lieu d'une Livedata
    //Les deux fonctionnent ?
    private void combine(@Nullable Location location, @Nullable Boolean hasGpsPermission) {
        if (location == null) {
            if (hasGpsPermission == null || !hasGpsPermission) {
                // Never hardcode translatable Strings, always use Context.getString(R.string.my_string) instead !
                mGpsMessageLiveData.setValue("I am lost... Should I click the permission button ?!");
            } else {
                mGpsMessageLiveData.setValue("Querying location, please wait for a few seconds...");
            }
        } else {
                mListCurrent = mNetRepository.fetchRestFollowing(location.getLatitude()+","+location.getLongitude());
        }
    }

    public LiveData<List<Place>> getList(){
        return mListCurrent;}

    private LiveData<List<Integer>> getCountUserRest(){

        return mUserRepository.CountUserSameRest(mListCurrent.getValue());
    }

    public LiveData<String> getMessageList()
        {
            return mGpsMessageLiveData;
        }

    @SuppressLint("MissingPermission")
    public void refresh() {
        boolean hasGpsPermission = mPermissionChecker.hasLocationPermission();
        mHasGpsPermissionLiveData.setValue(hasGpsPermission);

        if (hasGpsPermission) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
