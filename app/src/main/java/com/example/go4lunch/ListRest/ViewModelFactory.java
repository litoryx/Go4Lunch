package com.example.go4lunch.ListRest;

import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.Net.NetServiceRetrofit;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    private final NetRepository mNetRepository = new NetRepository(NetServiceRetrofit.getnetService());

    private ViewModelFactory() {
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListRestViewModel.class)) {
            // We inject the Repository in the ViewModel constructor
            return (T) new ListRestViewModel(mNetRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
