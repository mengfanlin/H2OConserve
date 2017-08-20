package com.example.mengfanlin.h2oreserve.services;

import android.util.Log;

import com.example.mengfanlin.h2oreserve.entities.Report;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mengfanlin on 18/08/2017.
 */

public class RestClient {

    //private static final String BASE_URI = "http://127.0.0.1:8080/H2OIteration1/webresources";
    private static final String BASE_URI = "http://10.0.0.49:8080/H2OIteration1/webresources";
    //private static final String BASE_URI = "http://10.0.2.2:8080/H2OIteration1/webresources";
    //private static final String BASE_URI = "http://54.252.175.242:4848/H2OIteration1/webresources";

    public static String addReport(Report report) {

        URL url;
        HttpURLConnection conn = null;
        final String methodPath="/entities.report/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX").create();
            String stringReportJson = gson.toJson(report);
            url = new URL(BASE_URI + methodPath);
            Log.i("url",url.toString());
            Log.i("string",stringReportJson);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringReportJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringReportJson);
            out.close();
            Log.i("Received: ",new Integer(conn.getResponseCode()).toString());
            return "Report has been added";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to ";
        } finally {
            conn.disconnect();
        }
    }

    public static String getReports(String userId) {
        URL url;
        HttpURLConnection conn = null;
        String textResult = "";
        final String methodPath="/entities.report/findallreports/" + userId;

        try {
            url = new URL(BASE_URI + methodPath);
            Log.e("path:",url.toString());
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.e("conn response", String.valueOf(conn.getResponseCode()));
            Log.e("textResult", textResult);
            if (conn.getResponseCode() != 200)
                return "Failed to get reports";
            else
                return textResult;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to get reports";
        } finally {
            conn.disconnect();
        }
    }

    public static String deleteReport(int reportId){
        //initialise
        URL url;
        HttpURLConnection conn = null;
        final String methodPath="/entities.report/" + reportId;
        try {
//          Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX").create();
//          String stringCourseJson = gson.toJson(report);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to PUT
            conn.setRequestMethod("DELETE");
            //set the output to true
            //conn.setDoOutput(true);
            //set length of the data you want to send
            //conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            //conn.setRequestProperty("Content-Type", "application/json");
            //Send the PUT out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            //out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
            return "This report has been deleted!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed!";
        } finally {
            conn.disconnect();
        }
    }

    public static String updateReport(Report report, int reportId){
        //initialise
        URL url;
        HttpURLConnection conn = null;
        final String methodPath="/entities.report/" + reportId;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX").create();
            String stringReport = gson.toJson(report);
            Log.e("StringUpdatedReport(REST)", stringReport);
            url = new URL(BASE_URI + methodPath);
            Log.e("URL of update is", url.toString());
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to PUT
            conn.setRequestMethod("PUT");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringReport.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            Log.e("conn response", String.valueOf(conn.getResponseCode()));
            //Send the PUT out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringReport);
            out.close();
            return "Updated successful!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed!";

        } finally {
            conn.disconnect();
        }
    }
}
