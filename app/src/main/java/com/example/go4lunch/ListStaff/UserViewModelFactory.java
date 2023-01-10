package com.example.go4lunch.ListStaff;

import android.os.Looper;

import com.example.go4lunch.ListRest.ViewModelFactory;
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

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private static UserViewModelFactory factory;
    MainApplication mMainApplication;
    Looper mLooper;

    public static UserViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new UserViewModelFactory();
                }
            }
        }
        return factory;
    }

    FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainApplication.getApplication());
    private final UserRepository mUserRepository = UserRepository.getInstance();
    private final NetRepository mNetRepository = new NetRepository(NetServiceRetrofit.getnetService());
    private final LocationRepository mLocationRepository = new LocationRepository(locationClient, mLooper);
    private final PermissionChecker mPermissionChecker = new PermissionChecker(mMainApplication);

    private UserViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StaffViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new StaffViewModel(mUserRepository, mNetRepository, mLocationRepository, mPermissionChecker);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
