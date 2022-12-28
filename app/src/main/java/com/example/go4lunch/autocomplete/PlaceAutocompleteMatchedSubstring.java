package com.example.go4lunch.autocomplete;

public class PlaceAutocompleteMatchedSubstring {

    Number length;
    Number offset;

    public PlaceAutocompleteMatchedSubstring(Number length, Number offset) {
        this.length = length;
        this.offset = offset;
    }


    public Number getLength() {
        return length;
    }

    public Number getOffset() {
        return offset;
    }
}
