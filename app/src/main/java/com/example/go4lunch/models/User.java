package com.example.go4lunch.models;

import androidx.annotation.Nullable;

public class User {

    private String uid;
    private String username;
    private RestaurantChoose restaurantChoose;

    public User() { }

    public User(String uid, String username, RestaurantChoose restaurantChoose) {
        this.uid = uid;
        this.username = username;
        this.restaurantChoose = restaurantChoose;
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
