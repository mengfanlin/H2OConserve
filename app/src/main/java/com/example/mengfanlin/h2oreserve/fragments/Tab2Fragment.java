package com.example.mengfanlin.h2oreserve.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mengfanlin.h2oreserve.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 *  Tab2 for monash water usage
 *  Reference: MPAndroidChart
 */
public class Tab2Fragment extends Fragment {

    View viewMain;
    BarChart barChart;

    public Tab2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewMain = inflater.inflate(R.layout.fragment_tab2, container, false);
        //getActivity().setTitle("Monash Water Usage");
        barChart = (BarChart) viewMain.findViewById(R.id.barChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        //to display five values, and later formatter is used so years will not have decimal values
        float[] xAxis = {0f,1f,2f,3f,4f};
        float[] yAxis = {52542.61f, 63406.65f, 57283.53f, 64879.91f, 54942.99f};
        for (int i=0; i<xAxis.length; i++){
            barEntries.add(new BarEntry(xAxis[i], yAxis[i]));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Water Usage of Each Year by Monash Caulfield");
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setColor(Color.BLUE);
        barDataSet.setValueTextSize(12f);

        ArrayList<String> theYears = new ArrayList<>();
        theYears.add("2012");
        theYears.add("2013");
        theYears.add("2014");
        theYears.add("2015");
        theYears.add("2016");

        //implementing IAxisValueFormatter interface to show year values not as float/decimal
        final String[] years = new String[] { "2012", "2013", "2014", "2015", "2016 Year"};

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return years[(int)value];
            }
        };

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxisFromChart = barChart.getXAxis();
        xAxisFromChart.setDrawAxisLine(true);
        xAxisFromChart.setValueFormatter(formatter);
        xAxisFromChart.setGranularity(1f);
        xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMaximum(100000);
        yAxisLeft.setAxisMinimum(0);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        //leftAxis.setSpaceBottom(20f);
        //leftAxis.setAxisMinimum(50000f);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setDragEnabled(true);
//        Description d = new Description();
//        d.setText("Year");
//        barChart.setDescription(d);
        barChart.animateY(2500);
        barChart.getDescription().setEnabled(false);

        return viewMain;
    }
}