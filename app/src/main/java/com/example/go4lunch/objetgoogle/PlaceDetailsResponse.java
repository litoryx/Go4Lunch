package com.example.go4lunch.objetgoogle;


public class PlaceDetailsResponse {

    Place result;

    public PlaceDetailsResponse(Place result) {
        this.result = result;
    }

    public Place getStaff() {
        return result;
    }
}
