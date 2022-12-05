package com.example.go4lunch.Geo;

import android.location.Location;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class GeoViewModel extends ViewModel {

    private final LiveData<List<GeoViewState>> mGeoViewStateLiveData;
    NetRepository mNetRepository;
    LocationRepository mLocationRepository;
    UserRepository mUserRepository;
    Boolean mBoo;

    public GeoViewModel(NetRepository netRepository, LocationRepository locationRepository, UserRepository userRepository) {
        mNetRepository = netRepository;
        mLocationRepository = locationRepository;
        mUserRepository = userRepository;

        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();

        mGeoViewStateLiveData = Transformations.switchMap(locationLiveData, location ->
                Transformations.map(mNetRepository.fetchRestFollowing(location.getLatitude() + "," + location.getLongitude()), this::mapDataToViewState));
    }

    // This is the "final product" of our ViewModel : every data needed from the view is in this LiveData
    public LiveData<List<GeoViewState>> getViewStateLiveData() {
        return mGeoViewStateLiveData;
    }


    public List<GeoViewState> mapDataToViewState(@Nullable List<Place> places) {
        List<GeoViewState> mGeoList = new ArrayList<>();
        int i = 0;

        if (places != null) {
            for (Place place : places) {
                if(mUserRepository.getUserData().getValue() != null) {
                    mBoo = place.getName().equals(mUserRepository.getUserData().getValue().get(i).getRestaurantChoose().getName());
                }
                    GeoViewState mGeoViewState = new GeoViewState(place.getGeometry().getLatLngLiteral().getLat(),
                            place.getGeometry().getLatLngLiteral().getLng(),mBoo);
                    mGeoList.add(mGeoViewState);

            }
        }
        return mGeoList;
    }


}
