package com.example.go4lunch.Geo;

import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.Net.NetServiceRetrofit;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GeoViewModelFactory implements ViewModelProvider.Factory {

    private static GeoViewModelFactory factory;

    public static GeoViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (GeoViewModelFactory.class) {
                if (factory == null) {
                    factory = new GeoViewModelFactory();
                }
            }
        }
        return factory;
    }

    // Here is our "graph / tree" of injection : CatFactsRepository needs CatApi, and later on, CatFactsViewModel will need CatFactsRepository
    private final NetRepository catFactsRepository = new NetRepository(
            // We inject the CatApi in the Repository constructor
            NetServiceRetrofit.getCatApi()
    );

    private GeoViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GeoViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new GeoViewModel(catFactsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
