package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.net.LocationRepository;
import com.example.go4lunch.autocomplete.AutoCompleteRepository;
import com.example.go4lunch.autocomplete.Prediction;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    AutoCompleteRepository mAutoCompleteRepository;
    LocationRepository mLocationRepository;
    LiveData<List<Prediction>> mListPredictionLive;
    PermissionChecker mPermissionChecker;

    @Nullable
    String location;

    public MainViewModel(AutoCompleteRepository autoCompleteRepository, LocationRepository locationRepository, PermissionChecker permissionChecker) {
        mAutoCompleteRepository = autoCompleteRepository;
        mLocationRepository = locationRepository;
        mPermissionChecker = permissionChecker;

        mLocationRepository.getLocationLiveData().observeForever(s -> {
            location = s;

        });
        mListPredictionLive = autoCompleteRepository.getListPredictionLiveData();

    }

    public LiveData<List<Prediction>> getPredictions(){
        return mListPredictionLive;
    }

    public void updateSearchText(String text){
        Log.d("geoview",""+location);
        if(location != null) {
            mAutoCompleteRepository.updateSearch(text, location);

        }
    }


    @SuppressLint("MissingPermission")
    public void refresh() {

        if (mPermissionChecker.hasLocationPermission()) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
