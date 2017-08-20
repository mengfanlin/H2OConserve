package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mengfanlin.h2oreserve.R;

/**
 * Created by mengfanlin on 15/08/2017.
 */

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private View viewMain;
    private TextView welcomeTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_main, container, false);
        welcomeTextView = (TextView) viewMain.findViewById(R.id.text_welcome);
        getActivity().setTitle("H2O CONSERVE");
        welcomeTextView.setText("Welcome to H2O CONSERVE!");

        return viewMain;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
