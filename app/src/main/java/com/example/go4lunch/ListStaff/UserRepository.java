package com.example.go4lunch.ListStaff;

import android.location.Location;
import android.util.Log;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.RestaurantDetailViewState;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {
    private static volatile UserRepository instance;
    private static final String COLLECTION_NAME = "user";
    List<User> usersSameRest;
    List<Float> mListLocationFloatRest;
    Float mDistance;
    List<Restaurant> mListRestConvert;

    public UserRepository() { }

    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserRepository.class) {
            if (instance == null) {
                instance = new UserRepository();
            }
            return instance;
        }
    }

    @Nullable
    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Get User Data from Firestore
    public LiveData<List<User>> getUserData(){
        MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();

        getUsersCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> users = task.getResult().toObjects(User.class);
                userLiveData.setValue(users);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
        return userLiveData;
    }

    // Update User Username
    public void addUser(FirebaseUser user) {
        getUsersCollection().document(user.getUid())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("success", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("stop", "Error writing document", e));
    }
    //Permettra d'ajouter au clique dans une liste Place ou pas FireStore ? un Array peut-être ?
    public void updateUserRestFavoris(RestaurantDetailViewState place){
        FirebaseUser mUser = getCurrentUser();
        if(mUser != null) {
            getUsersCollection().document(mUser.getUid()).collection("RestFavoris").add(place);
        }
    }

    public LiveData<List<String>> getListRestFavoris(){
        MutableLiveData<List<String>> restFavLiveData = new MutableLiveData<>();
        FirebaseUser mUser = getCurrentUser();
        if(mUser != null) {
            getUsersCollection().document(mUser.getUid()).collection("RestFavoris").get().addOnCompleteListener(restFav ->{
                List<String> mListRestFavString = restFav.getResult().toObjects(String.class);
                restFavLiveData.setValue(mListRestFavString);
            });
        }
        return restFavLiveData;
    }

    //Permet d'ajouter le restaurant au clique du bouton Présent.
    public void updateUserRest(RestaurantDetailViewState place){
        FirebaseUser mUser = getCurrentUser();
        if(mUser != null) {
            getUsersCollection().document(mUser.getUid()).update("restaurantChoose.id", place.getPlace_id());
            getUsersCollection().document(mUser.getUid()).update("restaurantChoose.name", place.getName());
            getUsersCollection().document(mUser.getUid()).update("restaurantChoose.style","Francais");
        }
    }

    public LiveData<String> getUserRest(){
        MutableLiveData<String> restLiveData = new MutableLiveData<>();
        FirebaseUser mUser = getCurrentUser();
        if(mUser != null) {
            getUsersCollection().document(mUser.getUid()).get().addOnCompleteListener(user -> {
                User use = user.getResult().toObject(User.class);
                if(use != null) {
                    String mNameRestCurrent = use.getRestaurantChoose().getName();
                    restLiveData.setValue(mNameRestCurrent);
                }
            });
        }
        return restLiveData;

    }

    public String photoReftoPhotoURl(String photo_reference){

        return "https://maps.googleapis.com/maps/api/place/photo"+
                "?maxwidth=400"+
                "&photo_reference="+photo_reference+
                "&key="+ BuildConfig.MAPS_API_KEY;
    }

    public LiveData<RestaurantDetailViewState> createPlaceToRestaurantDetail(LiveData<Place> mPlace){
        Place place = mPlace.getValue();
        MutableLiveData<RestaurantDetailViewState> restDetailLiveData = new MutableLiveData<>();
        List<User> mListUserCurrent = getUserSameRest(place).getValue();
        String restCurrent = getUserRest().getValue();
        List<String> listFav = getListRestFavoris().getValue();
        boolean Fav = false;

        if(listFav != null) {
            for (String namePlace : listFav) {
                if (place != null) {
                    if (place.getName().equals(namePlace)) {
                        Fav = true;
                    }
                }
            }
        }

        RestaurantDetailViewState detailViewState = new RestaurantDetailViewState();

        if(place != null) {
            detailViewState.setName(place.getName());
            detailViewState.setAdr_address(place.getAdr_address());
            detailViewState.setUrl(place.getUrl());
            detailViewState.setPhoto(photoReftoPhotoURl(place.getPhotos().get(0).getPhoto_reference()));
            detailViewState.setFormatted_phone_number(place.getFormatted_phone_number());
            detailViewState.setPlace_id(place.getPlace_id());
            detailViewState.setUserCurrentRest(mListUserCurrent);
            detailViewState.setRestCurrent(restCurrent);
            detailViewState.setFav(Fav);
        }

        restDetailLiveData.setValue(detailViewState);
        return restDetailLiveData;
    }

    public LiveData<List<Restaurant>> createListRest(LiveData<List<Place>> places, LiveData<Location> locationLiveData){
        List<Place> mListRestCurrent = places.getValue();
        LiveData<List<Integer>> mListCountCurrent = CountUserSameRest(mListRestCurrent);
        LiveData<List<Float>> mListDistancesCurrent = createListDistancesRestUser(places, locationLiveData);
        Restaurant restModel = new Restaurant(null);
        MutableLiveData<List<Restaurant>> mListRestLiveData = new MutableLiveData<>();
        int i = 0;

        if (mListRestCurrent != null) {
            for (Place place:mListRestCurrent){
                restModel.setName(place.getName());
                restModel.setUrl(place.getUrl());
                restModel.setAdr_address(place.getAdr_address());
                restModel.setPlace_id(place.getPlace_id());
                String url = photoReftoPhotoURl(place.getPhotos().get(0).getPhoto_reference());
                restModel.setPhoto(url);
                restModel.setFormatted_phone_number(place.getFormatted_phone_number());
                restModel.setOpening_hours(place.getOpening_hours());
                if (mListCountCurrent.getValue() != null) {
                    restModel.setNumbers_user_rest(mListCountCurrent.getValue().get(i));
                }
                assert mListDistancesCurrent.getValue() != null;
                restModel.setDistanceRest(mListDistancesCurrent.getValue().get(i));
                mListRestConvert.add(restModel);
                i++;
            }
        }
        mListRestLiveData.setValue(mListRestConvert);
        return mListRestLiveData;
    }



    public LiveData<List<Float>> createListDistancesRestUser(LiveData<List<Place>> places, LiveData<Location> locationLiveData){
        MutableLiveData<List<Float>> restDistLiveData = new MutableLiveData<>();
        if (places.getValue() != null) {
            int mSize = places.getValue().size();

            for (int i = 0; i <= mSize; i++) {
                Double mLat = places.getValue().get(i).getGeometry().getLatLngLiteral().getLat();
                Double mLng = places.getValue().get(i).getGeometry().getLatLngLiteral().getLng();
                Location locationR = new Location("Rest" + i);
                Location currentLocation = locationLiveData.getValue();

                locationR.setLongitude(mLng);
                locationR.setLatitude(mLat);
                if (currentLocation != null) {
                    mDistance = currentLocation.distanceTo(locationR);
                }
                mListLocationFloatRest.add(mDistance);
            }
            restDistLiveData.setValue(mListLocationFloatRest);
        }
            return restDistLiveData;
    }

    //Détermine la liste users qui sont dans le même restaurant dans le Place Detail
    public LiveData<List<User>> getUserSameRest(Place place){
        MutableLiveData<List<User>> userSameRestLiveData = new MutableLiveData<>();

        getUsersCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                usersSameRest = new ArrayList<>();
                List<User> users = task.getResult().toObjects(User.class);
                for (User usersList:users) {
                    if (usersList.getRestaurantChoose() != null){
                    if (usersList.getRestaurantChoose().getName().equals(place.getName())) {
                        usersSameRest.add(usersList);
                    }
                    }
                }
                userSameRestLiveData.setValue(usersSameRest);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
        return userSameRestLiveData;
    }

    //Compte le nombre d'utilisateur dans le même restaurant à faire évoluer pour une liste Place qui
    // retourne listInteger lié à ListRestViewModel
    public LiveData<List<Integer>> CountUserSameRest(List<Place> places){
        MutableLiveData<List<Integer>> userCountSameRestLiveData = new MutableLiveData<>();

        getUsersCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Integer> ListI = new ArrayList<>();
                int i = 0;
                List<User> users = task.getResult().toObjects(User.class);
                for (User usersList:users) {
                    for (Place place:places) {
                        if (usersList.getRestaurantChoose() != null) {
                            if (usersList.getRestaurantChoose().getName().equals(place.getName())) {
                                i++;
                            }
                        }
                        ListI.add(i);
                    }
                }
                userCountSameRestLiveData.setValue(ListI);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
        return userCountSameRestLiveData;
    }
}
