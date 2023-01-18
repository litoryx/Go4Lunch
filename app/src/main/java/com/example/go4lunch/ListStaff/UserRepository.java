package com.example.go4lunch.ListStaff;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.RestaurantDetailViewState;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import kotlin.jvm.internal.CollectionToArray;

public class UserRepository {
    private static volatile UserRepository instance;
    private static final String COLLECTION_NAME = "user";
    List<User> usersSameRest;
    Float mDistance;
    FirebaseFirestore mFirebaseFirestore;


    public UserRepository(FirebaseFirestore firebaseFirestore) {
        this.mFirebaseFirestore = firebaseFirestore;
    }

    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserRepository.class) {
            if (instance == null) {
                instance = new UserRepository(FirebaseFirestore.getInstance());
            }
            return instance;
        }
    }

    @Nullable
    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private CollectionReference getUsersCollection(){
        return mFirebaseFirestore.collection(COLLECTION_NAME);
    }

    // Get User Data from Firestore
    public LiveData<List<User>> getUserData(){
        MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();

        getUsersCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> users = task.getResult().toObjects(User.class);
                //Est-ce qu'une collection peut être dans un objet "RestFavoris" ?
                //L'erreur est la ligne 67, il manque un objet RestFavoris, qui est dans Firestore.
                userLiveData.setValue(users);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
        return userLiveData;
    }

    public UploadTask uploadImage(Uri imageUri){
        String uuid = UUID.randomUUID().toString(); // GENERATE UNIQUE STRING
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(uuid);
        return mImageRef.putFile(imageUri);
    }

    public void createImageFireStorageOnFireStore(String urlImage){
            List<User> users = getUserData().getValue();
            // Creating Message with the URL image
            // Storing Message on Firestore
        if(users != null) {
            for (User user : users) {
                User userNew = new User(user.getUid(),user.getUsername(),null,user.getMail(),null, urlImage);
                getUsersCollection().document(user.getUid())
                        .set(userNew);
            }
        }
    }

    // Update User Username
    public void addUser(FirebaseUser user) {
        String photoUrl = null;
        Uri url = user.getPhotoUrl();

        if(url != null){photoUrl = url.toString();}
                    User userNew = new User(user.getUid(),user.getDisplayName(),null,user.getEmail(),null, photoUrl);
                    String restDefault = "restDefault";

                    getUsersCollection().document(userNew.getUid())
                        .set(userNew)
                        .addOnSuccessListener(aVoid -> Log.d("success", "DocumentSnapshot successfully written!"))
                        .addOnFailureListener(e -> Log.w("stop", "Error writing document", e));

       //getUsersCollection().document(userNew.getUid()).collection("RestFavoris").document().set(restDefault)
                //.addOnSuccessListener(aVoid -> Log.d("success", "DocumentSnapshot successfully written!"))
                //.addOnFailureListener(e -> Log.w("stop", "Error writing document", e));
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

    public LiveData<RestaurantDetailViewState> createPlaceToRestaurantDetail(Place mPlace){
        MutableLiveData<RestaurantDetailViewState> restDetailLiveData = new MutableLiveData<>();
        List<User> mListUserCurrent = getUserSameRest(mPlace).getValue();
        String restCurrent = getUserRest().getValue();
        List<String> listFav = getListRestFavoris().getValue();
        boolean Fav = false;

        if(listFav != null) {
            for (String namePlace : listFav) {
                if (mPlace != null) {
                    if (mPlace.getName().equals(namePlace)) {
                        Fav = true;
                    }
                }
            }
        }

        if(mPlace != null) {
            RestaurantDetailViewState detailViewState = new RestaurantDetailViewState(
                    mPlace.getPlace_id(),
                    mPlace.getName(),
                    mPlace.getUrl(),
                    mPlace.getFormatted_phone_number(),
                    mPlace.getAdr_address(),
                    photoReftoPhotoURl(mPlace.getPhotos().get(0).getPhoto_reference()),
                    mListUserCurrent,
                    Fav,restCurrent);
            restDetailLiveData.setValue(detailViewState);
        }

        return restDetailLiveData;
    }

    public List<Restaurant> createListRest(List<Place> places, Location locationLiveData, List<User> users){
        List<Integer> mListCountCurrent = CountUserSameRest(places, users);
        List<Float> mListDistancesCurrent = createListDistancesRestUser(places, locationLiveData);
        Restaurant restModel = null;
        List<Restaurant> mListRestConvert = new ArrayList<>();

        int i = 0;
        String url = null;

        if (places != null) {
            Log.d("error","Places existent");
            for (Place place: places){
                if(!place.getPhotos().isEmpty()){
                    url = photoReftoPhotoURl(place.getPhotos().get(0).getPhoto_reference());
                    Log.d("photos","N'est pas vide"+url);}

                if (mListCountCurrent != null) {
                    Log.d("error","mListCountCurrent existent");
                    if (mListDistancesCurrent != null) {
                        Log.d("error","mListDistancesCurrent existent");
                        restModel = new Restaurant(place.getName(),
                                place.getUrl(), place.getFormatted_phone_number(),
                                place.getOpening_hours(),
                                place.getAdr_address(),
                                place.getPlace_id(),
                                url,
                                mListCountCurrent.get(i),
                                mListDistancesCurrent.get(i));
                    }

                    if(restModel != null){Log.d("error","Finis créer Restaurant");}else{Log.d("error","Pas resto");}

                }
                mListRestConvert.add(restModel);
                Log.d("error","Aj restaurants liste"+mListRestConvert.get(i).getName());
                i++;
            }
        }
        Log.d("error","retourne liste");
        return mListRestConvert;
    }



    public List<Float> createListDistancesRestUser(List<Place> places, Location locationLiveData){
        List<Float> restDistLiveData = new ArrayList<>();
        if (places != null) {
            int mSize = places.size();

            for (int i = 0; i < mSize; i++) {
                Double mLat = places.get(i).getGeometry().getLatLngLiteral().getLat();
                Double mLng = places.get(i).getGeometry().getLatLngLiteral().getLng();
                Location locationR = new Location("Rest" + i);

                locationR.setLongitude(mLng);
                locationR.setLatitude(mLat);
                if (locationLiveData != null) {
                    mDistance = locationLiveData.distanceTo(locationR);
                }
                restDistLiveData.add(mDistance);
            }
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
    public List<Integer> CountUserSameRest(List<Place> places, List<User> users) {
        List<Integer> ListI = new ArrayList<>();
        int i = 0;

        if(users != null) {
            for (User usersList : users) {
                for (Place place : places) {
                    if (usersList.getRestaurantChoose() != null) {
                        if (usersList.getRestaurantChoose().getName().equals(place.getName())) {
                            i++;
                        }
                    }
                    ListI.add(i);
                }
            }
        }
        return ListI;
    }
}
