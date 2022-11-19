package com.example.go4lunch.ListStaff;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.ViewRest.StaffViewModelFactory;
import com.example.go4lunch.ViewRest.ViewRestViewModel;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListStaffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListStaffFragment extends Fragment {
    RecyclerView mRecyclerView;
    StaffViewModel mStaffViewModel;

    public ListStaffFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ListStaffFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListStaffFragment newInstance() {
        ListStaffFragment fragment = new ListStaffFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_staff, container, false);
        Context context = view.getContext();

        mStaffViewModel = new ViewModelProvider(this, UserViewModelFactory.getInstance()).get(StaffViewModel.class);

        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        mStaffViewModel.getUser().observe(getViewLifecycleOwner(), users -> {
            if(users != null){initList(users);}
        });

        return view;
    }

    public void initList(List<User> users){
        mRecyclerView.setAdapter(new ListStaffRecyclerViewAdapter(users));

    }
}