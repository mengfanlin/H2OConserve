package com.example.mengfanlin.h2oreserve.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mengfanlin.h2oreserve.R;

/**
 * Created by mengfanlin on 08/10/2017.
 *  Water trends fragment
 */
public class WaterTrendsFragment extends Fragment {
    View rootView;
    private FragmentTabHost mTabHost;

    public WaterTrendsFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_water_trends, container, false);
        getActivity().setTitle("Water Trends");
        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realTabContent);

        mTabHost.addTab(mTabHost.newTabSpec("fragment1").setIndicator("Water Leak Calculator"),
                Tab3Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragment2").setIndicator("Water Usage of Campus"),
                Tab2Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragment3").setIndicator("Water Stress Levels"),
                Tab1Fragment.class, null);
        return rootView;
    }
}
