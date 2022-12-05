package com.example.go4lunch.ViewRest;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.RestaurantDetailViewState;
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
    LiveData<RestaurantDetailViewState> mCurrentListRest;

    public ViewRestViewModel(NetRepository netStaffRepository,UserRepository userRepository) {
        mNetRepository = netStaffRepository;
        mUserRepository = userRepository;


    }

    public LiveData<RestaurantDetailViewState> getRestDetail(Restaurant place){

        mCurrent = mNetRepository.fetchRestDetailFollowing(place.getPlace_id());

        mCurrentListRest = mUserRepository.createPlaceToRestaurantDetail(mCurrent);

        return mCurrentListRest;}

    public void setUpdateRestChoose(RestaurantDetailViewState rest){

        mUserRepository.updateUserRest(rest);
    }

    public void setUpdateListFavRest(RestaurantDetailViewState rest){

        mUserRepository.updateUserRestFavoris(rest);
    }
}
