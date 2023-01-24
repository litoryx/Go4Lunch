package com.example.go4lunch;

import com.example.go4lunch.autocomplete.AutoCompleteRepository;
import com.example.go4lunch.autocomplete.AutoCompleteService;
import com.example.go4lunch.autocomplete.PlacesAutocompleteResponse;
import com.example.go4lunch.autocomplete.PlacesAutocompleteStatus;
import com.example.go4lunch.autocomplete.Prediction;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class AutocompleteRepository {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    AutoCompleteService mAutoCompleteService = Mockito.mock(AutoCompleteService.class);
    AutoCompleteRepository mAutoCompleteRepository = new AutoCompleteRepository(mAutoCompleteService);

    private final Call<PlacesAutocompleteResponse> call = Mockito.mock(Call.class);
    private final Response<PlacesAutocompleteResponse> response = Mockito.mock(Response.class);
    private final ArgumentCaptor<Callback<PlacesAutocompleteResponse>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);

    @Test
    public void searchAutoComplete() throws InterruptedException {
        when(mAutoCompleteService.getPredictions(any(),any(),anyInt(),any(),any())).thenReturn(call);
        when(response.body()).thenReturn(createAutocompleteResponse());

        List<Prediction> actual = LiveDataTestUtil.getOrAwaitValue(mAutoCompleteRepository.getListPredictionLiveData());

        verify(call).enqueue(argumentCaptor.capture());
        Callback<PlacesAutocompleteResponse> callback = argumentCaptor.getValue();
        callback.onResponse(call, response);

        verify(mAutoCompleteService).getPredictions("Rue pelleport","1.0,0.0", 2000, "restaurant","key");
        verifyNoMoreInteractions(mAutoCompleteService);
    }

    private PlacesAutocompleteResponse createAutocompleteResponse() {
        PlacesAutocompleteStatus mStatut = new PlacesAutocompleteStatus("good");
        return new PlacesAutocompleteResponse(new ArrayList<>(), mStatut);
    }
}
