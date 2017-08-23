package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.AdapterReport;
import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.activities.ModifyReportActivity;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfanlin on 15/08/2017.
 */

public class CheckReportFragment extends Fragment {

    private View viewMain;
    private AdapterReport reportAdapter;
    private ArrayList<Report> reportArrayList;
    private ListView listView;
    private TextView textViewLocation;
    private TextView textViewDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_check_report, container, false);
        getActivity().setTitle("My Reports");
        reportArrayList = new ArrayList<>();

        // Create the adapter to convert the array to views
        reportAdapter = new AdapterReport(getActivity(), reportArrayList);
        // Attach the adapter to a ListView
        listView = (ListView) viewMain.findViewById(R.id.listView_check_report);
        listView.setAdapter(reportAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ModifyReportActivity.class);
                //intent.putExtra("Student", studentArrayList.get(position));
                intent.putExtra("chosenReport",reportArrayList.get(position));
                startActivity(intent);
            }
        });

        return viewMain;
    }


    public void displayAllReports() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                String response = RestClient.getReports(params[0]);
                Log.e("Response is",response);
                return response;
            }

            @Override
            protected void onPostExecute(String response) {

                try {
                    //int studentId = Integer.parseInt(loggedInStudentId);
                    if (!response.equals("false")) {

                        ArrayList<Report> reports = new ArrayList<>();
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX").create();
                        reportArrayList = gson.fromJson(response, new TypeToken<ArrayList<Report>>() {
                        }.getType());

                        reportAdapter.clear();

                        for (Report r : reportArrayList) {
                            Log.e("", r.toString());
                            reports.add(r);
                        }

                        Log.e("List of Students", reports.toString());
                        reportAdapter.addAll(reports);
                        reportAdapter.notifyDataSetChanged();
                    } else {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "failed to display your reports", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }catch (Exception e) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        }.execute("mfl333124@gmail.com"); //TODO use google account
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("", "onResume()");
        displayAllReports();
    }
}
