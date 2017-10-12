package com.example.mengfanlin.h2oreserve.services;

import android.util.Log;

import com.example.mengfanlin.h2oreserve.entities.Report;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *  Created by mengfanlin on 18/08/2017.
 *  RESTful services
 */

public class RestClient {

    //private static final String BASE_URI = "http://127.0.0.1:8080/H2OIteration1/webresources";
    //private static final String BASE_URI = "http://10.0.0.49:8080/H2OIteration1/webresources";
    //private static final String BASE_URI = "http://10.0.2.2:8080/H2OIteration1/webresources";
    //private static final String BASE_URI = "http://54.252.175.242:8080/H2OIteration1/webresources";
    //private static final String BASE_URI = "http://52.65.107.106/api/reports";
    //private static final String BASE_URI_2 = "http://52.65.107.106/api/building";
    //private static final String BASE_URI = "https://h2oconserve.tk/api/reports";
    //private static final String BASE_URI_2 = "https://h2oconserve.tk/api/building";
    private static final String BASE_URI = "https://h2oconserve.tk/api/reports";
    private static final String BASE_URI_2 = "https://h2oconserve.tk/api/building";

    /**
     * Add report rest service
     * @param report report created
     * @return
     */
    public static String addReport(Report report) {
        URL url;
        String textResult = "";
        HttpURLConnection conn = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Log.e("Report String",report.toString());
            String stringReportJson = gson.toJson(report);

//            BufferedWriter out2 = new BufferedWriter(new FileWriter("reportRecord.txt"));
//            out2.write(stringReportJson);  //Replace with the string
//            //you are trying to write
//            Log.e("has been written","");
//            out2.close();

            url = new URL(BASE_URI);
            Log.e("url",url.toString());
            Log.e("CreatedReport json",stringReportJson);
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
            conn.setRequestProperty("Accept", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringReportJson);
            out.close();
            conn.connect();
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.e("Response should be a number",textResult);
            Log.i("Received: ",new Integer(conn.getResponseCode()).toString());
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 204 )
                return textResult;
            else
                return "Failed by connection problem";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed due to exception";
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Count number of reports of each building
     * @return result
     */
    public static String countReports() {
        URL url;
        HttpURLConnection conn = null;
        String textResult = "";
        final String methodPath= "";

        try {
            url = new URL(BASE_URI_2 + methodPath);
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
            conn.connect(); // Last added
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.e("conn response", String.valueOf(conn.getResponseCode()));
            Log.e("textResult of count reports", textResult);
            conn.disconnect();
            if (conn.getResponseCode() != 200)
                return "Failed to get reports due to response code is not 200";
            else
                return textResult;

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to get reports due to exceptions";
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Get all my reports
     * @param ids String of my report ids
     * @return result
     */
    public static String getMyReports(String ids) {
        URL url;
        HttpURLConnection conn = null;
        String textResult = "";
        final String methodPath= "/" + ids;

        try {
            url = new URL(BASE_URI + methodPath);
            Log.e("path:",url.toString());
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(10000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect(); // Last added
            InputStream inputStream = null;
            String code = String.valueOf(conn.getResponseCode());
            if (code.startsWith("2")) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }
            //Read the response
            Scanner inStream = new Scanner(inputStream);
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.e("conn response", code);
            Log.e("textResult of get my reports", textResult);
            conn.disconnect();
            if (!code.startsWith("2"))
                return "Failed to get reports due to response code is not 200/204";
            else
                return textResult;

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to get reports due to exceptions";
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Get all the reports in each building
     * @param building building number
     * @return result
     */
    public static String getReportsInBuilding(String building) {
        URL url;
        HttpURLConnection conn = null;
        String textResult = "";
        final String methodPath= "/" + building;

        try {
            url = new URL(BASE_URI_2 + methodPath);
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
            conn.connect(); // Last added
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.e("conn response", String.valueOf(conn.getResponseCode()));
            Log.e("textResult of reports in a building", textResult);
            conn.disconnect();
            if (conn.getResponseCode() != 200)
                return "Failed to get reports due to response code is not 200";
            else
                return textResult;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to get reports due to exceptions";
        } finally {
            conn.disconnect();
        }
    }

    /**
     *  Delete report by id
     * @param reportId report id
     * @return result
     */
    public static String deleteReport(int reportId){
        //initialise
        URL url;
        HttpURLConnection conn = null;
        final String methodPath="/" + reportId;
        try {
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
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 204)
                return "This report has been deleted!";
            else
                return "Failed due to network problem";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed due to exception!";
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Update report
     * @param report report object
     * @param reportId report id
     * @return
     */
    public static String updateReport(Report report, int reportId){ //TODO
        //initialise
        URL url;
        HttpURLConnection conn = null;
        String textResult = "";
        final String methodPath="/" + reportId;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
            conn.setRequestMethod("PUT");
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
            conn.connect();
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.e("Response should include PK(update)",textResult);

            Log.e("Received code(update): ",new Integer(conn.getResponseCode()).toString());

            return "Report updated!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update!";

        } finally {
            conn.disconnect();
        }
    }
}
