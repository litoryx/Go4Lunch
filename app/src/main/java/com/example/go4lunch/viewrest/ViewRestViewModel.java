package com.example.go4lunch.viewrest;

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
        RestaurantDetailViewState detailViewState;

        if(placeDetails != null) {
            detailViewState = new RestaurantDetailViewState(
                    placeDetails.getPlace_id(),
                    placeDetails.getName(),
                    placeDetails.getUrl(),
                    placeDetails.getFormatted_phone_number(),
                    placeDetails.getVicinity(),
                    mUserRepository.photoReftoPhotoURl(placeDetails.getPhotos().get(0).getPhoto_reference()),
                    false
            );
        }else{detailViewState = new RestaurantDetailViewState(
                "0",
                "fqs",
                "cdsplaceDetails.getUrl()",
                "062543",
                "rue fsqvfv",
                "false",
                false
        );}

        return detailViewState;
    }

    public void setUpdateRestChoose(RestaurantDetailViewState rest){

        mUserRepository.updateUserRest(rest);
    }

    public void setUpdateListFavRest(RestaurantDetailViewState rest){

        mUserRepository.updateUserRestFavoris(rest);
    }
}
