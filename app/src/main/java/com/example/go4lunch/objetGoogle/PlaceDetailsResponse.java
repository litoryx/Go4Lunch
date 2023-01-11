package com.example.go4lunch.objetGoogle;


public class PlaceDetailsResponse {

    Place result;

    public PlaceDetailsResponse(Place result) {
        this.result = result;
    }

    public Place getStaff() {
        return result;
    }
}
