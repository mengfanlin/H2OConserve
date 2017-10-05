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
        tvDripPerDay = (TextView) viewMain.findViewById(R.id.tv_drip_per_day);
        tvLiterPerDay = (TextView) viewMain.findViewById(R.id.tv_liter_per_day);

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
        dripSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerCalculator.setAdapter(dripSpinnerArrayAdapter);

        spinnerCalculator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    tvDripPerDay.setText("1,440 drips per day");
                    tvLiterPerDay.setText("0.3 liter per day");
                }
                if (position == 1) {
                    tvDripPerDay.setText("7,200 drips per day");
                    tvLiterPerDay.setText("2 liters per day");
                }
                if (position == 2) {
                    tvDripPerDay.setText("14,400 drips per day");
                    tvLiterPerDay.setText("4 liter per day");
                }
                if (position == 3) {
                    tvDripPerDay.setText("43,200 drips per day");
                    tvLiterPerDay.setText("14 liter per day");
                }
                if (position == 4) {
                    tvDripPerDay.setText("86,400 drips per day");
                    tvLiterPerDay.setText("28 liter per day");
                }
                if (position == 5) {
                    tvDripPerDay.setText("172,800 drips per day");
                    tvLiterPerDay.setText("57 liter per day");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return viewMain;
    }
}