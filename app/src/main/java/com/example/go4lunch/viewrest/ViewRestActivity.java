package com.example.go4lunch.viewrest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.go4lunch.liststaff.ListStaffRecyclerViewAdapter;
import com.example.go4lunch.R;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.RestaurantDetailViewState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewRestActivity extends AppCompatActivity {

    ViewRestViewModel mViewRestViewModel;
    TextView mName_Detail;
    TextView mAdr_Detail;
    FloatingActionButton mButtonPresent;
    ImageButton mImageButtonWS;
    ImageButton mImageButtonCall;
    ImageButton mImageButtonFav;
    ImageView mStartRest;
    RecyclerView mRecyclerView;
    ListStaffRecyclerViewAdapter listStaffRecyclerViewAdapter = new ListStaffRecyclerViewAdapter();

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
        mStartRest = findViewById(R.id.star_rest);

        mStartRest.setVisibility(View.GONE);

        Intent intent = getIntent();
        Restaurant place = intent.getParcelableExtra("mPlace");

        mViewRestViewModel = new ViewModelProvider(this, StaffViewModelFactory.getInstance()).get(ViewRestViewModel.class);
        Log.d("PlaceDetail",""+place.getPlace_id());
        mViewRestViewModel.getRestDetail(place).observe(this, this::initPlace);

        mRecyclerView.setAdapter(listStaffRecyclerViewAdapter);
    }

    public void initPlace(RestaurantDetailViewState place){
        Log.d("PlaceDetail","nom : "+place.getName());
        mName_Detail.setText(place.getName());
        mAdr_Detail.setText(place.getAdr_address());

        listStaffRecyclerViewAdapter.submitList(place.getUserCurrentRest());
        listStaffRecyclerViewAdapter.notifyDataSetChanged();

        mButtonPresent.setOnClickListener(view -> {
            if(mButtonPresent.getContentBackground() == null || place.getRestCurrent() == null){
                mViewRestViewModel.setUpdateRestChoose(place); //Est-ce bon comme ça ? car sinon je ne comprend pas alors setOnclickListener comment le gérer
                mButtonPresent.setImageResource(R.drawable.ic_baseline_check_circle_24);
                }
            else{
                mButtonPresent.setImageDrawable(null);
                }
        });

        mImageButtonFav.setOnClickListener(view -> {
            if(!place.getFav()){
                mViewRestViewModel.setUpdateListFavRest(place);
                mStartRest.setVisibility(View.VISIBLE);
            }else{
                mStartRest.setVisibility(View.INVISIBLE);
            }
        });

        mImageButtonCall.setOnClickListener(view -> {
            String phoneNumber = place.getFormatted_phone_number();
            Uri uri = Uri.parse(phoneNumber);
            Intent intent1 = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent1);
        });

        mImageButtonWS.setOnClickListener(view -> {
            String uRi = place.getUrl();
            Uri uri = Uri.parse(uRi); // missing 'http://' will cause crashed
            Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent2);
        });
    }
}