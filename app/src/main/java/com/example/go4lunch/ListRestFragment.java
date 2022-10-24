package com.example.go4lunch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListRestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListRestFragment extends Fragment implements NetCall.Callbacks{

    RecyclerView mRecyclerView;

    ListRestRecyclerViewAdapter mListRestRecyclerViewAdapter;

    public ListRestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ListRestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListRestFragment newInstance() {
        ListRestFragment fragment = new ListRestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_rest, container, false);
        Context context = view.getContext();

        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        this.executeHttpRequestWithRetrofit();
        // Inflate the layout for this fragment

        return view;
    }

    private void executeHttpRequestWithRetrofit(){
        this.updateUIWhenStartingHTTPRequest();
        NetCall.fetchRestFollowing( this, "-33.8670522%2C151.1957362");
    }

    // 2 - Override callback methods


    @Override
    public void onResponse(@Nullable List<Restaurant> restGived) {
        // 2.1 - When getting response, we update UI
        if (restGived != null) this.updateUIWithListOfRestaurants(restGived);
    }

    @Override
    public void onFailure() {
        // 2.2 - When getting error, we update UI
        this.updateUIWhenStopingHTTPRequest("An error happened !");
    }

    // 3 - Update UI showing only name of users
    private void updateUIWithListOfRestaurants(List<Restaurant> restGived){
        initList(restGived);
        updateUIWhenStopingHTTPRequest("Arrêt");
    }

    private void updateUIWhenStartingHTTPRequest(){
        Toast.makeText(getContext(),"Downloading...", Toast.LENGTH_LONG).show();
    }

    private void updateUIWhenStopingHTTPRequest(String response){
        Toast.makeText(getContext(),response, Toast.LENGTH_LONG).show();
    }

    public void initList(List<Restaurant> list){
        mRecyclerView.setAdapter(new ListRestRecyclerViewAdapter(list)); }
}