package com.example.go4lunch.ListRest;

import android.annotation.SuppressLint;
import android.location.Location;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.objetGoogle.Place;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class ListRestViewModel extends ViewModel {
    NetRepository mNetRepository;
    UserRepository mUserRepository;
    LiveData<List<Place>> mListCurrent;
    LocationRepository mLocationRepository;
    PermissionChecker mPermissionChecker;

    LiveData<Location> locationLiveData;
    boolean hasGpsPermission;

    private final MediatorLiveData<String> mGpsMessageLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> mHasGpsPermissionLiveData = new MutableLiveData<>();


    public ListRestViewModel(NetRepository netRepository, LocationRepository locationRepository, UserRepository userRepository) {
        mNetRepository = netRepository;
        mLocationRepository = locationRepository;
        mUserRepository = userRepository;

        refresh();
        locationLiveData = locationRepository.getLocationLiveData();

            if (!hasGpsPermission) {mGpsMessageLiveData.setValue("Pas la permission des parents");}else {

                mListCurrent = Transformations.switchMap(locationLiveData, location ->
                        mNetRepository.fetchRestFollowing(location.getLatitude() + "," + location.getLongitude()));

            }
    }

    public LiveData<String> getMessageList()
        {
            return mGpsMessageLiveData;
        }

    public LiveData<List<Restaurant>> getListRest(){
        return mUserRepository.createListRest(mListCurrent, locationLiveData);
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        hasGpsPermission = mPermissionChecker.hasLocationPermission();
        mHasGpsPermissionLiveData.setValue(hasGpsPermission);

        if (hasGpsPermission) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
