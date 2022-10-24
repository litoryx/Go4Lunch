package com.example.go4lunch.ListRest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.objetGoogle.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
            String time = place.getOpening_hours().getPeriods().get(position).getPlaceOpeningHoursPeriodDetail().getTime();
            String adr_address = place.getAdr_address();

            holder.mNameRest.setText(place.getName());
            holder.mOpen_horary.setText(time);
            holder.mAdrRest.setText(adr_address);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameRest;
        public TextView mAdrRest;
        public TextView mOpen_horary;
        public TextView mDistance;
        public TextView mWorkmates;

        public ViewHolder(View view) {
            super(view);
            mNameRest = view.findViewById(R.id.nameRest);
            mAdrRest = view.findViewById(R.id.adrRest);
            mOpen_horary = view.findViewById(R.id.horary);
            mDistance = view.findViewById(R.id.distance);
            mWorkmates = view.findViewById(R.id.workmates);

        }
    }
}
