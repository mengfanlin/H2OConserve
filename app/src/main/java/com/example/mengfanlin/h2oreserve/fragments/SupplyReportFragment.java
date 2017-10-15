package com.example.mengfanlin.h2oreserve.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.activities.MainActivity;
import com.example.mengfanlin.h2oreserve.db.DBManager;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by mengfanlin on 15/08/2017.
 * Submit report fragment
 */

public class SupplyReportFragment extends Fragment implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 123;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CAMERA = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
    private View maskView;
    private ProgressBar progressBar;
    private Button buttonSubmit;
    private Button buttonTakePhoto;
    private DBManager dbManager;
    private EditText editTextDescription;
    private EditText editTextRoom;
    private ImageView imageViewTakenPhoto;
    private Report report;
    private Spinner spinnerBuilding;
    private Spinner spinnerCampus;
    private Spinner spinnerLevel;
    private View viewMain;
    private Uri file;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_supply_report, container, false);
        getActivity().setTitle("Report Water Leak");
        //Spinners
        spinnerCampus = (Spinner) viewMain.findViewById(R.id.spinner_campus);
        spinnerBuilding = (Spinner) viewMain.findViewById(R.id.spinner_building);
        spinnerLevel = (Spinner) viewMain.findViewById(R.id.spinner_level);
        maskView = (View) viewMain.findViewById(R.id.maskView);
        progressBar = (ProgressBar) viewMain.findViewById(R.id.progressBar);
        // Set values to campus spinner
        List<String> CampusList = new ArrayList<>();
        //CampusList.add("Select a campus...");
        CampusList.add("Caulfield");
        //Set campus spinner
        ArrayAdapter<String> campusSpinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_item, CampusList) {
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
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
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
                getActivity(), R.layout.spinner_item, buildingsList) {
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
        buildingSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerBuilding.setAdapter(buildingSpinnerArrayAdapter);
        // Add listener to building spinner
        spinnerBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    if (position == 13) {
                        list.add("Ground");
                    }
                    // set list to spinner
                    setLevelSpinner(list);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        imageViewTakenPhoto = ((ImageView) this.viewMain.findViewById(R.id.imageView_taken_photo));
        //EditText
        editTextDescription = (EditText) viewMain.findViewById(R.id.editText_description);
        editTextRoom = (EditText) viewMain.findViewById(R.id.editText_room);
        //Button
        buttonTakePhoto = ((Button) this.viewMain.findViewById(R.id.button_take_photo));
        buttonSubmit = (Button) viewMain.findViewById(R.id.button_submit);
        //Set listener on button submit
        buttonSubmit.setOnClickListener(this);
        buttonTakePhoto.setOnClickListener(this);

        //
        editTextDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        editTextRoom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
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

    /**
     * Set level spinner
     *
     * @param list item list in the spinner
     */
    private void setLevelSpinner(List<String> list) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, list) {
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
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerArrayAdapter.notifyDataSetChanged();
        spinnerLevel.setAdapter(spinnerArrayAdapter);
    }

    /**
     * Hide keyboard
     *
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = FileProvider.getUriForFile(getActivity(), "me.authorities", getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                startActivityForResult(intent, RESULT_CAMERA);
            }
        }
    }

    /**
     * OnClick method for the buttons
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        int i = v.getId();
        // Submit button listener
        if (i == R.id.button_submit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Submit the report?");
            //.setTitle(R.string.dialog_title);
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    attemptSubmit();
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
        // Take photo listener
        if (i == R.id.button_take_photo) {
            // Ask permissions
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
                return;
            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return;
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = FileProvider.getUriForFile(getActivity(), "me.authorities", getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
            startActivityForResult(intent, RESULT_CAMERA);
        }
    }

    /**
     * Get media file from mobile photo
     *
     * @return
     */
    private File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri cameraImg = file;
        Log.e("URI", cameraImg.getPath());
        imageViewTakenPhoto.setImageURI(cameraImg);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            //set the selected image to image variable
//            //get the current timeStamp and strore that in the time Variable
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp = tsLong.toString();
//            Toast.makeText(getActivity().getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Attempt to submit the report
     */
    private void attemptSubmit() {
        String campus;
        String building;
        String level;
        String room;
        String description;

        campus = spinnerCampus.getSelectedItem().toString();
        building = spinnerBuilding.getSelectedItem().toString();
        if (!building.equals("Ground Area"))
            building = building.substring(9, 10);

        level = spinnerLevel.getSelectedItem().toString();
        if (!level.equals("Basement") && !level.equals("Ground"))
            level = level.substring(6, 7);
        room = editTextRoom.getText().toString().trim();
        description = editTextDescription.getText().toString().trim();
        // Reset errors
        ((TextView) spinnerCampus.getSelectedView()).setError(null);
        ((TextView) spinnerBuilding.getSelectedView()).setError(null);
        ((TextView) spinnerLevel.getSelectedView()).setError(null);
        editTextRoom.setError(null);
        editTextDescription.setError(null);
        //checkBox.setError(null);
        buttonSubmit.setError(null);

        //Validation
        boolean cancel = false;
        View focusView = null;

        // Check for a description
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
            ((TextView) spinnerCampus.getSelectedView()).setError("This field is required!");
            focusView = spinnerCampus;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerBuilding.getSelectedItem().toString().trim().equals("Select a building...(*)")) {
            ((TextView) spinnerBuilding.getSelectedView()).setError("");
            focusView = spinnerBuilding;
            cancel = true;
        }
        // Check for a spinner
        if (spinnerLevel.getSelectedItem().toString().trim().equals("Select a level...(*)")) {
            ((TextView) spinnerLevel.getSelectedView()).setError("");
            focusView = spinnerLevel;
            cancel = true;
        }
        // If no errors
        if (cancel) {
            // There was an error; don't attempt submitting report and focus the first form field with an error
            focusView.requestFocus();
        } else {

            if ((imageViewTakenPhoto.getDrawable()) != null) {
                Bitmap bitmap = ((BitmapDrawable) imageViewTakenPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

                report = new Report(campus, building, level, room, description, Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
                Log.e("Report is constructed", this.report.toString());
            } else {
                report = new Report(campus, building, level, room, description, null);
            }

            maskView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            new AsyncTask<Report, Void, String>() {
                @Override
                protected String doInBackground(Report... params) {
                    String message = RestClient.addReport(params[0]);
                    return message;
                }

                @Override
                protected void onPostExecute(String message) {

                    maskView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    if (!message.startsWith("F")) {
                        try {
                            int i = Integer.parseInt(message);
                            Log.e("Integer is", i + "");
                            dbManager = new DBManager(getActivity());
                            dbManager.open();
                            dbManager.insertReportId(i);
                            Log.e("Now ids in SQLite are:", dbManager.getAllReportIds().toString());
                            dbManager.close();
                            Toast.makeText(SupplyReportFragment.this.getActivity().getApplicationContext(), "Report has been added!", Toast.LENGTH_LONG).show();
                            //change page to my reports.
                            Fragment fragment = new CheckReportFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        } finally {
                            dbManager.close();
                        }
                    }
                    Toast.makeText(SupplyReportFragment.this.getActivity().getApplicationContext(), "Failed to submit the report!", Toast.LENGTH_SHORT).show();
                }
            }.execute(report);
        }
    }
}
