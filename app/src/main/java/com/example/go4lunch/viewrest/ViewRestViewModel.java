package com.example.go4lunch.viewrest;

import android.util.Log;

import com.example.go4lunch.liststaff.UserRepository;
import com.example.go4lunch.net.NetRepository;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.RestaurantDetailViewState;
import com.example.go4lunch.objetgoogle.Place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class ViewRestViewModel extends ViewModel {

    NetRepository mNetRepository;
    UserRepository mUserRepository;

    public ViewRestViewModel(NetRepository netStaffRepository,UserRepository userRepository) {
        mNetRepository = netStaffRepository;
        mUserRepository = userRepository;

    }

    public LiveData<RestaurantDetailViewState> getRestDetail(Restaurant place){
        return Transformations.map(mNetRepository.fetchRestDetailFollowing(place.getPlace_id()),this::mapPlaceToRestaurantDetailViewState);

    }

    private RestaurantDetailViewState mapPlaceToRestaurantDetailViewState(Place placeDetails) {
        String photoUrl = null;
        if(!placeDetails.getPhotos().isEmpty()) {
            if(placeDetails.getPhotos() != null) {
                photoUrl = mUserRepository.photoReftoPhotoURl(placeDetails.getPhotos().get(0).getPhoto_reference());
            }
            }

        return new RestaurantDetailViewState(
                placeDetails.getPlace_id(),
                placeDetails.getName(),
                placeDetails.getUrl(),
                placeDetails.getFormatted_phone_number(),
                placeDetails.getVicinity(),
                photoUrl,
                false
        );
    }

    public void setUpdateRestChoose(RestaurantDetailViewState rest){

        mUserRepository.updateUserRest(rest);
    }

    public void setUpdateListFavRest(RestaurantDetailViewState rest){

        mUserRepository.updateUserRestFavoris(rest);
    }
}
