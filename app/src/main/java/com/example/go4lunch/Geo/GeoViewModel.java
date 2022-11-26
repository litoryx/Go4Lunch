package com.example.go4lunch.Geo;

import android.location.Location;

import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
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

    public GeoViewModel(NetRepository netRepository, LocationRepository locationRepository) {
        mNetRepository = netRepository;
        mLocationRepository = locationRepository;


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

        if (places != null) {
            for (Place place : places) {
                GeoViewState mGeoViewState = new GeoViewState(place.getGeometry().getLatLngLiteral().getLat(),
                        place.getGeometry().getLatLngLiteral().getLng());
                mGeoList.add(mGeoViewState);
            }
        }
        return mGeoList;
    }


}
