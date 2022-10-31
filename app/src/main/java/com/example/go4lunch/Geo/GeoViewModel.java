package com.example.go4lunch.Geo;

import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.objetGoogle.Place;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class GeoViewModel extends ViewModel {
    private final NetRepository repository;

    private final MutableLiveData<Integer> currentPageMutableLiveData = new MutableLiveData<>();

    private final LiveData<GeoViewState> catFactsViewStateLiveData;

    public GeoViewModel(NetRepository netRepository) {
        repository = netRepository;

        // We start the page at 0 (this will trigger the switchMap to query the first page from the server)
        currentPageMutableLiveData.setValue(0);

        // If the LiveData that contains the current page information changes...
        catFactsViewStateLiveData = Transformations.switchMap(currentPageMutableLiveData, currentPage ->
                // ... we query the repository to get the page (with a Transformations.switchMap)...
                Transformations.map(repository.fetchRestFollowing("48.864033,2.368425"), catFacts ->
                        // ... and we transform the data from the server to the ViewState (with a Transformations.map)
                        mapDataToViewState(catFacts, currentPage)
                )
        );
    }

    // This is the "final product" of our ViewModel : every data needed from the view is in this LiveData
    public LiveData<GeoViewState> getViewStateLiveData() {
        return catFactsViewStateLiveData;
    }

    private GeoViewState mapDataToViewState(@Nullable List<Place> places, int currentPage) {
        List<String> catFactsToBeDisplayed = new ArrayList<>();

        if (places != null) {
            // Mapping data from remote source to view data, ask to your mentor to know why it is important to do so
            for (Place cat : places) {
                catFactsToBeDisplayed.add(cat.getName());
            }
        }

        // Don't let user click to the previous button if the current page is 0 ;)
        boolean isPreviousPageButtonClickable = currentPage != 0;

        return new GeoViewState(
                catFactsToBeDisplayed,
                isPreviousPageButtonClickable
        );
    }
}
