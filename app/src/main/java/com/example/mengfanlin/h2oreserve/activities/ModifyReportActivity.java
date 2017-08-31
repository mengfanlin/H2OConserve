package com.example.mengfanlin.h2oreserve.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModifyReportActivity extends AppCompatActivity {

    public Report chosenReport;
    public Report modifiedReport;

    private Spinner spinnerCampus, spinnerBuilding, spinnerLevel, spinnerRoom;
    private Button buttonSave, buttonDelete;
    private CheckBox checkBox;
    private EditText editTextDescription;
    private TextView textViewReportDate;
    private int reportId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Modify Your Report");
        //Spinners
        spinnerCampus = (Spinner) findViewById(R.id.spinner_modify_campus);
        spinnerBuilding = (Spinner) findViewById(R.id.spinner_modify_building);
        spinnerLevel = (Spinner) findViewById(R.id.spinner_modify_level);
        spinnerRoom = (Spinner) findViewById(R.id.spinner_modify_room);
        //TextView
        textViewReportDate = (TextView) findViewById(R.id.textView_report_date);
        //EditText
        editTextDescription = (EditText) findViewById(R.id.editText_modify_description);
        //CheckBox
        checkBox = (CheckBox) findViewById(R.id.checkBox_modify);
        //Button
        buttonSave = (Button) findViewById(R.id.button_save_report);
        buttonDelete = (Button) findViewById(R.id.button_delete_report);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReport();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReport();
            }
        });

        //receive info by intent
        Bundle bundle = getIntent().getExtras();
        //friendship = (Friendship) bundle.getSerializable("Friendship");
        chosenReport = (Report) bundle.getSerializable("chosenReport");
        reportId = chosenReport.getId();
        Log.e("ReportId", String.valueOf(reportId));
        displayFriendInformation();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void displayFriendInformation() {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        setValueToSpinner(chosenReport.getCampus(),spinnerCampus);
        setValueToSpinner(chosenReport.getBuilding(),spinnerBuilding);
        setValueToSpinner(chosenReport.getLevel(),spinnerLevel);
        setValueToSpinner(chosenReport.getRoom(),spinnerRoom);
        editTextDescription.setText(chosenReport.getDescription());
        String date = sf.format(chosenReport.getDate());
        textViewReportDate.setText("Last Modification Date: " + date);

    }

    private void setValueToSpinner(String value, Spinner spinner) {
        ArrayAdapter arrayAdapter = (ArrayAdapter) spinner.getAdapter();
        int spinnerPosition = arrayAdapter.getPosition(value);
        spinner.setSelection(spinnerPosition);
    }

    private void saveReport() {
        attemptSave();
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
                    //TODO delete in SQLite
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
        String building = spinnerBuilding.getSelectedItem().toString();
        String level = spinnerLevel.getSelectedItem().toString();
        String room = spinnerRoom.getSelectedItem().toString();
        String description = editTextDescription.getText().toString().trim();

        // Reset errors
        ((TextView) spinnerCampus.getSelectedView()).setError(null);
        ((TextView) spinnerBuilding.getSelectedView()).setError(null);
        ((TextView) spinnerLevel.getSelectedView()).setError(null);
        ((TextView) spinnerRoom.getSelectedView()).setError(null);
        checkBox.setError(null);
        buttonDelete.setError(null);
        buttonSave.setError(null);

        //Validation
        boolean cancel = false;
        View focusView = null;

        // Check for a description
        if (TextUtils.isEmpty(description)) {
            editTextDescription.setError("This field is required!");
            focusView = editTextDescription;
            cancel = true;
        }

        // Check for a spinner
        if (spinnerCampus.getSelectedItem().toString().trim().equals("")) {
            ((TextView)spinnerCampus.getSelectedView()).setError("This field is required!");
            focusView = spinnerCampus;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerBuilding.getSelectedItem().toString().trim().equals("")) {
            ((TextView)spinnerBuilding.getSelectedView()).setError("This field is required!");
            focusView = spinnerBuilding;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerLevel.getSelectedItem().toString().trim().equals("")) {
            ((TextView)spinnerLevel.getSelectedView()).setError("This field is required!");
            focusView = spinnerLevel;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerRoom.getSelectedItem().toString().trim().equals("")) {
            ((TextView)spinnerRoom.getSelectedView()).setError("This field is required!");
            focusView = spinnerRoom;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt submitting report and focus the first form field with an error
            focusView.requestFocus();
        } else {
//            modifiedReport = new Report();
//            modifiedReport.setCampus(campus);
//            modifiedReport.setBuilding(building);
//            modifiedReport.setLevel(level);
//            modifiedReport.setRoom(room);
//            modifiedReport.setDescription(description);
////            modifiedReport.setDate(Calendar.getInstance().getTime());
////            modifiedReport.setUser("mfl333124@gmail.com");
//            modifiedReport.setId(reportId);

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
//                    if (!message.equals("Failed to get reports")) {
//
//                    }

                }
            }.execute(modifiedReport);

//            String newReport[] = new String[5];
//            newReport[0] = campus;
//            newReport[1] = building;
//            newReport[2] = level;
//            newReport[3] = room;
//            newReport[4] = description;
//
//            SharedPreferences spLoggedInStudent = getActivity().getSharedPreferences("loggedInStudent", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = spLoggedInStudent.edit();
//            //editor
//            Toast.makeText(getActivity(),"Button clicked",Toast.LENGTH_SHORT);
        }
    }
}
