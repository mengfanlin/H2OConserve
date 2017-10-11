package com.example.mengfanlin.h2oreserve.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.fragments.MainFragment;
import com.example.mengfanlin.h2oreserve.fragments.Tab1Fragment;
import com.example.mengfanlin.h2oreserve.fragments.Tab2Fragment;
import com.example.mengfanlin.h2oreserve.fragments.Tab3Fragment;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Water Trends");

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new Tab3Fragment(), "Figure1");
        adapter.addFragment(new Tab2Fragment(), "Figure2");
        adapter.addFragment(new Tab1Fragment(), "Figure3");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
//            if(position == 1)
//                setTitle("Australian Water Stress Levels");
//            if(position == 2)
//                setTitle("Water Usage by Monash Caulfield");
//            if(position == 3)
//                setTitle("Water Leak Calculator");
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0)
                return "Water Leak Calculator";
            if (position == 1)
                return "Water Usage at Monash";
            if (position == 2)
                return "Water Stress Levels";
            return "Water Related Open Data";
        }
    }

}
