package com.example.go4lunch.ListStaff;

import com.example.go4lunch.ListRest.ViewModelFactory;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.Net.NetServiceRetrofit;
import com.example.go4lunch.ViewRest.ViewRestViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StaffViewModelFactory implements ViewModelProvider.Factory {

    private static StaffViewModelFactory factory;

    public static StaffViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new StaffViewModelFactory();
                }
            }
        }
        return factory;
    }

    private final NetRepository mNetRepository = new NetRepository(NetServiceRetrofit.getnetStaffService());

    private StaffViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewRestViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new ViewRestViewModel(mNetRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
