package com.example.go4lunch;

import android.location.Location;

import com.example.go4lunch.Geo.GeoViewModel;
import com.example.go4lunch.ListStaff.StaffViewModel;
import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.models.RestaurantChoose;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class StaffViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final PermissionChecker permRepository = mock(PermissionChecker.class);
    private final LocationRepository locationRepository = mock(LocationRepository.class);
    private final NetRepository netRepository = mock(NetRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    private final StaffViewModel mStaffViewModel = new StaffViewModel(userRepository, netRepository, locationRepository, permRepository);

    @Test
    public void staffViewModel() throws InterruptedException {
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

        verify(locationRepository).getLocationLiveData();
        verify(userRepository).getUserData();
        verify(netRepository).fetchRestFollowing("1.0,0.0");

        List<User> actual = LiveDataTestUtil.getOrAwaitValue(mStaffViewModel.getUsers());

        assertEquals("1", actual.get(0).getUid());
    }

    private Location createLocation(Double longitude, Double latitude) {
        Location location = mock(Location.class);
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);
        return location;
    }
}