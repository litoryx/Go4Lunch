package com.example.go4lunch.autocomplete;

public class Prediction {

    private String idPred;
    private String valuePred;

    public Prediction(String id) {
        this.idPred = id;
    }

    public String getIdPred() {
        return idPred;
    }

    public String getValuePred() {
        return valuePred;
    }
}
