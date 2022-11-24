package com.example.go4lunch.ViewRest;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewRestViewModel extends ViewModel {

    NetRepository mNetRepository;
    UserRepository mUserRepository;
    LiveData<Place> mCurrent;

    public ViewRestViewModel(NetRepository netStaffRepository,UserRepository userRepository) {
        mNetRepository = netStaffRepository;
        mUserRepository = userRepository;

    }

    public LiveData<Place> getRestDetail(Place place){

        mCurrent = mNetRepository.fetchRestDetailFollowing(place.getPlace_id());

        return mCurrent;}

    public LiveData<Place> getUpdateUserRest(Place place){

            mUserRepository.updateUserRest(place);
            mCurrent = getRestDetail(place);

            return mCurrent;
    }

    public LiveData<List<User>> getListUserSameRest(Place place){

        return mUserRepository.getUserSameRest(place);
    }
}
