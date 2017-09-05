package com.example.mengfanlin.h2oreserve.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengfanlin.h2oreserve.R;
import com.example.mengfanlin.h2oreserve.activities.MainActivity;
import com.example.mengfanlin.h2oreserve.db.DBManager;
import com.example.mengfanlin.h2oreserve.entities.Report;
import com.example.mengfanlin.h2oreserve.services.RestClient;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mengfanlin on 15/08/2017.
 */

public class SupplyReportFragment extends Fragment implements View.OnClickListener{

    private static final int CAMERA_REQUEST = 123;
    private static final int RESULT_OK = -1;
    private static final int RESULT_CAMERA = 0;
    private static final String TAG = MainActivity.class.getSimpleName();
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
    private String timestamp;
    private View viewMain;
    private Uri file;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewMain = inflater.inflate(R.layout.fragment_supply_report, container, false);
        getActivity().setTitle("Submit Report");
        //Spinners
        spinnerCampus = (Spinner) viewMain.findViewById(R.id.spinner_campus);
        spinnerBuilding = (Spinner) viewMain.findViewById(R.id.spinner_building);
        spinnerLevel = (Spinner) viewMain.findViewById(R.id.spinner_level);


        imageViewTakenPhoto = ((ImageView)this.viewMain.findViewById(R.id.imageView_taken_photo));

        //EditText
        editTextDescription = (EditText) viewMain.findViewById(R.id.editText_description);
        editTextRoom = (EditText) viewMain.findViewById(R.id.editText_room);
        buttonTakePhoto = ((Button)this.viewMain.findViewById(R.id.button_take_photo));
        //CheckBox
        //checkBox = (CheckBox) viewMain.findViewById(R.id.checkBox);
        //Button
        buttonSubmit = (Button) viewMain.findViewById(R.id.button_submit);
        //Set listener on button submit
        buttonSubmit.setOnClickListener(this);
        buttonTakePhoto.setOnClickListener(this);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = FileProvider.getUriForFile(getActivity(), "me.inspiringbits", getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                startActivityForResult(intent, RESULT_CAMERA);
            }
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
//        if (i == R.id.button_take_photo){
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, CAMERA_REQUEST);
//        }
        if (i == R.id.button_submit) {
            attemptSubmit();
        }
        if (i == R.id.button_take_photo) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = FileProvider.getUriForFile(getActivity(), "me.inspiringbits", getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

            startActivityForResult(intent, RESULT_CAMERA);
        }

    }

    private File getOutputMediaFile(){

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri cameraImg = file;
//            Uri image = data.getData();
        Log.e("URI", cameraImg.getPath());
        imageViewTakenPhoto.setImageURI(cameraImg);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null){
            //set the selected image to image variable


//            //get the current timeStamp and strore that in the time Variable
//            Long tsLong = System.currentTimeMillis() / 1000;
//            timestamp = tsLong.toString();
//            Toast.makeText(getActivity().getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptSubmit() {

        String campus = spinnerCampus.getSelectedItem().toString();
        String building = spinnerBuilding.getSelectedItem().toString();
        String level = spinnerLevel.getSelectedItem().toString();
        String room = editTextRoom.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
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
        if (TextUtils.isEmpty(description)) {
            editTextDescription.setError("This field is required!");
            focusView = editTextDescription;
            cancel = true;
        }
        if (TextUtils.isEmpty(description)) {
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
            }

                report = new Report(campus, building, level, room, description, null);
                new AsyncTask<Report, Void, String>() {
                    @Override
                    protected String doInBackground(Report... params) {
                        String message = RestClient.addReport(params[0]);
                        return message;
                    }

                    @Override
                    protected void onPostExecute(String message) {

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
                                FragmentManager fragmentManager = getActivity().getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.content_frame, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        Toast.makeText(SupplyReportFragment.this.getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }.execute(report);
        }
    }
}
