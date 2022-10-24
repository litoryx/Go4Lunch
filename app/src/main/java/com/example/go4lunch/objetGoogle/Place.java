package com.example.go4lunch.objetGoogle;

import androidx.annotation.NonNull;

public class Place {

    String name;
    Geometry geometry;
    PlaceOpeningHours opening_hours;
    String adr_address;

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public PlaceOpeningHours getOpening_hours() {
        return opening_hours;
    }

    public String getAdr_address() {
        return adr_address;
    }

    @NonNull
    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                '}';
    }
}
