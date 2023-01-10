package com.example.go4lunch;

import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.autocomplete.AutoCompleteRepository;
import com.example.go4lunch.autocomplete.PlaceAutocompletePrediction;
import com.example.go4lunch.autocomplete.Prediction;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    AutoCompleteRepository mAutoCompleteRepository;
    LocationRepository mLocationRepository;
    LiveData<List<Prediction>> mListPredictionLive;

    @Nullable
    String location;

    public MainViewModel(AutoCompleteRepository autoCompleteRepository, LocationRepository locationRepository) {
        mAutoCompleteRepository = autoCompleteRepository;
        mLocationRepository = locationRepository;

        mLocationRepository.getLocationLiveData().observeForever(s -> {
            location = s;

        });
        mListPredictionLive = autoCompleteRepository.getListPredictionLiveData();

    }

    public LiveData<List<Prediction>> getPredictions(){
        return mListPredictionLive;
    }

    public void updateSearchText(String text){
        if(location != null) {
            mAutoCompleteRepository.updateSearch(text, location);

        }
    }

}
