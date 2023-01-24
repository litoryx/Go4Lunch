package com.example.go4lunch.models;

public class RestaurantChoose {

    private String id;
    private String name;
    private String style;

    public String getId() {
        return id;
    }

    public String getName() {
        if(name != null){return name;}else{return null;}
    }

    public String getStyle() {
        return style;
    }

}
