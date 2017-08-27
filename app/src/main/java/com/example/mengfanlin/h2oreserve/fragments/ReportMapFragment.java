package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.adapters.AdapterLeakInfo;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

/**
 * Created by mengfanlin on 26/08/2017.
 */

public class ReportMapFragment extends Fragment implements OnMapReadyCallback{

    private View viewMain;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private GoogleApiClient google;
    private Map<Marker, Report> markReports;

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
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGoogleMap = googleMap;

        //mGoogleMap.setInfoWindowAdapter(new AdapterLeakInfo(markReports,getActivity()));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng leak1 = new LatLng(-37.876651, 145.045153);
        LatLng leak2 = new LatLng(-37.877625, 145.045193);
        LatLng leak3 = new LatLng(-37.878132, 145.045078);
        LatLng leak4 = new LatLng(-37.877878, 145.046900);


        googleMap.addMarker(new MarkerOptions().position(leak1)
                .title("A leak in Building K").snippet("K234")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addMarker(new MarkerOptions().position(leak2)
                .title("A leak in Building B").snippet("B110")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addMarker(new MarkerOptions().position(leak3)
                .title("A leak in Building T").snippet("T301")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addMarker(new MarkerOptions().position(leak4)
                .title("A water leak in Building D").snippet("D101")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));



        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(leak1);
        builder.include(leak2);
        builder.include(leak3);
        builder.include(leak4);
        LatLngBounds bounds = builder.build();

        int padding = 300; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        //googleMap.moveCamera(cu);
        googleMap.animateCamera(cu);


//        for (Marker marker : markers) {
//            builder.include(marker.getPosition());
//        }
//        LatLngBounds bounds = builder.build();


        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.876851,145.044703),5));

//        map = googleMap;
//        // Add a marker in Sydney, Australia,
//        // and move the map's camera to the same location.

//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
