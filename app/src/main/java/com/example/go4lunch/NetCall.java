package com.example.go4lunch;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetCall {
    public interface Callbacks {
        void onResponse(@Nullable List<Restaurant> Rest);
        void onFailure();
    }

    public static void fetchRestFollowing(Callbacks callbacks, String restGived){

        // 2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // 2.2 - Get a Retrofit instance and the related endpoints
        NetService netService = NetService.retrofit.create(NetService.class);

        // 2.3 - Create the call on Github API
        Call<List<Restaurant>> call = netService.getFollowing(restGived);
        // 2.4 - Start the call
        call.enqueue(new Callback<List<Restaurant>>() {

            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }

}
