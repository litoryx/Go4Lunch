package com.example.go4lunch.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class PlacesAutocompleteResponse {

    List<PlaceAutocompletePrediction> predictions;

    public PlacesAutocompleteResponse(List<PlaceAutocompletePrediction> predictions) {
        this.predictions = predictions;
    }

    public List<PlaceAutocompletePrediction> getPredictions() {
        return predictions;
    }

}
