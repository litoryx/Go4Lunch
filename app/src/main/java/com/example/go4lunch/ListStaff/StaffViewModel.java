package com.example.go4lunch.ListStaff;

import android.location.Location;

import com.example.go4lunch.MainActivity;
import com.example.go4lunch.Net.GeoLocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;
import com.google.android.gms.tasks.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class StaffViewModel extends ViewModel {
    UserRepository mUserRepository;
    NetRepository mNetRepository;
    MutableLiveData<List<User>> mListUserLD;

    public StaffViewModel(UserRepository userRepository, NetRepository netRepository) {
        mUserRepository = userRepository;
        mNetRepository = netRepository;

        mListUserLD = new MutableLiveData<>(mUserRepository.getUserData());
        LiveData<List<Place>> mListResto = mNetRepository.fetchRestFollowing("48.864033,2.368425");
    }

    public void getComparaisonLocation(List<User> users,String nameR){

    }


    public LiveData<List<User>> getUser(){return mListUserLD;}
}
