package com.example.go4lunch.objetGoogle;

import com.example.go4lunch.objetGoogle.Place;

import java.util.ArrayList;
import java.util.List;

public class PlacesNearbySearchResponse {

    List<Place> results;

    public PlacesNearbySearchResponse(List<Place> results) {
        this.results = results;
    }

    public List<Place> getPlaces() {
        return results;
    }
}
