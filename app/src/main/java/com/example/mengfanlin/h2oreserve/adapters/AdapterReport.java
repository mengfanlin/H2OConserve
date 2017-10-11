package com.example.mengfanlin.h2oreserve.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.entities.Report;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mengfanlin on 19/08/2017.
 * Table cell for displaying one report
 */

public class AdapterReport extends ArrayAdapter<Report> {

    public AdapterReport(Context context, ArrayList<Report> studentArrayList) {
        super(context, 0, studentArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Report report = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_report, parent, false);
        }
        // Lookup view for data population
        TextView textViewLocation = (TextView) convertView.findViewById(R.id.textView_location);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.textView_date);
        TextView textViewRoom = (TextView) convertView.findViewById(R.id.textView_room);
        TextView textViewStatus = (TextView) convertView.findViewById(R.id.textView_status);
        // Populate the data into the template view using the data object
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        String levelInfo = report.getLevel();
        String buildingInfo = report.getBuilding();
        if (!levelInfo.equals("Basement") && !levelInfo.equals("Ground")) {
            levelInfo = "Level " + levelInfo;
        }
        // If ground
        if (buildingInfo.startsWith("G") && !buildingInfo.equals("G")){
            textViewLocation.setText(buildingInfo);
            textViewRoom.setText(report.getRoom());
        } else {
            textViewLocation.setText("Building " + report.getBuilding() + " " + levelInfo);
            textViewRoom.setText("Room " + report.getRoom());
        }
        // Add color for status
        String status = report.getStatus();
        textViewStatus.setText(status);
        if (status.toLowerCase().startsWith("p"))
            textViewStatus.setTextColor(new Color().parseColor("#E53935"));
        if (status.toLowerCase().startsWith("v"))
            textViewStatus.setTextColor(new Color().parseColor("#FFD600"));
        if (status.toLowerCase().startsWith("c"))
            textViewStatus.setTextColor(new Color().parseColor("#43A047"));
        textViewDate.setText("Date added: " + formatter.format(report.getDate()));
        // Return the completed view to render on screen
        return convertView;
    }
}
