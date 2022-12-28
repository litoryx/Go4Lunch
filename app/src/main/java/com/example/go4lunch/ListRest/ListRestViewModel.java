package com.example.go4lunch.ListRest;

import android.annotation.SuppressLint;
import android.location.Location;

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
    LiveData<List<Restaurant>> viewState;
    LocationRepository mLocationRepository;
    PermissionChecker mPermissionChecker;

    LiveData<Location> locationLiveData;
    LiveData<String> locString;
    boolean hasGpsPermission;

    private final MediatorLiveData<String> mGpsMessageLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> mHasGpsPermissionLiveData = new MutableLiveData<>();


    public ListRestViewModel(NetRepository netRepository, LocationRepository locationRepository,
                             UserRepository userRepository, PermissionChecker permissionChecker) {
        mNetRepository = netRepository;
        mLocationRepository = locationRepository;
        mUserRepository = userRepository;
        mPermissionChecker = permissionChecker;

        refresh();
        locationLiveData = locationRepository.getLocationLiveDatafft();
        locString = locationRepository.getLocationLiveData();

        mListCurrent = Transformations.switchMap(locString, location ->
                mNetRepository.fetchRestFollowing(location));

        viewState = Transformations.switchMap(mListCurrent, list ->
                mUserRepository.createListRest(mListCurrent, locationLiveData));

    }

    public LiveData<String> getMessageList() {
        return mGpsMessageLiveData;
    }

    public LiveData<List<Restaurant>> getListRest() {
        return viewState;
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
