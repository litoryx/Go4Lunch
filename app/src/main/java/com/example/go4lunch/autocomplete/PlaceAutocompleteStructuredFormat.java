package com.example.go4lunch.autocomplete;

import java.util.List;

public class PlaceAutocompleteStructuredFormat {

    String main_text;
    List<PlaceAutocompleteMatchedSubstring> main_text_matched_substrings;
    String secondary_text;

    public PlaceAutocompleteStructuredFormat(String main_text, List<PlaceAutocompleteMatchedSubstring> main_text_matched_substrings, String secondary_text) {
        this.main_text = main_text;
        this.main_text_matched_substrings = main_text_matched_substrings;
        this.secondary_text = secondary_text;
    }


    public String getMain_text() {
        return main_text;
    }

    public List<PlaceAutocompleteMatchedSubstring> getMain_text_matched_substrings() {
        return main_text_matched_substrings;
    }

    public String getSecondary_text() {
        return secondary_text;
    }
}
