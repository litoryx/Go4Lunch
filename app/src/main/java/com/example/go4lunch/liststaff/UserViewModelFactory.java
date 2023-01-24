package com.example.go4lunch.liststaff;

import com.example.go4lunch.listrest.ViewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private static UserViewModelFactory factory;

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

    private final UserRepository mUserRepository = UserRepository.getInstance();

    private UserViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StaffViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new StaffViewModel(mUserRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
