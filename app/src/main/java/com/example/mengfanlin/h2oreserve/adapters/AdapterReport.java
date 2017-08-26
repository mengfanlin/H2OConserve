package com.example.mengfanlin.h2oreserve.adapters;

import android.content.Context;
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


        // Populate the data into the template view using the data object

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");

        textViewLocation.setText("Building " + report.getBuilding() + " " + report.getRoom());

        textViewDate.setText(formatter.format(report.getDate()));
        // Return the completed view to render on screen
        return convertView;
    }
}
