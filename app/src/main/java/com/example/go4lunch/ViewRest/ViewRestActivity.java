package com.example.go4lunch.ViewRest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.go4lunch.ListRest.ListRestViewModel;
import com.example.go4lunch.ListStaff.StaffViewModelFactory;
import com.example.go4lunch.R;
import com.example.go4lunch.objetGoogle.Place;

public class ViewRestActivity extends AppCompatActivity {

    ViewRestViewModel mViewRestViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rest);

        Intent intent = getIntent();
        Place place = intent.getParcelableExtra("mPlace");

        mViewRestViewModel = new ViewModelProvider(this, StaffViewModelFactory.getInstance()).get(ViewRestViewModel.class);

        mViewRestViewModel.getStaffDetail(place).observe(this, Staffs->{


        });
    }
}