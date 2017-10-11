package com.example.mengfanlin.h2oreserve.adapters;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Map;

/**
 * Created by mengfanlin on 26/08/2017.
 * Adapter for popup window after selecting the pin on map
 */
public class AdapterLeakInfo implements GoogleMap.InfoWindowAdapter {

    private Map<Marker, Report> markerLeaks;

    private Activity activity;

    public AdapterLeakInfo(Map<Marker, Report> markerReports, Activity activity) {
        this.markerLeaks = markerReports;
        this.activity = activity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.e("markerStudents", markerLeaks.toString());
        if (markerLeaks.get(marker) == null) return null;

        View viewInfoWindow = activity.getLayoutInflater().inflate(R.layout.info_window_leak, null);

        TextView building = (TextView) viewInfoWindow.findViewById(R.id.leak_location);
        TextView description = (TextView) viewInfoWindow.findViewById(R.id.leak_description);

        Report leakReport = markerLeaks.get(marker);

        building.setText("Location: " + leakReport.getBuilding() + leakReport.getRoom());
        description.setText("Description: " + leakReport.getDescription());

        return viewInfoWindow;
    }
}
