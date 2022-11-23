package com.example.go4lunch.ListStaff;

import android.content.Context;
import android.util.Log;

import com.example.go4lunch.models.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {
    private static volatile UserRepository instance;
    private static final String COLLECTION_NAME = "user";
    private static final String USERNAME_FIELD = "Philippe";
    private static final String IS_MENTOR_FIELD = "Philippe";

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

    public Task<Void> signOut(Context context){
        return AuthUI.getInstance().signOut(context);
    }

    public Task<Void> deleteUser(Context context){
        return AuthUI.getInstance().delete(context);
    }

    private CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Get User Data from Firestore
    public LiveData<List<User>> getUserData(){
        MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();

        getUsersCollection().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<User> users = task.getResult().toObjects(User.class);
                    userLiveData.setValue(users);
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return userLiveData;
    }

    // Update User Username
    public void addUser(FirebaseUser user) {
        //J'ai rien capter de comment on modifie un FirebaseUser en Map ne comprends pas pourquoi faut le faire ?
        Map<String, Object> userU = new HashMap<>();
        userU.put("uid","2");
        userU.put("username",user.getDisplayName());
        userU.put("style","Francais");

        getUsersCollection().document("User")
                .set(userU)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("success", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("stop", "Error writing document", e);
                    }
                });
    }

    // Update User isMentor
    public void updateIsMentor(Boolean isMentor) {
        String uid = this.getCurrentUserUID();
        if(uid != null){
            this.getUsersCollection().document(uid).update(IS_MENTOR_FIELD, isMentor);
        }
    }

    // Delete the User from Firestore
    public void deleteUserFromFirestore() {
        String uid = this.getCurrentUserUID();
        if(uid != null){
            this.getUsersCollection().document(uid).delete();
        }
    }

    public String getCurrentUserUID(){
        FirebaseUser user = getCurrentUser();
        String uid = "null";
        if(user != null){
            uid = getCurrentUser().getUid();
        }
        return uid;
    }
}
