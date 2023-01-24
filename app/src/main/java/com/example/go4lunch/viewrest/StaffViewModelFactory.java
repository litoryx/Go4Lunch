package com.example.go4lunch.viewrest;

import com.example.go4lunch.listrest.ViewModelFactory;
import com.example.go4lunch.liststaff.UserRepository;
import com.example.go4lunch.net.NetRepository;
import com.example.go4lunch.net.NetServiceRetrofit;

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
    private final UserRepository mUserRepository = UserRepository.getInstance();

    private StaffViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewRestViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new ViewRestViewModel(mNetRepository, mUserRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
