package com.example.go4lunch.ListRest;

import android.app.Application;

import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.objetGoogle.Place;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListRestViewModel extends ViewModel {
    NetRepository mNetRepository;
    LiveData<List<Place>> mListCurrent;

    public ListRestViewModel(NetRepository netRepository) {
        mNetRepository = netRepository;
        mListCurrent = mNetRepository.fetchRestFollowing("48.864033,2.368425");
    }

    public LiveData<List<Place>> getList(){return mListCurrent;}

}
