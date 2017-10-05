package com.example.mengfanlin.h2oreserve.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mengfanlin.h2oreserve.fragments.Tab1Fragment;
import com.example.mengfanlin.h2oreserve.fragments.Tab2Fragment;
import com.example.mengfanlin.h2oreserve.fragments.Tab3Fragment;

/**
 * Created by mengfanlin on 15/9/17.
 */

public class AdapterPager extends FragmentPagerAdapter {

    int mNoOfTabs;

    public AdapterPager(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mNoOfTabs = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Tab1Fragment tab1 = new Tab1Fragment();
                return tab1;
            case 1:
                Tab2Fragment tab2 = new Tab2Fragment();
                return tab2;
            case 2:
                Tab3Fragment tab3 = new Tab3Fragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}



