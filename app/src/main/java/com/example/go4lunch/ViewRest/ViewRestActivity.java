package com.example.go4lunch.ViewRest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.objetGoogle.Place;

public class ViewRestActivity extends AppCompatActivity {

    ViewRestViewModel mViewRestViewModel;
    TextView mName_Detail;
    TextView mAdr_Detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rest);
        mName_Detail = findViewById(R.id.nameRDetail);
        mAdr_Detail = findViewById(R.id.adresse);

        Intent intent = getIntent();
        Place place = intent.getParcelableExtra("mPlace");

        mViewRestViewModel = new ViewModelProvider(this, StaffViewModelFactory.getInstance()).get(ViewRestViewModel.class);

        mViewRestViewModel.getRestDetail(place).observe(this, Staffs->{
            mName_Detail.setText(Staffs.getName());
            mAdr_Detail.setText(Staffs.getAdr_address());
        });
    }
}