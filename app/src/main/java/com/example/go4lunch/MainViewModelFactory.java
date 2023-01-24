package com.example.go4lunch;

import android.os.Looper;

import com.example.go4lunch.listrest.ViewModelFactory;
import com.example.go4lunch.net.LocationRepository;
import com.example.go4lunch.autocomplete.ACServiceRetrofit;
import com.example.go4lunch.autocomplete.AutoCompleteRepository;
import com.example.go4lunch.models.PermissionChecker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private static MainViewModelFactory factory;
    MainApplication mMainApplication;
    Looper mLooper;

    public static MainViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new MainViewModelFactory();
                }
            }
        }
        return factory;
    }


    FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainApplication.getApplication());
    private final LocationRepository mLocationRepository = new LocationRepository(locationClient, mLooper);
    private final PermissionChecker mPermissionChecker = new PermissionChecker(mMainApplication);
    private final AutoCompleteRepository mAutoCompleteRepository = new AutoCompleteRepository(ACServiceRetrofit.getACService());

    private MainViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new MainViewModel(mAutoCompleteRepository, mLocationRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
