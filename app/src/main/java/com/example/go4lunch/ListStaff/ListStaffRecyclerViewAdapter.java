package com.example.go4lunch.ListStaff;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.go4lunch.ListRest.ListRestRecyclerViewAdapter;
import com.example.go4lunch.R;
import com.example.go4lunch.models.User;
import com.example.go4lunch.objetGoogle.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
//Le problème est-ce que j'ai utilisé un RecyclerView.Adapter au lieu de celui de FirestoreAdapter,
// la liste n'apparait pas, mais ne me met aucune erreur aussi donc je me dit que c'est plus le connexion à Firestore ?
public class ListStaffRecyclerViewAdapter extends ListAdapter<User, ListStaffRecyclerViewAdapter.ViewHolder> {


    public ListStaffRecyclerViewAdapter(){
        super(new ItemCallback());
        }

    @NonNull
    @Override
    public ListStaffRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item_staff, parent, false);
        return new ListStaffRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mUsername;
        public TextView mStyleR;
        public TextView mResto;

        public ViewHolder(View view) {
            super(view);
            mUsername = view.findViewById(R.id.username);
            mStyleR = view.findViewById(R.id.styleR);
            mResto = view.findViewById(R.id.resto);
        }

        public void bind(User user) {

                String username = user.getUsername();
            mUsername.setText(username);
            if(user.getRestaurantChoose() != null){
                String styleR = user.getRestaurantChoose().getStyle();
                String resto = user.getRestaurantChoose().getName();

                mStyleR.setText(styleR);
                mResto.setText(resto);
            }



        }
    }

    private static class ItemCallback extends DiffUtil.ItemCallback<User> {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return true;
        }
    }
}
