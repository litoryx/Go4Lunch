package com.example.go4lunch.autocomplete;

import java.util.List;

import androidx.annotation.Nullable;

public class PlaceAutocompletePrediction {

    @Nullable
    String place_id;
    String description;
    List<PlaceAutocompleteMatchedSubstring> matched_substrings;
    PlaceAutocompleteStructuredFormat structured_formatting;
    List<PlaceAutocompleteTerm> terms;

    public PlaceAutocompletePrediction(@Nullable String place_id, String description, List<PlaceAutocompleteMatchedSubstring> matched_substrings, PlaceAutocompleteStructuredFormat structured_formatting, List<PlaceAutocompleteTerm> terms) {
        this.place_id = place_id;
        this.description = description;
        this.matched_substrings = matched_substrings;
        this.structured_formatting = structured_formatting;
        this.terms = terms;
    }

    @Nullable
    public String getPlace_id() {
        return place_id;
    }

    public String getDescription() {
        return description;
    }

    public List<PlaceAutocompleteMatchedSubstring> getMatched_substrings() {
        return matched_substrings;
    }

    public PlaceAutocompleteStructuredFormat getStructured_formatting() {
        return structured_formatting;
    }

    public List<PlaceAutocompleteTerm> getTerms() {
        return terms;
    }
}
