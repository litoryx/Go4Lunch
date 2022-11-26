package com.example.go4lunch.ListStaff;

import android.util.Log;

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
    public void updateUserRestFavoris(Place place){
        FirebaseUser mUser = getCurrentUser();
        if(mUser != null) {
            getUsersCollection().document(mUser.getUid()).collection("RestFavoris").add(place);
        }
    }

    //Permet d'ajouter le restaurant au clique du bouton Présent.
    public void updateUserRest(Place place){
        FirebaseUser mUser = getCurrentUser();
        if(mUser != null) {
            getUsersCollection().document(mUser.getUid()).update("restaurantChoose.id", place.getPlace_id());
            getUsersCollection().document(mUser.getUid()).update("restaurantChoose.name", place.getName());
            getUsersCollection().document(mUser.getUid()).update("restaurantChoose.style","Francais");
        }
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
