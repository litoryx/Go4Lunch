package com.example.go4lunch;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.Nullable;

public class NetworkAsyncTask extends android.os.AsyncTask<String, Void, String> {

public interface Listeners {
    void onPreExecute();
    void doInBackground();
    void onPostExecute(String success);

    void onResponse(@Nullable List<Restaurant> restGived);

    void onFailure();
}

    private final WeakReference<Listeners> callback;

    public NetworkAsyncTask(Listeners callback){
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.get().onPreExecute();
        Log.e("TAG", "AsyncTask is started.");
    }

    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(success);
        this.callback.get().onPostExecute(success);
        Log.e("TAG", "AsyncTask is finished.");
    }

    @Override
    protected String doInBackground(String... url) {
        this.callback.get().doInBackground();
        Log.e("TAG", "AsyncTask doing some big work...");
        return MyHttpConnection.startHttpRequest(url[0]);
    }
}
