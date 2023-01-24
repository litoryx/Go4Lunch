package com.example.go4lunch.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Restaurant implements Parcelable {

    String name;
    String url;
    String formatted_phone_number;
    boolean open_now;
    String formatted_address;
    String place_id;
    String photo;
    Integer numbers_user_rest;
    Float distanceRest;

    public Restaurant(String name, String url, String formatted_phone_number,
                      boolean open_now, String formatted_address, String place_id,
                      String photo, Integer numbers_user_rest, Float distanceRest) {

        this.name = name;
        this.url = url;
        this.formatted_phone_number = formatted_phone_number;
        this.open_now = open_now;
        this.formatted_address = formatted_address;
        this.place_id = place_id;
        this.photo = photo;
        this.numbers_user_rest = numbers_user_rest;
        this.distanceRest = distanceRest;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Restaurant(Parcel in) {
        name = in.readString();
        url = in.readString();
        formatted_address = in.readString();
        open_now = in.readBoolean();
        formatted_phone_number = in.readString();
        place_id = in.readString();
        photo = in.readString();
        numbers_user_rest = in.readInt();
        distanceRest = in.readFloat();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public Integer getNumbers_user_rest() {
        return numbers_user_rest;
    }

    public void setNumbers_user_rest(Integer numbers_user_rest) {
        this.numbers_user_rest = numbers_user_rest;
    }

    public Float getDistanceRest() {
        return distanceRest;
    }

    public void setDistanceRest(Float distanceRest) {
        this.distanceRest = distanceRest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public boolean getOpen() {
        return open_now;
    }

    public void setOpening_hours(boolean opening_hours) {
        this.open_now = opening_hours;
    }

    public String getAdr() {
        return formatted_address;
    }

    public void setAdr_address(String adr_address) {
        this.formatted_address = adr_address;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(formatted_address);
        parcel.writeString(formatted_phone_number);
        parcel.writeString(place_id);
        parcel.writeString(photo);
        if (numbers_user_rest == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(numbers_user_rest);
        }
        if (distanceRest == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(distanceRest);
        }
    }
}
