package com.example.go4lunch;

import android.location.Location;

import com.example.go4lunch.listrest.ListRestViewModel;
import com.example.go4lunch.liststaff.UserRepository;
import com.example.go4lunch.net.LocationRepository;
import com.example.go4lunch.net.NetRepository;
import com.example.go4lunch.autocomplete.AutoCompleteRepository;
import com.example.go4lunch.autocomplete.Prediction;
import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.RestaurantChoose;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetgoogle.Place;

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
public class RestViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final LocationRepository locationRepository = mock(LocationRepository.class);
    private final NetRepository netRepository = mock(NetRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AutoCompleteRepository autoRepository = mock(AutoCompleteRepository.class);
    private final PermissionChecker permRepository = mock(PermissionChecker.class);

    private final ListRestViewModel mListRestViewModel = new ListRestViewModel(netRepository, locationRepository,
            userRepository, permRepository);

    @Test
    public void testRestGetList() throws InterruptedException {
        MutableLiveData<Location> locationLivedata = new MutableLiveData<>(createLocation(1.0, 0.0));
        when(locationRepository.getLocationLiveDatafft()).thenReturn(locationLivedata);

        MutableLiveData<List<Place>> placeTest = new MutableLiveData<>(null);
        when(netRepository.fetchRestFollowing("1.0,0.0")).thenReturn(placeTest);

        List<User> usersTest = new ArrayList<>();
        RestaurantChoose restChosed = new RestaurantChoose();
        List<String> favoris = new ArrayList<>();
        User user1 = new User("1","Dad",restChosed,"mick@gmail.com",favoris,"http:/finissonsen");
        usersTest.add(user1);

        MutableLiveData<List<User>> userTest = new MutableLiveData<>(usersTest);
        when(userRepository.getUserData()).thenReturn(userTest);

        List<Prediction> predictions = new ArrayList<>();
        Prediction prediction = new Prediction("1");
        predictions.add(prediction);

        MutableLiveData<List<Prediction>> predTest = new MutableLiveData<>(predictions);
        when(autoRepository.getListPredictionLiveData()).thenReturn(predTest);

        List<Restaurant> mListLiveDataRest = LiveDataTestUtil.getOrAwaitValue(mListRestViewModel.getListRest());

        verify(locationRepository).getLocationLiveData();
        verify(userRepository).getUserData();
        verify(netRepository).fetchRestFollowing("1.0,0.0");
    }

    @Test
    public void testRefresh(){

        when(permRepository.hasLocationPermission()).thenReturn(true);

        mListRestViewModel.refresh();

        verify(locationRepository).startLocationRequest();
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    public void testNotRefresh(){

        when(permRepository.hasLocationPermission()).thenReturn(false);

        mListRestViewModel.refresh();

        verify(locationRepository).stopLocationRequest();
        verifyNoMoreInteractions(locationRepository);
    }

    private Location createLocation(Double longitude, Double latitude) {
        Location location = mock(Location.class);
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);
        return location;
    }
}
