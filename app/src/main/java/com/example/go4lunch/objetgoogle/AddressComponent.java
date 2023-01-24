package com.example.go4lunch.objetgoogle;

import java.util.List;

public class AddressComponent {

    String long_name;
    String short_name;
    List<String> types;

    public String getLong_name() {
        if(long_name != null){return long_name;}else{return null;}
    }

    public String getShort_name() {
        return short_name;
    }

    public List<String> getTypes() {
        return types;
    }
}
