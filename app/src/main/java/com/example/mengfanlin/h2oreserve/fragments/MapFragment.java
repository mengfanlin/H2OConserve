//package com.example.mengfanlin.h2oreserve.fragments;
//
//
///**
// * Created by mengfanlin on 26/08/2017.
// */
//
//
//
//import android.Manifest;
//import android.app.Fragment;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.example.mengfanlin.h2oreserve.adapters.AdapterLeakInfo;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.mapbox.mapboxsdk.MapboxAccountManager;
//import com.mapquest.mapping.maps.OnMapReadyCallback;
//import com.mapquest.mapping.maps.MapboxMap;
//import com.mapquest.mapping.maps.MapView;
//
//import com.example.mengfanlin.h2oreserve.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by mengfanlin on 26/08/2017.
// */
//
//public class MapFragment extends Fragment
//        implements LocationListener,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener{
//
//    private View viewMain;
//    private MapboxMap mMapboxMap;
//    private MapView mapView;
//    private List<Marker> markers;
//    private GoogleMap map;
//    private GoogleApiClient google;
//    private Marker currLocationMarker;
//
//    public void onCreateView(Bundle savedInstanceState) {
//
//        mapView = (MapView) viewMain.findViewById(R.id.mapView);
//        //mapView.setVisibility(View.INVISIBLE); //??
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
//
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        markers = new ArrayList<>();
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        if (!requestLocation()) return;
//        getLastLocation();
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(getActivity(),
//                "Cannot connect to google map api, " + connectionResult.getErrorMessage(),
//                Toast.LENGTH_LONG).show();
//        Log.e("google connection", connectionResult.getErrorMessage());
//    }
//
//    @Override
//    public void onLocationChanged(android.location.Location location) {
//        Log.i("google location", location.toString());
//        currLocationMarker = showCurrentLocation(location);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        google.connect();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (google != null && google.isConnected()) {
//            google.stopAutoManage((FragmentActivity) getActivity());
//            google.disconnect();
//        }
//    }
//
//    protected boolean requestLocation() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.e("google location", "no location permission");
//
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
//                   0); //Permission.PERMISSION_REQUEST_LOCATION
//
//            return false;
//        }
//
//        LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(5000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationServices.FusedLocationApi.requestLocationUpdates(
//                google, locationRequest, this);
//
//        return true;
//    }
//
//
//    protected void getLastLocation() {
//        try {
//            android.location.Location location = LocationServices.FusedLocationApi.getLastLocation(google);
//
//            if (location != null) {
//                Log.i("google location", location.toString());
//                showCurrentLocation(location);
//            }
//        } catch (SecurityException e) {
//            Log.e("google location", e.getMessage());
//        }
//    }
//
//    protected Marker showCurrentLocation(android.location.Location location) {
//        if (map == null) return null;
//
//        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
//
//        if (currLocationMarker == null) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),
//                    R.drawable.current_location);
//
//            MarkerOptions markerOptions = new MarkerOptions()
//                    .position(point)
//                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//                    .anchor(0.5f, 0.5f);
//
//            currLocationMarker = map.addMarker(markerOptions);
//        } else {
//            currLocationMarker.setPosition(point);
//            markers.remove(currLocationMarker);
//        }
//        markers.add(currLocationMarker);
//
//        moveCamera();
//
//        return currLocationMarker;
//    }
//
//    protected void moveCamera() {
//        if (markers.size() == 0) return;
//
//        if (markers.size() == 1) {
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getPosition(), 16));
//        } else {
//            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
//            for (Marker marker : markers) {
//                boundsBuilder.include(marker.getPosition());
//            }
//            map.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 200));
//        }
//    }
//
//}
