package com.example.go4lunch.models;

import java.util.List;

import androidx.annotation.Nullable;

public class User {

    private String uid;
    private String username;
    private RestaurantChoose restaurantChoose;
    private String mail;
    private List<String> mListFav;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User() { }

    public List<String> getListFav() {
        return mListFav;
    }

    public User(String uid, String username, RestaurantChoose restaurantChoose, String mail, List<String> listFav) {
        this.uid = uid;
        this.username = username;
        this.restaurantChoose = restaurantChoose;
        this.mail = mail;
        this.mListFav = listFav;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RestaurantChoose getRestaurantChoose() {
        return restaurantChoose;
    }

    public void setRestaurantChoose(RestaurantChoose restaurantChoose) {
        this.restaurantChoose = restaurantChoose;
    }
}
