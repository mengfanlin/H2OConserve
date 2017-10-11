package com.example.mengfanlin.h2oreserve.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.entities.Report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Check report details in My Reports page.
 */
public class CheckReportDetailActivity extends AppCompatActivity {

    public Report chosenReport;
    private TextView tvCampus, tvBuilding, tvLevel, tvRoom, tvDescription, tvDateAdded, tvStatus;
    private int reportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report_detail);
        //textViews
        tvCampus = (TextView) findViewById(R.id.textView_check_campus);
        tvBuilding = (TextView) findViewById(R.id.textView_check_building);
        tvLevel = (TextView) findViewById(R.id.textView_check_level);
        tvRoom = (TextView) findViewById(R.id.textView_check_room);
        tvDescription = (TextView) findViewById(R.id.textView_check_description);
        tvDateAdded = (TextView) findViewById(R.id.textView_check_date);
        tvStatus = (TextView) findViewById(R.id.textView_check_status);
        //get the chosen report
        chosenReport = new Report();
        //receive info by intent
        Bundle bundle = getIntent().getExtras();
        //friendship = (Friendship) bundle.getSerializable("Friendship");
        chosenReport = (Report) bundle.getSerializable("chosenReport");
        Log.e("Chosen Report", chosenReport.toString());
        Log.e("Chosen Report", chosenReport.getCampus());
        Log.e("Chosen Report", chosenReport.getBuilding());
        Log.e("Chosen Report", chosenReport.getLevel());
        Log.e("Chosen Report", chosenReport.getRoom());
        Log.e("Chosen Report", chosenReport.getDescription());
        Log.e("Chosen Report", chosenReport.getStatus());
        displayData();
    }

    /**
     * Display the detailed info of a report
     */
    private void displayData() {
        tvCampus.setText(chosenReport.getCampus());
        tvBuilding.setText(chosenReport.getBuilding());
        tvLevel.setText(chosenReport.getLevel());
        tvRoom.setText(chosenReport.getRoom());
        tvDescription.setText(chosenReport.getDescription());
        Date date = chosenReport.getDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateAdded = df.format(date);
        tvDateAdded.setText(dateAdded);
        String status = chosenReport.getStatus();
        tvStatus.setText(status);

        if (status.toLowerCase().startsWith("p"))
            tvStatus.setTextColor(new Color().parseColor("#E53935"));
        if (status.toLowerCase().startsWith("v"))
            tvStatus.setTextColor(new Color().parseColor("#FFD600"));
        if (status.toLowerCase().startsWith("c"))
            tvStatus.setTextColor(new Color().parseColor("#43A047"));
    }
}
