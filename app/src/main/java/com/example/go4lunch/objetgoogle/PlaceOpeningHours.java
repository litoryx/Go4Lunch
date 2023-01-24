package com.example.go4lunch.objetgoogle;

import java.util.List;

import androidx.annotation.Nullable;

public class PlaceOpeningHours {

    boolean open_now;
    List<PlaceOpeningHoursPeriod> periods;

    public boolean isOpen_now() {
        return open_now;
    }

    @Nullable
    public List<PlaceOpeningHoursPeriod> getPeriods() {
        if(periods != null){return periods;}else{return null;}
    }
}
