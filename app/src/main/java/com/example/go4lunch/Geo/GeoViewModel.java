package com.example.go4lunch.Geo;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.autocomplete.AutoCompleteRepository;
import com.example.go4lunch.autocomplete.Prediction;
import com.example.go4lunch.models.PermissionChecker;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class GeoViewModel extends ViewModel {

    NetRepository mNetRepository;
    LocationRepository mLocationRepository;
    UserRepository mUserRepository;
    AutoCompleteRepository mAutoCompleteRepository;
    PermissionChecker mPermissionChecker;
    Boolean hasGpsPermission = false;
    private final MediatorLiveData<List<GeoViewState>> mGeoViewStateLiveData = new MediatorLiveData<>();
    LiveData<String> locationLiveData;
    LiveData<Location> locationLiveDataLoc;

    public GeoViewModel(NetRepository netRepository, LocationRepository locationRepository, UserRepository userRepository, AutoCompleteRepository autoCompleteRepository,PermissionChecker permissionChecker) {
        mNetRepository = netRepository;
        mLocationRepository = locationRepository;
        mUserRepository = userRepository;
        mAutoCompleteRepository = autoCompleteRepository;
        mPermissionChecker = permissionChecker;

        LiveData<List<Prediction>> mListPredictionLiveData = autoCompleteRepository.getListPredictionLiveData();


        locationLiveData = locationRepository.getLocationLiveData();
        locationLiveDataLoc = locationRepository.getLocationLiveDatafft();

        LiveData<List<Place>> nearbyPlacesLivedata = Transformations.switchMap(locationLiveData, location ->
                    mNetRepository.fetchRestFollowing(location));

        LiveData<List<User>> usersLivedata = userRepository.getUserData();

        mGeoViewStateLiveData.addSource(nearbyPlacesLivedata, places ->
                combine(places, usersLivedata.getValue(), mListPredictionLiveData.getValue())
        );

        mGeoViewStateLiveData.addSource(usersLivedata, users ->
                combine(nearbyPlacesLivedata.getValue(), users, mListPredictionLiveData.getValue())
        );

        mGeoViewStateLiveData.addSource(mListPredictionLiveData, predictions ->
                combine(nearbyPlacesLivedata.getValue(), usersLivedata.getValue(), predictions)
        );
    }

    private void combine(@Nullable List<Place> places, @Nullable List<User> users, @Nullable List<Prediction> predictions) {
        // Si places ou users est null, cela veut dire qu'on a pas encore reçu la réponse à nos
        // différentes requêtes. On ne peut pas calculer le GeoViewState donc on stoppe l'exécution
        // de cette fonction en appellant "return"
        if (places == null || users == null) {
            Log.d("GeoviewModel","places=null users=null");
            return;
        }
        Log.d("GeoviewModel", places.size()+"");
        List<GeoViewState> mGeoList = new ArrayList<>();

        // On transforme les places en GeoViewState
        for (Place place : places) {
            Boolean hasRestaurantBeChosen = hasAUserChosenRestaurant(place, users);
            GeoViewState mGeoViewState = new GeoViewState(
                    place.getGeometry().getLatLngLiteral().getLat(),
                    place.getGeometry().getLatLngLiteral().getLng(),
                    hasRestaurantBeChosen
            );
            //Verifie si il est dans la liste Predictions
            if(predictions == null){mGeoList.add(mGeoViewState);
                Log.d("GeoviewModel", mGeoList.size()+" GeoViewState");
            }else{
                if(TrueorFalsePredictions(predictions, place.getPlace_id())){
                    mGeoList.add(mGeoViewState);
                    Log.d("GeoviewModel", mGeoList.size()+" GeoViewState");
                }
            }
        }
        mGeoViewStateLiveData.setValue(mGeoList);
    }

    private Boolean TrueorFalsePredictions(List<Prediction> predictions, String placeId){
        for (Prediction prediction:predictions){
            if(placeId.equals(prediction.getIdPred())){return true;}
        }
        return false;
    }

    private boolean hasAUserChosenRestaurant(@NonNull Place place, @NonNull List<User> users) {
        for (User user : users) {
            if (user.getRestaurantChoose() != null) {
                if (user.getRestaurantChoose().getId().equals(place.getPlace_id())) {
                    // Cet utilisateur a choisi le restaurant, pas besoin de regarder les autres car
                    // la couleur de la pin est rouge si 0 choix, verte si 1+ choix.
                    return true;
                }
            }
        }
        // Aucun utilisateur n'a choisi ce restaurant
        return false;
    }

    // This is the "final product" of our ViewModel : every data needed from the view is in this LiveData
    public LiveData<List<GeoViewState>> getViewStateLiveData() {
        return mGeoViewStateLiveData;
    }

    @Nullable
    public Location getUserLocation(){

        return locationLiveDataLoc.getValue();
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        hasGpsPermission = mPermissionChecker.hasLocationPermission();

        if (hasGpsPermission) {
            mLocationRepository.startLocationRequest();
        } else {
            mLocationRepository.stopLocationRequest();
        }
    }
}
