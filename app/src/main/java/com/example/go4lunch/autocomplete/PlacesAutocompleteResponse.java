package com.example.go4lunch.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class PlacesAutocompleteResponse {

    List<PlaceAutocompletePrediction> predictions;
    PlacesAutocompleteStatus status;

    public PlacesAutocompleteResponse(List<PlaceAutocompletePrediction> predictions, PlacesAutocompleteStatus status) {
        this.predictions = predictions;
        this.status = status;
    }



    public List<PlaceAutocompletePrediction> getPredictions() {
        return predictions;
    }

    public PlacesAutocompleteStatus getStatus() {
        return status;
    }
}
