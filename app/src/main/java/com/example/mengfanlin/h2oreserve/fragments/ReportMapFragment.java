package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mengfanlin.h2oreserve.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;


import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by mengfanlin on 26/08/2017.
 */

public class ReportMapFragment extends Fragment implements OnMapReadyCallback{

    private View viewMain;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private GoogleApiClient google;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_leaks_map, container, false);

//        // Gets the MapView from the XML layout and creates it
//        mMapView = (MapView) viewMain.findViewById(R.id.map);
//        mMapView.onCreate(savedInstanceState);
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


        return viewMain;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) viewMain.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
           mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       // MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng sydney = new LatLng(-33.852, 151.211);

        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").snippet("I hope to go there"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        map = googleMap;
//        // Add a marker in Sydney, Australia,
//        // and move the map's camera to the same location.

//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
