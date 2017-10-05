package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.activities.LeakInBuildingActivity;
import com.example.mengfanlin.h2oreserve.entities.CountReports;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mengfanlin on 26/08/2017.
 */

public class ReportMapFragment extends Fragment implements OnMapReadyCallback, OnInfoWindowClickListener {

    private CountReportTask countReportTask;
    private ArrayList<CountReports> countReportsList;
    private Marker currLocationMarker;
    private GoogleApiClient google;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private Map<Marker, Report> markReports;
    private List<Marker> markers;
    private View viewMain;

    private static final String COMBINATION_STRING = " water leak(s) found here!";
    private static final LatLng LAT_LNG_OF_A = new LatLng(-37.877168D, 145.045377D);
    private static final LatLng LAT_LNG_OF_B = new LatLng(-37.877663D, 145.045335D);
    private static final LatLng LAT_LNG_OF_C = new LatLng(-37.877799D, 145.045994D);
    private static final LatLng LAT_LNG_OF_D = new LatLng(-37.877858D, 145.046606D);
    private static final LatLng LAT_LNG_OF_E = new LatLng(-37.8776D, 145.04674D);
    private static final LatLng LAT_LNG_OF_F = new LatLng(-37.87718D, 145.046289D);
    private static final LatLng LAT_LNG_OF_G = new LatLng(-37.876668D, 145.045512D);
    private static final LatLng LAT_LNG_OF_H = new LatLng(-37.876304D, 145.044353D);
    private static final LatLng LAT_LNG_OF_J = new LatLng(-37.876261D, 145.043558D);
    private static final LatLng LAT_LNG_OF_K = new LatLng(-37.877521D, 145.044325D);
    private static final LatLng LAT_LNG_OF_N = new LatLng(-37.877439D, 145.043811D);
    private static final LatLng LAT_LNG_OF_S = new LatLng(-37.877135D, 145.043422D);
    private static final LatLng LAT_LNG_OF_T = new LatLng(-37.878155D, 145.045077D);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_leaks_map, container, false);
        getActivity().setTitle("All Leaks at Campus");

//        // Gets the MapView from the XML layout and creates it
//        mMapView = (MapView) viewMain.findViewById(R.id.map);
//        mMapView.onCreate(savedInstanceState);
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


        return viewMain;
    }

    public void setMarker(String building, String count){

        switch (building) {
            case "A":
                Log.e("case A is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_A)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "B":
                Log.e("case B is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_B)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "C":
                Log.e("case C is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_C)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "D":
                Log.e("case D is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_D)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "E":
                Log.e("case E is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_E)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "F":
                Log.e("case F is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_F)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "G":
                Log.e("case G is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_G)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "H":
                Log.e("case H is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_H)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "J":
                Log.e("case J is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_J)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "K":
                Log.e("case K is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_K)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "N":
                Log.e("case N is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_N)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "S":
                Log.e("case S is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_S)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            case "T":
                Log.e("case T is invoked", building);
                markers.add(mGoogleMap.addMarker(new MarkerOptions()
                        .position(LAT_LNG_OF_T)
                        .title("Building " + building).snippet(count + COMBINATION_STRING)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
                break;
            default:
                break;

        }
    }

    protected class CountReportTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String response = RestClient.countReports();
            return response;
        }

        @Override
        protected void onPostExecute(String response) {

            if (!response.startsWith("F")) {
                try {
                    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                    countReportsList = gson.fromJson(response, new TypeToken<ArrayList<CountReports>>() {
                    }.getType());
                    Log.e("countReports List is", ReportMapFragment.this.countReportsList.toString());
                    for (CountReports c : countReportsList) {
                        setMarker(c.getBuilding(), c.getCount() + "");
                        Log.e("Building and count", c.getBuilding() + " " + c.getCount());
                    }
                    moveCamera();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to load data..", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) viewMain.findViewById(R.id.map);

        markers = new ArrayList<>();

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
            googleMap.setOnInfoWindowClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGoogleMap = googleMap;
        // mGoogleMap.setInfoWindowAdapter(new AdapterLeakInfo(markReports,getActivity()));
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }



    public void moveCamera() {
        if (markers.size() == 0)
            return;

        if (markers.size() == 1) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getPosition(), 16));
        } else {
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                boundsBuilder.include(marker.getPosition());
            }
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 250));
        }
    }

    private void fetchResultFromServer()
    {
        this.countReportTask = new CountReportTask();
        this.countReportTask.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        markers.clear();
        fetchResultFromServer();
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        Log.i("google location", location.toString());
//        currLocationMarker = showCurrentLocation(location);
//    }

//    protected Marker showCurrentLocation(android.location.Location location) {
//        if (mGoogleMap == null) return null;
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
//            currLocationMarker = mGoogleMap.addMarker(markerOptions);
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

    @Override
    public void onInfoWindowClick(Marker marker) {
        String title = marker.getTitle().toString();
        String building = title.substring(title.length() - 1, title.length());
        Log.e("clicked building", building);
        Intent localIntent = new Intent(getActivity(), LeakInBuildingActivity.class);
        localIntent.putExtra("building", building);
        startActivity(localIntent);
    }
}
