package com.example.mengfanlin.h2oreserve.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.db.DBManager;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ModifyReportActivity extends AppCompatActivity {

    public Report chosenReport;
    public Report modifiedReport;

    private Spinner spinnerCampus, spinnerBuilding, spinnerLevel;
    private Button buttonSave, buttonDelete;
    private EditText editTextDescription,editTextRoom;
    private TextView textViewReportDate;
    private int reportId;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //back function
//        ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Modify Your Report");
        //Spinners
        spinnerCampus = (Spinner) findViewById(R.id.spinner_modify_campus);
        spinnerBuilding = (Spinner) findViewById(R.id.spinner_modify_building);
        spinnerLevel = (Spinner) findViewById(R.id.spinner_modify_level);

        //TextView
        textViewReportDate = (TextView) findViewById(R.id.textView_modify_report_date);
        //EditText
        editTextDescription = (EditText) findViewById(R.id.editText_modify_description);
        editTextRoom = (EditText) findViewById(R.id.editText_modify_room);
        //Button
        buttonSave = (Button) findViewById(R.id.button_save_report);
        buttonDelete = (Button) findViewById(R.id.button_delete_report);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Save the modification?");
                //.setTitle(R.string.dialog_title);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        attemptSave();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder2.setMessage("Delete the report?");
                //.setTitle(R.string.dialog_title);
                // Add the buttons
                builder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        deleteReport();
                    }
                });
                builder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
            }
        });

        chosenReport = new Report();
        //receive info by intent
        Bundle bundle = getIntent().getExtras();
        //friendship = (Friendship) bundle.getSerializable("Friendship");
        chosenReport = (Report) bundle.getSerializable("chosenReport");
        Log.e("Chosen Report", chosenReport.toString());
        reportId = chosenReport.getId();
        Log.e("chosen ReportId", String.valueOf(reportId));

        // Set values to campus spinner
        List<String> CampusList = new ArrayList<>();
        //CampusList.add("Select a campus...");
        CampusList.add("Caulfield");

        ArrayAdapter<String> campusSpinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,CampusList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        campusSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerCampus.setAdapter(campusSpinnerArrayAdapter);

        // Set default spinner to level spinner
        List<String> defaultLevelList = new ArrayList<>();
        defaultLevelList.add("Select a level...");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, defaultLevelList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                return view;

            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerArrayAdapter.notifyDataSetChanged();
        spinnerLevel.setAdapter(spinnerArrayAdapter);

        // Set values to building spinner and connect it with next spinner
        String[] buildings = new String[] {
                "Select a building...",
                "Building A (Library)",
                "Building B",
                "Building C",
                "Building D",
                "Building E",
                "Building F",
                "Building G",
                "Building H",
                "Building J",
                "Building K",
                "Building N",
                "Building S",
                "Building T"
        };
        List<String> buildingsList = new ArrayList<>(Arrays.asList(buildings));

        ArrayAdapter<String> buildingSpinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,buildingsList){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        buildingSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerBuilding.setAdapter(buildingSpinnerArrayAdapter);

        displayReportInformation();

        // Add listener to building spinner
        spinnerBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    List<String> list = new ArrayList<>();

                    list.clear(); //reset list
                    list.add("Select a level...");
                    list.add("Basement");
                    // Library
                    if (position == 1) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                    }
                    // Building B
                    if (position == 2) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                        list.add("Level 5");
                        list.add("Level 6");
                        list.add("Level 7");
                    }
                    // Building C
                    if (position == 3) {
                        list.add("Level 1");
                        list.add("Level 2");
                    }
                    // Building D
                    if (position == 4) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                    }
                    // Building E
                    if (position == 5) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                    }
                    // Building F
                    if (position == 6) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                    }
                    // Building G
                    if (position == 7) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                    }
                    // Building H
                    if (position == 8) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                        list.add("Level 5");
                        list.add("Level 6");
                        list.add("Level 7");
                        list.add("Level 8");
                        list.add("Level 9");
                        list.add("Level 10");
                    }
                    // Building J
                    if (position == 9) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                        list.add("Level 5");
                        list.add("Level 6");
                    }
                    // Building K
                    if (position == 10) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                    }
                    // Building N
                    if (position == 11) {
                        list.add("Level 1");
                        list.add("Level 2");
                    }
                    // Building S
                    if (position == 12) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                    }
                    // Building T
                    if (position == 13) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                    }
                    // set list to spinner
                    setLevelSpinner(list);
                    String level = chosenReport.getLevel();
                    if (!level.equals("Basement"))
                        level = "Level " + level;
                    Log.e("Level full name is ", level);
                    setValueToSpinner(level,spinnerLevel);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    /**
     * Set level spinner
     * @param list the level spinner list of certain building
     */
    private void setLevelSpinner(List<String> list) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerArrayAdapter.notifyDataSetChanged();
        spinnerLevel.setAdapter(spinnerArrayAdapter);
    }

    private void displayReportInformation() {

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

        setValueToSpinner(chosenReport.getCampus(),spinnerCampus);
        String building = "Building " + chosenReport.getBuilding();
        if (building.equals("Building A"))
            building = building + " (Library)";
        Log.e("Building full name is ", building);
        // Set values to building spinner and level spinner
        setValueToSpinner(building,spinnerBuilding);
        editTextRoom.setText(chosenReport.getRoom());
        editTextDescription.setText(chosenReport.getDescription());
        String date = sf.format(chosenReport.getDate());
        textViewReportDate.setText("Date added: " + date);
    }

    private void setValueToSpinner(String value, Spinner spinner) {
        ArrayAdapter arrayAdapter = (ArrayAdapter) spinner.getAdapter();
        int spinnerPosition = arrayAdapter.getPosition(value);
        spinner.setSelection(spinnerPosition);
    }

    /**
     * Delete report
     */
    private void deleteReport() {

        new AsyncTask<Integer, Void, String>(){

            @Override
            protected String doInBackground(Integer... params) {
                String message = RestClient.deleteReport(params[0]);
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("This report has been deleted!")) {
                    try {
                        dbManager = new DBManager(getApplicationContext());
                        dbManager.open();
                        dbManager.deleteReportId(reportId);
                        dbManager.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        }.execute(reportId);
    }

    /**
     * Update report
     */
    private void attemptSave() {

        String campus = spinnerCampus.getSelectedItem().toString();
        String building = spinnerBuilding.getSelectedItem().toString().substring(9,10);
        String level = spinnerLevel.getSelectedItem().toString();
        if (!level.equals("Basement"))
            level = level.substring(6,7);
        String room = editTextRoom.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        // Reset errors
        ((TextView) spinnerCampus.getSelectedView()).setError(null);
        ((TextView) spinnerBuilding.getSelectedView()).setError(null);
        ((TextView) spinnerLevel.getSelectedView()).setError(null);

        editTextRoom.setError(null);
        editTextDescription.setError(null);
        buttonDelete.setError(null);
        buttonSave.setError(null);

        //Validation
        boolean cancel = false;
        View focusView = null;

//        // Check for a description
//        if (TextUtils.isEmpty(description)) {
//            editTextDescription.setError("This field is required!");
//            focusView = editTextDescription;
//            cancel = true;
//        }

        if (TextUtils.isEmpty(room)) {
            editTextRoom.setError("This field is required!");
            focusView = editTextRoom;
            cancel = true;
        }

        // Check for a spinner
        if (spinnerCampus.getSelectedItem().toString().trim().equals("")) {
            ((TextView)spinnerCampus.getSelectedView()).setError("This field is required!");
            focusView = spinnerCampus;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerBuilding.getSelectedItem().toString().trim().equals("Select a building...")) {
            ((TextView)spinnerBuilding.getSelectedView()).setError("This field is required!");
            focusView = spinnerBuilding;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerLevel.getSelectedItem().toString().trim().equals("Select a level...")) {
            ((TextView)spinnerLevel.getSelectedView()).setError("This field is required!");
            focusView = spinnerLevel;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt submitting report and focus the first form field with an error
            focusView.requestFocus();
        } else {

            final Report editedReport = new Report(reportId, campus, building, level, room, description);
            //TODO Validation for modifying the report that has been marked finished.
            //Log.e("modifiedReport", modifiedReport.toString());

            new AsyncTask<Report, Void, String>(){

                @Override
                protected String doInBackground(Report...params) {
                    //Log.e("BeforeTransferModifiedReport", modifiedReport.toString());
                    Log.e("BeforeTransferEditedReport", editedReport.toString());
                    String message = RestClient.updateReport(editedReport,reportId);
                    return message;
                }

                @Override
                protected void onPostExecute(String message) {
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }.execute(modifiedReport);
        }
    }
}
