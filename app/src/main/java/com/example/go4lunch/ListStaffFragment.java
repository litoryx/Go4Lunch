package com.example.go4lunch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListStaffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListStaffFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_list_staff, container, false);
    }
}