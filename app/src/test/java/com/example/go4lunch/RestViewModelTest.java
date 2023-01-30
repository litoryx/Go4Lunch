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
public class RestViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final LocationRepository locationRepository = mock(LocationRepository.class);
    private final NetRepository netRepository = mock(NetRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AutoCompleteRepository autoRepository = mock(AutoCompleteRepository.class);
    private final PermissionChecker permRepository = mock(PermissionChecker.class);

    private ListRestViewModel mListRestViewModel;

    @Before
    public void setup() {
        MutableLiveData<Location> locationLivedata = new MutableLiveData<>(createLocation(1.0, 0.0));
        when(locationRepository.getLocationLiveDatafft()).thenReturn(locationLivedata);
        MutableLiveData<String> locationStringLivedata = new MutableLiveData<>("1.0,0.0");
        when(locationRepository.getLocationLiveData()).thenReturn(locationStringLivedata);

        List<Place> places = new ArrayList<>();
        Place place = new Place("Wtf","wtf","065985221","Rue des papa","1");
        places.add(place);

        MutableLiveData<List<Place>> placeTest = new MutableLiveData<>(places);
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

       mListRestViewModel = new ListRestViewModel(netRepository, locationRepository,
                userRepository, permRepository);

        verify(locationRepository).getLocationLiveData();
        verify(locationRepository).getLocationLiveDatafft();
        verify(userRepository).getUserData();
    }

    @Test
    public void testRestGetList() throws InterruptedException {
        List<Restaurant> restaurants = LiveDataTestUtil.getOrAwaitValue(mListRestViewModel.getListRest());

        verify(netRepository).fetchRestFollowing("1.0,0.0");
        // TODO assert que le contenu de 'restaurants' est bien ce qui est attendu
    }

    private Location createLocation(Double longitude, Double latitude) {
        Location location = mock(Location.class);
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);
        return location;
    }
}
