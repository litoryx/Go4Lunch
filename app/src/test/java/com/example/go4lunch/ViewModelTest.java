package com.example.go4lunch;


import android.location.Location;

import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.net.LocationRepository;
import com.example.go4lunch.autocomplete.AutoCompleteRepository;
import com.example.go4lunch.autocomplete.Prediction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ViewModelTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final LocationRepository locationRepository = mock(LocationRepository.class);
    private final AutoCompleteRepository autoRepository = mock(AutoCompleteRepository.class);

    private MainViewModel mainViewModel;

    @Before
    public void setup(){
        MutableLiveData<Location> locationLivedata = new MutableLiveData<>(createLocation(1.0, 0.0));
        when(locationRepository.getLocationLiveDatafft()).thenReturn(locationLivedata);

        List<Prediction> predictions = new ArrayList<>();
        Prediction prediction = new Prediction("1");
        predictions.add(prediction);

        MutableLiveData<List<Prediction>> predTest = new MutableLiveData<>(predictions);
        when(autoRepository.getListPredictionLiveData()).thenReturn(predTest);

        mainViewModel = new MainViewModel(autoRepository, locationRepository);

        verify(autoRepository).getListPredictionLiveData();
        verify(locationRepository).getLocationLiveData();
    }

    @Test
    public void testViewModelTextSearch() throws InterruptedException {
        List<Prediction> pred = LiveDataTestUtil.getOrAwaitValue(mainViewModel.getPredictions());

        verify(mainViewModel).updateSearchText("New York");
        verifyNoMoreInteractions(mainViewModel);
    }

    private Location createLocation(Double longitude, Double latitude) {
        Location location = mock(Location.class);
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);
        return location;
    }
}
