package com.example.go4lunch.objetgoogle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import androidx.annotation.NonNull;

public class Place implements Parcelable {

    String name;
    Geometry geometry;
    String url;
    PlaceOpeningHours opening_hours;
    String formatted_phone_number;
    String vicinity;
    String place_id;
    List<PlacePhoto> photos;

    public List<PlacePhoto> getPhotos() {
        return photos;
    }

    public Place(Parcel in) {
        name = in.readString();
        url = in.readString();
        formatted_phone_number = in.readString();
        vicinity = in.readString();
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

    public String getVicinity() {
        return vicinity;
    }

    public String getUrl() {
        return url;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
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
        parcel.writeString(url);
        parcel.writeString(formatted_phone_number);
        parcel.writeString(vicinity);
        parcel.writeString(place_id);
    }
}
