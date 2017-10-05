package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.activities.ViewDataActivity;
import com.example.mengfanlin.h2oreserve.db.DBManager;

/**
 * Created by mengfanlin on 15/08/2017.
 */

public class MainFragment extends Fragment {

    private View viewMain;
    //private TextView welcomeTextView;
    private NavigationView navigationView;
    private ImageView imageView2, imageView4;
    private int numOfReports;
    private TextView textView4, textView5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_main, container, false);
        //welcomeTextView = (TextView) viewMain.findViewById(R.id.text_welcome);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        getActivity().setTitle("Home Page");
        //welcomeTextView.setText("Welcome to H2O CONSERVE!");
//        imageButtonSubmitReport = (ImageButton) viewMain.findViewById(R.id.imageButton_submit);
//        imageButtonCheckReports = (ImageButton) viewMain.findViewById(R.id.imageButton_my_reports);
//        imageButtonCheckLeaksOnMap = (ImageButton) viewMain.findViewById(R.id.imageButton_view_leaks);
//        imageButtonCheckReports.setOnClickListener(new onNavigationButtonsClickedListener());
//        imageButtonSubmitReport.setOnClickListener(new onNavigationButtonsClickedListener());
//        imageButtonCheckLeaksOnMap.setOnClickListener(new onNavigationButtonsClickedListener());
        imageView2 = (ImageView) viewMain.findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new onNavigationButtonsClickedListener());
        imageView4 = (ImageView) viewMain.findViewById(R.id.imageView4);
        textView4 = (TextView) viewMain.findViewById(R.id.textView4);
        textView5 = (TextView) viewMain.findViewById(R.id.textView5);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You will get one star(Maximum 3) for every 5 reports", Toast.LENGTH_SHORT).show();
            }
        });
        showNumOfReports();
        return viewMain;
    }

    private void showNumOfReports() {
        DBManager dbManager = new DBManager(getActivity());
        dbManager.open();
        numOfReports = dbManager.getAllReportIds().size();
        textView4.setText("You've submitted " + numOfReports + " report(s)");
        if (numOfReports == 0) {
            textView5.setText("Please help Monash save water!");
        } else {
            textView5.setText("Thanks for your contribution!");
        }
        if (numOfReports < 5)
            imageView4.setVisibility(View.GONE);
        else if (numOfReports >= 5 && numOfReports < 10)
            imageView4.setImageResource(R.drawable.chevron1);
        else if (numOfReports >= 10 && numOfReports < 15)
            imageView4.setImageResource(R.drawable.chevron2);
        else if (numOfReports >= 15)
            imageView4.setImageResource(R.drawable.chevron3);
        dbManager.close();
    }


    private class onNavigationButtonsClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            int drawerItemIndex = 0;
            Fragment fragment = null;
            //final MediaPlayer mp = MediaPlayer.create(this, R.raw.waterdrop);

            if (id == R.id.imageView2) {
                drawerItemIndex = 1;
                fragment = new SupplyReportFragment();
            }
//            } else if (id == R.id.imageButton_my_reports) {
//                drawerItemIndex = 2;
//                fragment = new CheckReportFragment();
//            }
//            else if (id == R.id.imageButton_view_leaks) {
//                drawerItemIndex = 3;
//                fragment = new ReportMapFragment();
//            } else if (id == R.id.imageButton_settings) {
//                drawerItemIndex = 4;
//            }
            navigationView.getMenu().getItem(drawerItemIndex).setChecked(true);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }
}
