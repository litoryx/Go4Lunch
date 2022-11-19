package com.example.go4lunch.objetGoogle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import androidx.annotation.NonNull;

public class Place implements Parcelable {

    String name;
    Geometry geometry;
    PlaceOpeningHours opening_hours;
    String adr_address;
    String place_id;
    List<PlacePhoto> photos;

    protected Place(Parcel in) {
        name = in.readString();
        adr_address = in.readString();
        place_id = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getPlace_id() { return place_id; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(adr_address);
        parcel.writeString(place_id);
    }
}
