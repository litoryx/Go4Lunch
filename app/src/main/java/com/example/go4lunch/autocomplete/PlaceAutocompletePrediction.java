package com.example.go4lunch.autocomplete;

import java.util.List;

import androidx.annotation.Nullable;

public class PlaceAutocompletePrediction {

    @Nullable
    String place_id;
    String description;

    public PlaceAutocompletePrediction(@Nullable String place_id, String description, List<PlaceAutocompleteMatchedSubstring> matched_substrings, PlaceAutocompleteStructuredFormat structured_formatting, List<PlaceAutocompleteTerm> terms) {
        this.place_id = place_id;
        this.description = description;
    }

    @Nullable
    public String getPlace_id() {
        return place_id;
    }

    public String getDescription() {
        return description;
    }

}
