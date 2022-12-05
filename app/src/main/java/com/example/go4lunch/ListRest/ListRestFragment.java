package com.example.go4lunch.ListRest;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.objetGoogle.Place;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListRestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListRestFragment extends Fragment {

    RecyclerView mRecyclerView;
    ListRestViewModel mListRestViewModel;

    public ListRestFragment() {
        // Required empty public constructor
    }


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

        mListRestViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ListRestViewModel.class);

        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        // Inflate the layout for this fragment

        mListRestViewModel.getListRest().observe(getViewLifecycleOwner(), this::initList);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mListRestViewModel.refresh();
    }

    public void initList(List<Restaurant> list){
        mRecyclerView.setAdapter(new ListRestRecyclerViewAdapter(list));
    }
}