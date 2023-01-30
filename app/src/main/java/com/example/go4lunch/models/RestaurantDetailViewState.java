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

    public RestaurantDetailViewState(String place_id, String name, String url, String formatted_phone_number, String adr_address, String photo, boolean fav) {
        this.place_id = place_id;
        this.name = name;
        this.url = url;
        this.formatted_phone_number = formatted_phone_number;
        this.adr_address = adr_address;
        this.photo = photo;
        mFav = fav;
    }

    public boolean getFav() {
        return mFav;
    }

    public void setFav(boolean fav) {
        mFav = fav;
    }

    String restCurrent;

    public String getAdr_address() {
    if(adr_address != null){return adr_address;}else{return null;}
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
        if (place_id != null) {
            return place_id;
        }else{return null;}
    }

    public String getName() {
        if (name != null) {
            return name;
        }else{return null;}
    }

    public String getUrl() {
        if (url != null) {
            return url;
        }else{return null;}
    }

    public String getFormatted_phone_number() {
            if (formatted_phone_number != null) {
                return formatted_phone_number;
        }else{return null;}

    }

    public String getPhoto() {
        if (formatted_phone_number != null) {
            return photo;
        }else{return null;}
    }

    public List<User> getUserCurrentRest() {
        if (formatted_phone_number != null) {
        return mUserCurrentRest;
        }else{return null;}
    }

    public String getRestCurrent() {
            if (formatted_phone_number != null) {
        return restCurrent;
            }else{return null;}
    }

    public void setRestCurrent(String restCurrent) {
        this.restCurrent = restCurrent;
    }
}
