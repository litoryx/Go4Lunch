package com.example.go4lunch.liststaff;

import com.example.go4lunch.models.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class StaffViewModel extends ViewModel {
    UserRepository mUserRepository;

    public StaffViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public LiveData<List<User>> getUsers(){return mUserRepository.getUserData();}
}
