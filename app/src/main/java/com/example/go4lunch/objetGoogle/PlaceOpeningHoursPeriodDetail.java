package com.example.go4lunch.objetGoogle;

public class PlaceOpeningHoursPeriodDetail {

    Number day;
    String date;
    String time;
    boolean truncated;

    public Number getDay() {
        if(day != null){return day;}else{return null;}
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        if(time != null){return time;}else{return null;}
    }

    public boolean isTruncated() {
        return truncated;
    }
}
