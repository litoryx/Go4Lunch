package com.example.go4lunch.objetGoogle;

public class PlaceDetails {

    String name;
    String place_id;
    PlaceOpeningHours opening_hours;
    String adr_address;

    public PlaceDetails(String name, String place_id, PlaceOpeningHours opening_hours, String adr_address) {
        this.name = name;
        this.place_id = place_id;
        this.opening_hours = opening_hours;
        this.adr_address = adr_address;
    }

    public String getName() {
        return name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public PlaceOpeningHours getOpening_hours() {
        return opening_hours;
    }

    public String getAdr_address() {
        return adr_address;
    }
}
