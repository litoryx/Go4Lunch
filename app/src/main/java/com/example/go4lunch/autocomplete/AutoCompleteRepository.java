package com.example.go4lunch.autocomplete;

import android.util.Log;

import com.example.go4lunch.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoCompleteRepository {

    MutableLiveData<List<Prediction>> mListPredictionLiveData = new MutableLiveData<>();
    private static AutoCompleteRepository instance;
    AutoCompleteService mAutoCompleteService ;

    public AutoCompleteRepository(AutoCompleteService autoCompleteService) {
        mAutoCompleteService = autoCompleteService;
    }

    public static AutoCompleteRepository getInstance(){
        if(instance == null){
            instance = new AutoCompleteRepository(ACServiceRetrofit.getACService());
        }

       return instance;
    }

    public void updateSearch(String input, String location){

        Call<PlacesAutocompleteResponse> call = mAutoCompleteService.getPredictions(input, location, 1500,"restaurant", BuildConfig.MAPS_API_KEY);

        call.enqueue(new Callback<PlacesAutocompleteResponse>() {

            @Override
            public void onResponse(@NonNull Call<PlacesAutocompleteResponse> call, @NonNull Response<PlacesAutocompleteResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    List<PlaceAutocompletePrediction> predis = response.body().getPredictions();
                    List<Prediction> mListPred = new ArrayList<>();
                    Log.d("geoview",""+predis);
                    if (predis != null) {
                        Log.d("geoview","Ajout predictions");
                            for (PlaceAutocompletePrediction prediction : predis) {
                                Prediction pred = new Prediction(prediction.getPlace_id());
                                mListPred.add(pred);
                            }
                            mListPredictionLiveData.setValue(mListPred);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlacesAutocompleteResponse> call, @NonNull Throwable t) {
                mListPredictionLiveData.setValue(null);
                Log.d("AutoCRepository","Failure getAutoCompletefollowing");
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<Prediction>> getListPredictionLiveData() {
        return mListPredictionLiveData;
    }
}
