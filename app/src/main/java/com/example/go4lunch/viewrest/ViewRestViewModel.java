package com.example.go4lunch.viewrest;

import android.util.Log;

import com.example.go4lunch.liststaff.UserRepository;
import com.example.go4lunch.net.NetRepository;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.RestaurantDetailViewState;
import com.example.go4lunch.objetgoogle.Place;

import androidx.lifecycle.LiveData;
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
        Log.d("placeDetail","Id:"+place.getPlace_id());
        mCurrent = mNetRepository.fetchRestDetailFollowing(place.getPlace_id());

        mCurrentListRest = mUserRepository.createPlaceToRestaurantDetail(mCurrent.getValue());

        return mCurrentListRest;}

    public void setUpdateRestChoose(RestaurantDetailViewState rest){

        mUserRepository.updateUserRest(rest);
    }

    public void setUpdateListFavRest(RestaurantDetailViewState rest){

        mUserRepository.updateUserRestFavoris(rest);
    }
}
