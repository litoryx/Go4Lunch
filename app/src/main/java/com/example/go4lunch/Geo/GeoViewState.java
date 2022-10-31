package com.example.go4lunch.Geo;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

public class GeoViewState {
    @NonNull
    private final List<String> catFacts;

    private final boolean isPreviousPageButtonEnabled;

    public GeoViewState(
            @NonNull List<String> catFacts,
            boolean isPreviousPageButtonEnabled
    ) {
        this.catFacts = catFacts;
        this.isPreviousPageButtonEnabled = isPreviousPageButtonEnabled;
    }

    @NonNull
    public List<String> getCatFacts() {
        return catFacts;
    }

    public boolean isPreviousPageButtonEnabled() {
        return isPreviousPageButtonEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoViewState that = (GeoViewState) o;
        return isPreviousPageButtonEnabled == that.isPreviousPageButtonEnabled &&
                catFacts.equals(that.catFacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catFacts, isPreviousPageButtonEnabled);
    }

    @NonNull
    @Override
    public String toString() {
        return "CatFactsViewState{" +
                "catFacts=" + catFacts +
                ", isPreviousPageButtonEnabled=" + isPreviousPageButtonEnabled +
                '}';
    }
}
