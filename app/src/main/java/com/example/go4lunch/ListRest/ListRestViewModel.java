package com.example.go4lunch.ListRest;

import android.annotation.SuppressLint;
import android.location.Location;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.PermissionChecker;
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
    List<Float> mListLocationFloatRest;
    Float mDistance;
    LiveData<List<Float>> mDistances;
    LiveData<Location> locationLiveData;
    boolean hasGpsPermission;

    private final MediatorLiveData<String> mGpsMessageLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<List<Float>> mDistancesMedia = new MediatorLiveData<>();
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

                mDistances = Transformations.switchMap(mListCurrent, this::createListDistancesRestUser);
            }
    }

    public LiveData<List<Place>> getList(){
        return mListCurrent;}

    public LiveData<List<Integer>> getCountUserRest(){
        return mUserRepository.CountUserSameRest(mListCurrent.getValue());
    }

    public LiveData<List<Float>> createListDistancesRestUser(List<Place> places){
        int mSize = places.size();

        for (int i = 0;i <= mSize;i++){
            Double mLat = places.get(i).getGeometry().getLatLngLiteral().getLat();
            Double mLng = places.get(i).getGeometry().getLatLngLiteral().getLng();
            Location locationR = new Location("Rest"+i);
            Location currentLocation = locationLiveData.getValue();

            locationR.setLongitude(mLng);
            locationR.setLatitude(mLat);
            if(currentLocation != null){mDistance = currentLocation.distanceTo(locationR);}
            mListLocationFloatRest.add(mDistance);
        }
        mDistancesMedia.setValue(mListLocationFloatRest);
        return mDistancesMedia;
    }

    public LiveData<List<Float>> getListDistancesRestUser(){

        return mDistances;
    }

    public LiveData<String> getMessageList()
        {
            return mGpsMessageLiveData;
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
