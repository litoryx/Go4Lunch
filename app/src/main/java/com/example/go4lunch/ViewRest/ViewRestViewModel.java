package com.example.go4lunch.ViewRest;

import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.objetGoogle.Place;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewRestViewModel extends ViewModel {

    NetRepository mNetRepository;
    LiveData<Place> mCurrent;

    public ViewRestViewModel(NetRepository netStaffRepository) {
        mNetRepository = netStaffRepository;

    }

    public LiveData<Place> getStaffDetail(Place place){

                mCurrent = mNetRepository.fetchStaffFollowing(place.getPlace_id());

        return mCurrent;}
}
