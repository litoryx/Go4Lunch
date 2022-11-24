package com.example.go4lunch.ListStaff;

import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.models.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class StaffViewModel extends ViewModel {
    UserRepository mUserRepository;
    NetRepository mNetRepository;
    LiveData<List<User>> mListUserLD;

    public StaffViewModel(UserRepository userRepository, NetRepository netRepository, LocationRepository locationRepository, PermissionChecker permissionChecker) {
        mUserRepository = userRepository;
        mNetRepository = netRepository;

        mListUserLD = mUserRepository.getUserData();
    }

    public void getComparaisonLocation(List<User> users,String nameR){

    }


    public LiveData<List<User>> getUsers(){return mListUserLD;}
}
