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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListRestRecyclerViewAdapter.ViewHolder holder, int position) {
            Place place = mList.get(position);
            String time = place.getOpening_hours().getPeriods().get(position).getPlaceOpeningHoursPeriodDetail().getTime();
            String adr_address = place.getAdr_address();

            holder.mNameRest.setText(place.getName());
            if(time != null) { holder.mOpen_horary.setText(time); }else{holder.mOpen_horary.setText("Pas d'horaire");}
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
