package com.example.go4lunch.models;

import java.util.List;

import androidx.annotation.Nullable;

public class User {

    private String uid;
    private String username;
    private RestaurantChoose restaurantChoose;
    private String mail;
    private List<String> mListFav;
    private String photoUrl;
    private boolean isMentor;

    public boolean getIsMentor() {
        return isMentor;
    }

    public String getUrlPicture() {
        return photoUrl;
    }


    public String getMail() {
        return mail;
    }

    public User() { }

    public List<String> getListFav() {
        return mListFav;
    }

    public User(String uid, String username, RestaurantChoose restaurantChoose, String mail, List<String> listFav, String photoUrl) {
        this.uid = uid;
        this.username = username;
        this.restaurantChoose = restaurantChoose;
        this.mail = mail;
        this.mListFav = listFav;
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public RestaurantChoose getRestaurantChoose() {
        return restaurantChoose;
    }

}
