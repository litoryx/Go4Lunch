package com.example.go4lunch.ListRest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.ViewRest.ViewRestActivity;
import com.example.go4lunch.objetGoogle.Place;
import com.example.go4lunch.objetGoogle.PlaceOpeningHoursPeriod;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;

public class ListRestRecyclerViewAdapter extends RecyclerView.Adapter<ListRestRecyclerViewAdapter.ViewHolder> {

    List<Place> mList;


    public ListRestRecyclerViewAdapter(List<Place> mListRest){
        mList = mListRest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRestRecyclerViewAdapter.ViewHolder holder, int position) {
            Place place = mList.get(position);
            List<PlaceOpeningHoursPeriod> opHours = place.getOpening_hours().getPeriods();
            String adr_address = place.getAdr_address();
            String name = place.getName();
            String time;

            holder.mNameRest.setText(name);
            if(opHours != null) {
                time = opHours.get(position).getPlaceOpeningHoursPeriodDetail().getTime();
                holder.mOpen_horary.setText(time); }else{holder.mOpen_horary.setText("Pas d'horaire");}
            holder.mAdrRest.setText(adr_address);

            holder.mRest.setOnClickListener(view -> {
                Intent activityIntent = new Intent(view.getContext(), ViewRestActivity.class);
                activityIntent.putExtra("mPlace", place);
                startActivity(view.getContext(), activityIntent, null);
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameRest;
        public TextView mAdrRest;
        public TextView mOpen_horary;
        public TextView mDistance;
        public TextView mWorkmates;
        public ConstraintLayout mRest;

        public ViewHolder(View view) {
            super(view);
            mNameRest = view.findViewById(R.id.nameRest);
            mAdrRest = view.findViewById(R.id.adrRest);
            mOpen_horary = view.findViewById(R.id.horary);
            mDistance = view.findViewById(R.id.distance);
            mWorkmates = view.findViewById(R.id.workmates);
            mRest = view.findViewById(R.id.rest);
        }
    }
}
