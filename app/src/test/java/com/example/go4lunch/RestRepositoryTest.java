package com.example.go4lunch;

import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.Net.NetService;
import com.example.go4lunch.Net.NetServiceRetrofit;
import com.example.go4lunch.objetGoogle.Place;
import com.example.go4lunch.objetGoogle.PlaceDetailsResponse;
import com.example.go4lunch.objetGoogle.PlacesNearbySearchResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class RestRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    NetService mNetService = Mockito.mock(NetService.class);
    NetRepository mNetRepository = new NetRepository(mNetService);

    private final Call<PlaceDetailsResponse> callDetail = Mockito.mock(Call.class);
    private final Response<PlaceDetailsResponse> responseDetail = Mockito.mock(Response.class);
    private final ArgumentCaptor<Callback<PlaceDetailsResponse>> argumentCaptorDetail = ArgumentCaptor.forClass(Callback.class);

    private final Call<PlacesNearbySearchResponse> call = Mockito.mock(Call.class);
    private final Response<PlacesNearbySearchResponse> response = Mockito.mock(Response.class);
    private final ArgumentCaptor<Callback<PlacesNearbySearchResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    @Test
    public void searchRestaurants() throws InterruptedException {
        when(mNetService.getFollowing(any(),anyInt(),any(),any())).thenReturn(call);
        when(response.body()).thenReturn(createNearbySearchResponse());

        LiveData<List<Place>> restaurantsLivedata = mNetRepository.fetchRestFollowing("0.0, 1.0");

        verify(call).enqueue(argumentCaptor.capture());
        Callback<PlacesNearbySearchResponse> callback = argumentCaptor.getValue();
        callback.onResponse(call, response);

        List<Place> actual = LiveDataTestUtil.getOrAwaitValue(restaurantsLivedata);

        assertEquals(0, actual.size());
        verify(mNetService).getFollowing("1.0,0.0", 2000, "restaurant", "key");
        verifyNoMoreInteractions(mNetService);
    }

    @Test
    public void searchDetailRestaurant() throws InterruptedException {
        when(mNetService.getStaffFollowing(any(),any())).thenReturn(callDetail);
        when(responseDetail.body()).thenReturn(createDetailSearchResponse());

        LiveData<Place> restaurantsLivedata = mNetRepository.fetchRestDetailFollowing(
                "0"
        );

        verify(callDetail).enqueue(argumentCaptorDetail.capture());
        Callback<PlaceDetailsResponse> callback = argumentCaptorDetail.getValue();
        callback.onResponse(callDetail, responseDetail);

        Place actual = LiveDataTestUtil.getOrAwaitValue(restaurantsLivedata);

        assertFalse(actual.getName().isEmpty());
        verify(mNetService).getStaffFollowing("0","key");
        verifyNoMoreInteractions(mNetService);
    }

    private PlaceDetailsResponse createDetailSearchResponse() {
        return new PlaceDetailsResponse(new Place(null));
    }

    private PlacesNearbySearchResponse createNearbySearchResponse() {
        return new PlacesNearbySearchResponse(new ArrayList<>());
    }
}
