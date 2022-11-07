package com.example.go4lunch.Geo;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

public class GeoViewState {
    private Double lat;
    private Double lng;

    public GeoViewState(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
