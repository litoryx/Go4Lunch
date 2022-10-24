package com.example.go4lunch;

import java.util.List;

import androidx.annotation.NonNull;

public class Place {



    String name;

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                '}';
    }
}
