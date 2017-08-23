package com.example.mengfanlin.h2oreserve.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.activities.MainActivity;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;

import java.util.Calendar;

/**
 * Created by mengfanlin on 15/08/2017.
 */

public class SupplyReportFragment extends Fragment implements View.OnClickListener{

    private View viewMain;
    private Spinner spinnerCampus, spinnerBuilding, spinnerLevel, spinnerRoom;
    private Button buttonSubmit;
    private CheckBox checkBox;
    private EditText editTextDescription;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_supply_report, container, false);
        getActivity().setTitle("Submit Report");
        //Spinners
        spinnerCampus = (Spinner) viewMain.findViewById(R.id.spinner_campus);
        spinnerBuilding = (Spinner) viewMain.findViewById(R.id.spinner_building);
        spinnerLevel = (Spinner) viewMain.findViewById(R.id.spinner_level);
        spinnerRoom = (Spinner) viewMain.findViewById(R.id.spinner_room);

        //EditText
        editTextDescription = (EditText) viewMain.findViewById(R.id.editText_description);
        //CheckBox
        checkBox = (CheckBox) viewMain.findViewById(R.id.checkBox);
        //Button
        buttonSubmit = (Button) viewMain.findViewById(R.id.button_submit);
        //Set listener on button submit
        buttonSubmit.setOnClickListener(this);
        return viewMain;
    }

    @Override
    public void onClick(View v) {
        attemptSubmit();

    }

    private void attemptSubmit() {

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
        buttonSubmit.setError(null);

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
            Report report = new Report();
            report.setCampus(campus);
            report.setBuilding(building);
            report.setLevel(level);
            report.setRoom(room);
            report.setDescription(description);
            report.setDate(Calendar.getInstance().getTime());
            report.setUser("mfl333124@gmail.com");
            Log.e("CreatedReport", report.toString());

            new AsyncTask<Report, Void, String>(){

                @Override
                protected String doInBackground(Report...params) {
                    String message = RestClient.addReport(params[0]);
                    return message;
                }

                @Override
                protected void onPostExecute(String message) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.show();
                    Fragment fragment = new CheckReportFragment();
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
//                    finish();
//                    if (!message.equals("Failed to get reports")) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        LayoutInflater inflater = getActivity().getLayoutInflater();
//                        builder.setView(inflater.inflate(R.layout.dialog_report, null));
//                    }

                }
            }.execute(report);

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
