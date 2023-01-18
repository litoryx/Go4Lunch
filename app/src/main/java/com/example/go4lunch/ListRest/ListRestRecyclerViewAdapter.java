package com.example.go4lunch.ListRest;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.ListStaff.ListStaffRecyclerViewAdapter;
import com.example.go4lunch.R;
import com.example.go4lunch.ViewRest.ViewRestActivity;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.AddressComponent;
import com.example.go4lunch.objetGoogle.Place;
import com.example.go4lunch.objetGoogle.PlaceOpeningHours;
import com.example.go4lunch.objetGoogle.PlaceOpeningHoursPeriod;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.startActivity;

public class ListRestRecyclerViewAdapter extends ListAdapter<Restaurant, ListRestRecyclerViewAdapter.ViewHolder> {

    public ListRestRecyclerViewAdapter(){
        super(new ItemCallback());
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
        holder.bind(getItem(position));
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameRest;
        public TextView mAdrRest;
        public TextView mOpen_horary;
        public TextView mDistance;
        public TextView mWorkmates;
        public ConstraintLayout mRest;
        public ImageView mPhtoRest;

        public ViewHolder(View view) {
            super(view);
            mNameRest = view.findViewById(R.id.nameRest);
            mAdrRest = view.findViewById(R.id.adrRest);
            mOpen_horary = view.findViewById(R.id.horary);
            mDistance = view.findViewById(R.id.distance);
            mWorkmates = view.findViewById(R.id.workmates_number);
            mRest = view.findViewById(R.id.rest);
            mPhtoRest = view.findViewById(R.id.imageView);
        }

        public void bind(Restaurant restaurant) {

            int numberUserInSameRest = restaurant.getNumbers_user_rest();
            Float numberMetersDistance = restaurant.getDistanceRest();
            int b = Math.round(numberMetersDistance);
            String stNumberMetersDistance = b+"m";
            String stNumberUserInSameRest = "("+numberUserInSameRest+")";
            PlaceOpeningHours opHours = restaurant.getOpening_hours();
            String adr_address = restaurant.getAdr_address();
            String name = restaurant.getName();
            String time = null;
            Number day = null;

            if(adr_address != null){mAdrRest.setText(adr_address);}else{
                mAdrRest.setText("Pas d'adresse"); }
            mDistance.setText(stNumberMetersDistance);
            mWorkmates.setText(stNumberUserInSameRest);
            mNameRest.setText(name);
            if(opHours != null) {
                List<PlaceOpeningHoursPeriod> openingHoursPeriods = opHours.getPeriods();
                if(openingHoursPeriods != null){
                    //Liste open et close la liste close n'est pas required openingHoursPeriods.get(0)
                    day = openingHoursPeriods.get(0).getPlaceOpeningHoursPeriodDetail().getDay();
                    time = openingHoursPeriods.get(0).getPlaceOpeningHoursPeriodDetail().getTime();}
                String result = day+" "+time;
                mOpen_horary.setText(result);
                Log.d("horaire","Horaire modifié");
            }else{mOpen_horary.setText("wtf");
                Log.d("horaire","Horaire null");}

            Glide.with(mPhtoRest.getContext()).load(restaurant.getPhoto()).into(mPhtoRest);

            mRest.setOnClickListener(view -> {
                Intent activityIntent = new Intent(view.getContext(), ViewRestActivity.class);
                activityIntent.putExtra("mPlace", restaurant);
                startActivity(view.getContext(), activityIntent, null);
            });
        }
    }

    private static class ItemCallback extends DiffUtil.ItemCallback<Restaurant> {
        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return true;
        }
    }
}
