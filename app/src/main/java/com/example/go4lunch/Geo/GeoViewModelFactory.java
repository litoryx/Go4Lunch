package com.example.go4lunch.Geo;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.MainApplication;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.Net.NetServiceRetrofit;
import com.example.go4lunch.models.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GeoViewModelFactory implements ViewModelProvider.Factory {

    private static GeoViewModelFactory factory;

    public static GeoViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (GeoViewModelFactory.class) {
                if (factory == null) {
                    factory = new GeoViewModelFactory();
                }
            }
        }
        return factory;
    }

    FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainApplication.getApplication());
    private final NetRepository catFactsRepository = new NetRepository(NetServiceRetrofit.getCatApi());
    private final LocationRepository mLocationRepository = new LocationRepository(locationClient);
    private final UserRepository mUserRepository = new UserRepository();

    private GeoViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GeoViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new GeoViewModel(catFactsRepository, mLocationRepository, mUserRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
