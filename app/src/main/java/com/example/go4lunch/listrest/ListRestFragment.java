package com.example.go4lunch.listrest;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListRestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListRestFragment extends Fragment {

    RecyclerView mRecyclerView;
    ListRestViewModel mListRestViewModel;
    private final ListRestRecyclerViewAdapter mListRest = new ListRestRecyclerViewAdapter();

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
        mRecyclerView.setAdapter(mListRest);
        // Inflate the layout for this fragment

        mListRestViewModel.getListRest().observe(getViewLifecycleOwner(), list-> {
                mListRest.submitList(list);
                mListRest.notifyDataSetChanged();

        });

        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                0
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mListRestViewModel.refresh();
    }
}