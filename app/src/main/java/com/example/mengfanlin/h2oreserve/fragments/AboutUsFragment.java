package com.example.mengfanlin.h2oreserve.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mengfanlin.h2oreserve.R;

/**
 * Created by mengfanlin on 08/10/2017.
 */
public class AboutUsFragment extends Fragment {

    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        getActivity().setTitle("About Us");
        return rootView;
    }
}
