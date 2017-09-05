package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.mengfanlin.h2oreserve.R;

/**
 * Created by mengfanlin on 15/08/2017.
 */

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private View viewMain;
    //private TextView welcomeTextView;
    private ImageButton imageButtonSubmitReport, imageButtonCheckReports, imageButtonCheckLeaksOnMap;
    private NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_main, container, false);
        //welcomeTextView = (TextView) viewMain.findViewById(R.id.text_welcome);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        getActivity().setTitle("Main Page");
        //welcomeTextView.setText("Welcome to H2O CONSERVE!");
        imageButtonSubmitReport = (ImageButton) viewMain.findViewById(R.id.imageButton_submit);
        imageButtonCheckReports = (ImageButton) viewMain.findViewById(R.id.imageButton_my_reports);
        imageButtonCheckLeaksOnMap = (ImageButton) viewMain.findViewById(R.id.imageButton_view_leaks);
        imageButtonCheckReports.setOnClickListener(new onNavigationButtonsClickedListener());
        imageButtonSubmitReport.setOnClickListener(new onNavigationButtonsClickedListener());
        imageButtonCheckLeaksOnMap.setOnClickListener(new onNavigationButtonsClickedListener());
        return viewMain;
    }

    private class onNavigationButtonsClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            int drawerItemIndex = 0;
            Fragment fragment = null;
            //final MediaPlayer mp = MediaPlayer.create(this, R.raw.waterdrop);
            if (id == R.id.imageButton_submit) {
                drawerItemIndex = 1;
                fragment = new SupplyReportFragment();
            } else if (id == R.id.imageButton_my_reports) {
                drawerItemIndex = 2;
                fragment = new CheckReportFragment();
            }
            else if (id == R.id.imageButton_view_leaks) {
                drawerItemIndex = 3;
                fragment = new ReportMapFragment();
            }
            navigationView.getMenu().getItem(drawerItemIndex).setChecked(true);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



}
