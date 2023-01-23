package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.go4lunch.notification.MyWork;
import com.facebook.FacebookSdk;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class ConnexionActivity2 extends AppCompatActivity {

    Intent intent;
    private static final int RC_SIGN_IN = 123;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion2);
        FacebookSdk.sdkInitialize(this);
        startSignInActivity();

        createNotificationChannel();

        PeriodicWorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(MyWork.class, 1, TimeUnit.DAYS)
                        .build();

        WorkManager
                .getInstance(this)
                .enqueueUniquePeriodicWork("work1", ExistingPeriodicWorkPolicy.KEEP,saveRequest);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel 0";
            String description = "getString(R.string.channel_description)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("12", name, importance);
            channel.setDescription(description);
            //j'ai laissé createNotificationChannel dans l'activity pour getSystemService qui en as besoin
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startSignInActivity() {

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        //.setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        //.setLogo(R.drawable.ic_logo_auth)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response = IdpResponse.fromResultIntent(data);
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("ConnexionActivity2", "firebUser "+fbUser);
        if (resultCode == RESULT_OK) {
            Intent activityIntent = new Intent(this, MainActivity.class);
            activityIntent.putExtra("mfbUser", fbUser);
            startActivity(activityIntent, null);
        } else {
            if (response == null) {//Backbouton appuyé
                finish();
                return;
            }
            Log.d("ConnexionActivity2", "Cela a echoué");
        }
    }

}