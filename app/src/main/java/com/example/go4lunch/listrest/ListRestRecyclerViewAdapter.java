package com.example.go4lunch.listrest;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.viewrest.ViewRestActivity;
import com.example.go4lunch.models.Restaurant;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

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
            String name = restaurant.getName();

            mAdrRest.setText(restaurant.getAdr());
            mDistance.setText(stNumberMetersDistance);
            mWorkmates.setText(stNumberUserInSameRest);
            mNameRest.setText(name);

            if(restaurant.getOpen()){
            mOpen_horary.setText("Ouvert actuellement");
            mOpen_horary.setTextColor(ContextCompat.getColor(this.mOpen_horary.getContext(),R.color.green));
            }else{mOpen_horary.setText("Fermé");}

            Log.d("getPhoto",""+restaurant.getPhoto());
            Log.d("getPhoto",""+restaurant.getUrl());
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
