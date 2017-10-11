package com.example.mengfanlin.h2oreserve.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mengfanlin.h2oreserve.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Tab 3 is for drip calculator
 */
public class Tab3Fragment extends Fragment {

    private Spinner spinnerCalculator;
    private View viewMain;
    private TextView tvDripPerDay, tvLiterPerDay;

    public Tab3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_tab3, container, false);
        spinnerCalculator = (Spinner) viewMain.findViewById(R.id.spinner_calculator);
        tvDripPerDay = (TextView) viewMain.findViewById(R.id.tv_leak_per_day);
        tvLiterPerDay = (TextView) viewMain.findViewById(R.id.tv_leak_per_month);
        // Set spinner list
        List<String> dripList = new ArrayList<>();
        dripList.add("1");
        dripList.add("5");
        dripList.add("10");
        dripList.add("30");
        dripList.add("60");
        dripList.add("120");

        ArrayAdapter<String> dripSpinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(),R.layout.spinner_item,dripList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        // Set spinner style and content
        dripSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerCalculator.setAdapter(dripSpinnerArrayAdapter);
        spinnerCalculator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    tvDripPerDay.setText("Daily waste in liters:\n0.55");
                    tvLiterPerDay.setText("Monthly (30 days) waste in liters:\n16.5");
                }
                if (position == 1) {
                    tvDripPerDay.setText("Daily waste in liters:\n2.73");
                    tvLiterPerDay.setText("Monthly (30 days) waste in liters:\n81.9");
                }
                if (position == 2) {
                    tvDripPerDay.setText("Daily waste in liters:\n5.45");
                    tvLiterPerDay.setText("Monthly (30 days) waste in liters:\n163.5");
                }
                if (position == 3) {
                    tvDripPerDay.setText("Daily waste in liters:\n16.35");
                    tvLiterPerDay.setText("Monthly (30 days) waste in liters:\n490.5");
                }
                if (position == 4) {
                    tvDripPerDay.setText("Daily waste in liters:\n32.71");
                    tvLiterPerDay.setText("Monthly (30 days) waste in liters:\n981.3");
                }
                if (position == 5) {
                    tvDripPerDay.setText("Daily waste in liters:\n65.41");
                    tvLiterPerDay.setText("Monthly (30 days) waste in liters:\n1962.3");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return viewMain;
    }
}