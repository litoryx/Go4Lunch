package com.example.go4lunch.Geo;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

public class GeoViewState {
    private Double lat;
    private Double lng;
    private Boolean mUserCurrent;


    public GeoViewState(Double lat, Double lng, Boolean userCurrent) {
        this.lat = lat;
        this.lng = lng;
        this.mUserCurrent = userCurrent;
    }

    public Boolean getUserCurrent() {
        return mUserCurrent;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
