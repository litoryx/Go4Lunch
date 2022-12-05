package com.example.go4lunch.models;

import java.util.List;

public class RestaurantDetailViewState {

    String place_id;
    String name;
    String url;
    String formatted_phone_number;
    String adr_address;
    String photo;
    List<User> mUserCurrentRest;
    boolean mFav;

    public boolean getFav() {
        return mFav;
    }

    public void setFav(boolean fav) {
        mFav = fav;
    }

    String restCurrent;

    public String getAdr_address() {
        return adr_address;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public void setAdr_address(String adr_address) {
        this.adr_address = adr_address;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setUserCurrentRest(List<User> userCurrentRest) {
        mUserCurrentRest = userCurrentRest;
    }



    public String getPlace_id() {
        return place_id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public String getPhoto() {
        return photo;
    }

    public List<User> getUserCurrentRest() {
        return mUserCurrentRest;
    }

    public String getRestCurrent() {
        return restCurrent;
    }

    public void setRestCurrent(String restCurrent) {
        this.restCurrent = restCurrent;
    }
}
