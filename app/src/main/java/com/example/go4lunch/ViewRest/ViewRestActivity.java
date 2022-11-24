package com.example.go4lunch.ViewRest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.go4lunch.ListStaff.ListStaffRecyclerViewAdapter;
import com.example.go4lunch.R;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewRestActivity extends AppCompatActivity {

    ViewRestViewModel mViewRestViewModel;
    TextView mName_Detail;
    TextView mAdr_Detail;
    FloatingActionButton mButtonPresent;
    ImageButton mImageButtonWS;
    ImageButton mImageButtonCall;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rest);
        mName_Detail = findViewById(R.id.nameRDetail);
        mAdr_Detail = findViewById(R.id.adresse);
        mButtonPresent = findViewById(R.id.present);
        mRecyclerView = findViewById(R.id.list_staff_detail);
        mImageButtonWS = findViewById(R.id.website);
        mImageButtonCall = findViewById(R.id.call);

        Intent intent = getIntent();
        Place place = intent.getParcelableExtra("mPlace");

        mViewRestViewModel = new ViewModelProvider(this, StaffViewModelFactory.getInstance()).get(ViewRestViewModel.class);

        mViewRestViewModel.getRestDetail(place).observe(this, this::initPlace);

        mButtonPresent.setOnClickListener(view -> mViewRestViewModel.getUpdateUserRest(place).observe(ViewRestActivity.this, placeDetail ->{
            if(mButtonPresent.getContentBackground() == null) {
                initPlace(placeDetail);
                mButtonPresent.setImageResource(R.drawable.ic_baseline_check_circle_24);
            }else{
                mButtonPresent.setImageDrawable(null);
            }
        }));

        mImageButtonCall.setOnClickListener(view -> {
            Uri uri = Uri.parse("123456789");
            Intent intent1 = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent1);
        });

        mImageButtonWS.setOnClickListener(view -> {
            Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
            Intent intent12 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent12);
        });

        mViewRestViewModel.getListUserSameRest(place).observe(this, this::initListSameRest);
    }



    public void initListSameRest(List<User> users){
        mRecyclerView.setAdapter(new ListStaffRecyclerViewAdapter(users));
    }

    public void initPlace(Place place){
        mName_Detail.setText(place.getName());
        mAdr_Detail.setText(place.getAdr_address());
    }
}