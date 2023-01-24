package com.example.go4lunch;

import android.location.Location;
import android.os.Looper;

import com.example.go4lunch.net.LocationRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(JUnit4.class)
public class LocationRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final FusedLocationProviderClient fusedLocationProvider =
            mock(FusedLocationProviderClient.class);
    private final LocationRepository locationRepository = new LocationRepository(
            fusedLocationProvider, mock(Looper.class)
    );
    private final ArgumentCaptor<LocationCallback> argumentCaptor =
            ArgumentCaptor.forClass(LocationCallback.class);

    @Test
    public void startLocation() throws InterruptedException {
        Location location = createLocation(1.0, 0.0);
        LocationResult locationResult = mock(LocationResult.class);
        when(locationResult.getLastLocation()).thenReturn(location);

        locationRepository.startLocationRequest();
        verify(fusedLocationProvider).requestLocationUpdates(
                any(),
                argumentCaptor.capture(),
                any()
        );

        LocationCallback locationCallback = argumentCaptor.getValue();
        locationCallback.onLocationResult(locationResult);

        Location actual = LiveDataTestUtil.getOrAwaitValue(locationRepository.getLocationLiveDatafft());

        assertEquals(1.0, actual.getLongitude(), 0.001);
        assertEquals(0.0, actual.getLatitude(), 0.001);
    }

    @Test
    public void stopLocationWhenStartedRemovesCallback() {
        locationRepository.startLocationRequest();

        locationRepository.stopLocationRequest();
        // Verify that it is called twice because startLocationRequest will always call it.
        verify(fusedLocationProvider, times(2))
                .removeLocationUpdates(any(LocationCallback.class));
    }

    @Test
    public void stopLocationWhenNotStartedDoesNothing() {
        locationRepository.stopLocationRequest();
        verifyNoInteractions(fusedLocationProvider);
    }

    private Location createLocation(Double longitude, Double latitude) {
        Location location = mock(Location.class);
        when(location.getLongitude()).thenReturn(longitude);
        when(location.getLatitude()).thenReturn(latitude);
        return location;
    }
}
