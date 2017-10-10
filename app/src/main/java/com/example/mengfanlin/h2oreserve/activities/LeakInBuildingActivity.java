package com.example.mengfanlin.h2oreserve.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.adapters.AdapterReport;
import com.example.mengfanlin.h2oreserve.db.DBManager;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class LeakInBuildingActivity
        extends AppCompatActivity
{
    private DBManager dbManager;
    private FindReportsInBuilding findReportsInBuilding;
    private ListView listView;
    private AdapterReport reportAdapter;
    private ArrayList<Report> reportArrayList;

    private void loadData(String paramString) {}

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_in_building);
        this.listView = ((ListView)findViewById(R.id.listView_leak_in_building));

        String building = getIntent().getExtras().getString("building");

        //building = "Building " + building;
        if (building.equals("Ground Area")) {
            setTitle("All Leaks on " + building);
        } else {
            setTitle("All Leaks in Building " + building);
        }

        reportArrayList = new ArrayList<>();

        // Create the adapter to convert the array to views
        reportAdapter = new AdapterReport(this, reportArrayList);
        // Attach the adapter to a ListView
        listView.setAdapter(reportAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CheckReportDetailActivity.class);
                //intent.putExtra("Student", studentArrayList.get(position));
                intent.putExtra("chosenReport",reportArrayList.get(position));
                intent.putExtra("classFrom", "LeakInBuildingActivity");
                startActivity(intent);
            }
        });

        View view = this.getCurrentFocus();
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        findReportsInBuilding = new FindReportsInBuilding(building);
        findReportsInBuilding.execute();
    }

    protected class FindReportsInBuilding extends AsyncTask<String, Void, String> {

        private String building;

        public FindReportsInBuilding(String building) {
            this.building = building;
        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("Async building", building);
            String message =  RestClient.getReportsInBuilding(building);
            return message;
        }

        @Override
        protected void onPostExecute(String message) {
            if (!message.startsWith("F")){
                try {
                    Log.e("message on Post Execute", message);
                    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                    ArrayList<Report> reports = gson.fromJson(message, new TypeToken<ArrayList<Report>>() {
                    }.getType());
                    reportAdapter.clear();
                    Log.e("List of Students", reports.toString());
                    Collections.reverse(reports);
                    reportAdapter.addAll(reports);
                    reportAdapter.notifyDataSetChanged();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                }
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            }
        }
    }
}
