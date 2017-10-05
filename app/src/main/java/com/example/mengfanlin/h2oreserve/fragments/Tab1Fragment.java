package com.example.mengfanlin.h2oreserve.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mengfanlin.h2oreserve.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class Tab1Fragment extends Fragment {

    View viewMain;
    LineChart lineChart;
    public Tab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewMain = inflater.inflate(R.layout.fragment_tab1, container, false);
        //getActivity().setTitle("Australian Water Stress");

        // Inflate the layout for this fragment
        LineChart lineChart =(LineChart) viewMain.findViewById(R.id.lineChart);
        List<Entry> entries = new ArrayList<>();

        //to display five values, and later formatter is used so years will not have decimal values
        float[] xAxis = {0f,1f,2f};
        float[] yAxis = {3.3f, 3.41f, 3.55f};
        for (int i=0; i<xAxis.length; i++){
            entries.add(new Entry(xAxis[i], yAxis[i]));
        }

        //implementing IAxisValueFormatter interface to show year values not as float/decimal
        final String[] years = new String[] { "2020", "2030", "2040" };

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return years[(int)value];
            }
        };

        LineDataSet dataSet = new LineDataSet(entries, "Water Stress Levels in Australia");
//        dataSet.setColor(112311);
//        dataSet.setValueTextColor(1);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColor(Color.BLUE);
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.animateX(2000);


        XAxis xAxisFromChart = lineChart.getXAxis();
        xAxisFromChart.setDrawAxisLine(true);
        xAxisFromChart.setValueFormatter(formatter);
        // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
        xAxisFromChart.setGranularity(1f);
        xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

//        YAxis yAxisLeft = lineChart.getAxisLeft();
//        yAxisLeft.setAxisMaximum(10);
//        yAxisLeft.setAxisMinimum(0);
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        return viewMain;
    }
}