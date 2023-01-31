package com.example.go4lunch.geo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.go4lunch.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeoFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    GeoViewModel viewModel;
    GoogleMap map;
    FloatingActionButton mFloatingActionButton;
    View view;
    Location loc = new Location("1.0,0.0");
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public GeoFragment() {
        // Required empty public constructor
    }

    /**

     * @return A new instance of fragment GeoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeoFragment newInstance() {
        GeoFragment fragment = new GeoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_geo, container, false);

        mFloatingActionButton = view.findViewById(R.id.center_buttonMap);

        viewModel = new ViewModelProvider(this, GeoViewModelFactory.getInstance()).get(GeoViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync( this);

        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                0
        );

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        viewModel.getViewStateLiveData().observe(getViewLifecycleOwner(), geoViewState -> {
            map.clear();
            for (GeoViewState viewState : geoViewState) {
                LatLng mCoord = new LatLng(viewState.getLat(), viewState.getLng());
                Log.d("Coord", mCoord+"");

                    map.addMarker(new MarkerOptions()
                            .position(mCoord));

                    map.addMarker(new MarkerOptions()
                            .position(mCoord)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }

            loc = viewModel.getUserLocation();

            if(loc != null) {
                LatLng finalMCoord = new LatLng(loc.getLatitude(), loc.getLongitude());
                float zoomLevel = 16f;

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(finalMCoord, zoomLevel));

            }
        });



        mFloatingActionButton.setOnClickListener(view -> {

            loc = viewModel.getUserLocation();

            if(loc != null) {
                LatLng finalMCoord = new LatLng(loc.getLatitude(), loc.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLng(finalMCoord));
            }

        });



        map.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if(clickCount != null){marker.getTitle();}

        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(view.getContext(), "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.refresh();
    }
}