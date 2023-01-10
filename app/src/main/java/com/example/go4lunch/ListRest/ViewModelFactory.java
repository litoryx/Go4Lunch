package com.example.go4lunch.ListRest;

import android.os.Looper;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.MainApplication;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.Net.NetServiceRetrofit;
import com.example.go4lunch.models.PermissionChecker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    Looper mLooper;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainApplication.getApplication());
    private final NetRepository mNetRepository = new NetRepository(NetServiceRetrofit.getnetService());
    private final LocationRepository mLocationRepository= new LocationRepository(locationClient, mLooper);
    private final UserRepository mUserRepository = UserRepository.getInstance();
    private final PermissionChecker mPermissionChecker = new PermissionChecker(MainApplication.getApplication());

    private ViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListRestViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new ListRestViewModel(mNetRepository, mLocationRepository, mUserRepository, mPermissionChecker);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
