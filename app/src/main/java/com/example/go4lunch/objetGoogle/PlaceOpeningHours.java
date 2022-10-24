package com.example.go4lunch.objetGoogle;

import java.util.List;

public class PlaceOpeningHours {

    boolean open_now;
    List<PlaceOpeningHoursPeriod> periods;

    public boolean isOpen_now() {
        return open_now;
    }

    public List<PlaceOpeningHoursPeriod> getPeriods() {
        return periods;
    }
}
