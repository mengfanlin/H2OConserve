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

        // scaling can now only be done on x- and y-axis separately
//        barChart.setPinchZoom(false);
//
//        barChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);


//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//        xAxis.setLabelCount(7);
//
//
//        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        YAxis rightAxis = barChart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        Legend l = barChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//         l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
//         "def", "ghj", "ikl", "mno" });
//         l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
//         "def", "ghj", "ikl", "mno" });

        // mChart.setDrawLegend(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        //to display five values, and later formatter is used so years will not have decimal values
        float[] xAxis = {0f,1f,2f,3f,4f};
        float[] yAxis = {52542.61f, 63406.65f, 57283.53f, 64879.91f, 54942.99f};
        for (int i=0; i<xAxis.length; i++){
            barEntries.add(new BarEntry(xAxis[i], yAxis[i]));
        }
//        barEntries.add(new BarEntry(52542.61f,0));
//        barEntries.add(new BarEntry(63406.65f,1));
//        barEntries.add(new BarEntry(57283.53f,2));
//        barEntries.add(new BarEntry(64879.91f,3));
//        barEntries.add(new BarEntry(54942.99f,4));

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