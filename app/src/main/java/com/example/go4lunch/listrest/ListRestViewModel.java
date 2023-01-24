package com.example.go4lunch.listrest;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import com.example.go4lunch.liststaff.UserRepository;
import com.example.go4lunch.net.LocationRepository;
import com.example.go4lunch.net.NetRepository;
import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetgoogle.Place;

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
    private final MediatorLiveData<List<Restaurant>> mGeoViewStateLiveData = new MediatorLiveData<>();
    LiveData<Location> locationLiveData;
    LiveData<String> locString;
    boolean hasGpsPermission;

    private final MediatorLiveData<String> mGpsMessageLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> mHasGpsPermissionLiveData = new MutableLiveData<>();
    private final MediatorLiveData<List<Restaurant>> viewstate = new MediatorLiveData<>();

    public ListRestViewModel(NetRepository netRepository, LocationRepository locationRepository,
                             UserRepository userRepository, PermissionChecker permissionChecker) {
        mNetRepository = netRepository;
        mLocationRepository = locationRepository;
        mUserRepository = userRepository;
        mPermissionChecker = permissionChecker;

        refresh();
        locationLiveData = locationRepository.getLocationLiveDatafft();
        locString = locationRepository.getLocationLiveData();
        LiveData<List<User>> usersLivedata = userRepository.getUserData();

        mListCurrent = Transformations.switchMap(locString, location ->
                mNetRepository.fetchRestFollowing(location));

        viewstate.addSource(mListCurrent, places -> {
            combine(places, usersLivedata.getValue());
        });

        viewstate.addSource(usersLivedata, users -> {
            combine(mListCurrent.getValue(), users);
        });

    }

    public void combine(List<Place> places, List<User> users){
        if(places == null || users == null){return;}
        List<Restaurant> rest = mUserRepository.createListRest(places, locationLiveData.getValue(), users);
        if(rest.size() == 0){Log.d("error","0 dans la liste restaurant");}
        viewstate.setValue(rest);
    }

    public LiveData<String> getMessageList() {
        return mGpsMessageLiveData;
    }

    public LiveData<List<Restaurant>> getListRest() {
        return viewstate;
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        hasGpsPermission = mPermissionChecker.hasLocationPermission();

        if (hasGpsPermission) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
