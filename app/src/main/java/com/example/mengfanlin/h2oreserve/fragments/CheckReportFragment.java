package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.activities.ModifyReportActivity;
import com.example.mengfanlin.h2oreserve.adapters.AdapterReport;
import com.example.mengfanlin.h2oreserve.db.DBManager;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mengfanlin on 15/08/2017.
 */

public class CheckReportFragment extends Fragment {

    private DBManager dbManager;
    private ListView listView;
    private AdapterReport reportAdapter;
    private ArrayList<Report> reportArrayList;
    private View viewMain;
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
                intent.putExtra("classFrom", "CheckReportFragment");
                startActivity(intent);
            }
        });

        View view = getActivity().getCurrentFocus();
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewMain;
    }


    public void displayAllReports() {

        StringBuilder sb = new StringBuilder();
        dbManager = new DBManager(getActivity());
        dbManager.open();
        try {
            ArrayList<String> ids = dbManager.getAllReportIds();
            Log.e("ids are", ids.toString());
            for (String id : ids) {
                sb.append(id).append("-");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //dbManager.deleteAll();
        dbManager.close();
        if (sb.toString().equals("")){
            reportAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity().getApplicationContext(), "No reports can be displayed", Toast.LENGTH_LONG);
            return;
        }
        String idsString = sb.toString().substring(0, sb.toString().length()-1);
        Log.e("idsString", idsString);

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                String response = RestClient.getMyReports(params[0]);
                Log.e("Response is",response);
                return response;
            }

            @Override
            protected void onPostExecute(String response) {

                try {
                    //int studentId = Integer.parseInt(loggedInStudentId);
                    if (!response.equals("false")) {

                        ArrayList<Report> reports;
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        reports = gson.fromJson(response, new TypeToken<ArrayList<Report>>() {
                        }.getType());

                        reportAdapter.clear();

                        Log.e("List of Students", reports.toString());
                        reportAdapter.addAll(reports);
                        reportAdapter.notifyDataSetChanged();
                    } else {
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Failed to display your reports", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }catch (Exception e) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }.execute(idsString); //TODO use SQLite info
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("", "onResume()");
        displayAllReports();
    }
}
