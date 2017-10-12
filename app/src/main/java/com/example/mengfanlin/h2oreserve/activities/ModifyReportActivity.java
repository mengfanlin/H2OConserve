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
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;
    private View maskView;
    private Spinner spinnerCampus, spinnerBuilding, spinnerLevel;
    private Button buttonSave, buttonDelete;
    private EditText editTextDescription, editTextRoom;
    private TextView textViewReportDate;
    private int reportId;
    private String reportLevel;
    private DBManager dbManager;
    private boolean firstRunningFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstRunningFlag = true;
        // Set title
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

        maskView = (View) findViewById(R.id.maskView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Alert dialog
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
        // Alert dialog 2
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
        reportLevel = chosenReport.getLevel();
        Log.e("chosen ReportId", String.valueOf(reportId));

        // Set values to campus spinner
        List<String> CampusList = new ArrayList<>();
        //CampusList.add("Select a campus...");
        CampusList.add("Caulfield");
        ArrayAdapter<String> campusSpinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, CampusList) {
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
        defaultLevelList.add("Select a level...(*)");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, defaultLevelList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                return view;

            }
        };
        // Set spinner style
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerArrayAdapter.notifyDataSetChanged();
        spinnerLevel.setAdapter(spinnerArrayAdapter);

        // Set values to building spinner and connect it with next spinner
        String[] buildings = new String[]{
                "Select a building...(*)",
                "Building A (Library)",
                "Building B",
                "Building C",
                "Building D",
                "Building E",
                "Building F",
                "Building G",
                "Building H",
                "Building K",
                "Building N",
                "Building S",
                "Building T",
                "Ground Area"
        };
        List<String> buildingsList = new ArrayList<>(Arrays.asList(buildings));
        ArrayAdapter<String> buildingSpinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, buildingsList) {
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
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        // Set spinner style
        buildingSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerBuilding.setAdapter(buildingSpinnerArrayAdapter);
        displayReportInformation();

        // Add listener to building spinner
        spinnerBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Set values to the level spinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    List<String> list = new ArrayList<>();

                    list.clear(); //reset list
                    list.add("Select a level...(*)");
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
                        list.add("Level 3");
                        list.add("Level 4");
                        list.add("Level 5");
                        list.add("Level 6");
                        list.add("Level 7");
                        list.add("Level 8");
                    }
                    // Building D
                    if (position == 4) {
                        list.add("Level 1");
                        list.add("Level 2");
                    }
                    // Building E
                    if (position == 5) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                    }
                    // Building F
                    if (position == 6) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                        list.add("Level 5");
                        list.add("Level 6");
                    }
                    // Building G
                    if (position == 7) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                    }
                    // Building H
                    if (position == 8) {
                        list.add("Basement");
                        list.add("Level 4");
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
                    // Building K
                    if (position == 9) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                    }
                    // Building N
                    if (position == 10) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                        list.add("Level 4");
                        list.add("Level 5");
                        list.add("Level 6");
                        list.add("Level 7");
                    }
                    // Building S
                    if (position == 11) {
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
                        list.add("Level 11");
                    }
                    // Building T
                    if (position == 12) {
                        list.add("Level 1");
                        list.add("Level 2");
                        list.add("Level 3");
                    }
                    // Ground Area
                    if (position == 13) {
                        list.add("Ground");
                    }
                    // set list to spinner
                    setLevelSpinner(list);

                    //selectedBuilding = spinnerBuilding.getSelectedItem().toString();

                    Log.e("Level full name is ", reportLevel);
                    if (firstRunningFlag == true) {
                        if (!reportLevel.equals("Basement") && !reportLevel.equals("Ground") && !reportLevel.startsWith("L")) {
                            reportLevel = "Level " + reportLevel;
                        }
                        //if (getBuildingNameByPosition(position).equals(reportBuilding))
                        setValueToSpinner(reportLevel, spinnerLevel);
                        firstRunningFlag = false;
                    }
//                    if (!reportLevel.equals("Basement") && !reportLevel.equals("Ground"))
//                        reportLevel = "Level " + reportLevel;
//                    Log.e("Level full name is ", reportLevel);
//                    setValueToSpinner(reportLevel,spinnerLevel);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
//    private String getBuildingNameByPosition(int position) {
//        switch (position) {
//            case 1:
//                return "A";
//            case 2:
//                return "B";
//            case 3:
//                return "C";
//            case 4:
//                return "D";
//            case 5:
//                return "E";
//            case 6:
//                return "F";
//            case 7:
//                return "G";
//            case 8:
//                return "H";
//            case 9:
//                return "K";
//            case 10:
//                return "N";
//            case 11:
//                return "S";
//            case 12:
//                return "T";
//            case 13:
//                return "Ground Area";
//            default:
//                return "";
//
//        }
//  }

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

    /**
     * display report info when loading
     */
    private void displayReportInformation() {

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

        setValueToSpinner(chosenReport.getCampus(),spinnerCampus);
        String building = chosenReport.getBuilding();
        if (!building.startsWith("G")) {
            building = "Building " + chosenReport.getBuilding();
            if (building.equals("Building A"))
                building = building + " (Library)";
        }

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
        maskView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
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
                maskView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
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
        String building;
        building = spinnerBuilding.getSelectedItem().toString();
        if (!building.equals("Ground Area"))
            building = building.substring(9,10);

        String level = spinnerLevel.getSelectedItem().toString();
        if (!level.equals("Basement") && !level.equals("Ground"))
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
        if (spinnerBuilding.getSelectedItem().toString().trim().equals("Select a building...(*)")) {
            ((TextView)spinnerBuilding.getSelectedView()).setError("This field is required!");
            focusView = spinnerBuilding;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerLevel.getSelectedItem().toString().trim().equals("Select a level...(*)")) {
            ((TextView)spinnerLevel.getSelectedView()).setError("This field is required!");
            focusView = spinnerLevel;
            cancel = true;
        }
        // If no errors, continue
        if (cancel) {
            focusView.requestFocus();
        } else {
            final Report editedReport = new Report(reportId, campus, building, level, room, description);
            //TODO Validation for modifying the report that has been marked finished.
            //Log.e("modifiedReport", modifiedReport.toString());
            maskView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
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
                    maskView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }.execute(modifiedReport);
        }
    }
}
