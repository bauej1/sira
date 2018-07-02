package com.sira.sira;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is used to bypass the struggle with the AsyncTask in Android Studio regarding the ServerConnector Class.
 * The ServerConnector Class loads the important information like Token and Context to this Class and saves it as a member variable.
 * Furthermore this Class is used to load the final SIRIS Formular (xml) into the SIRIS Server at the University of Bern.
 */

public class ServerHelpService {

    public static String uploadUrl = "https://memdocdemo.memdoc.org/memdocRestServer/rest/demo/imports?";
    public static String token = null;
    public static boolean saveInc = true;
    public static boolean autoSubmit = false;
    public static RequestQueue requestQueue;
    public static Context context;
    private static Patient patient;

    /**
     * Reassign the requestQueue from Volley
     */
    public static boolean initRequestQueue(){
        if(context == null){
            return false;
        }
        requestQueue = Volley.newRequestQueue(context);
        return true;
    }

    /**
     * Set Token and Context
     * @param responseToken - token from ServerConnector
     * @param responseContext - context from ServerConnector
     */
    public static void setTokenAndContext(String responseToken, Context responseContext){
        token = responseToken;
        context = responseContext;
    }

    /**
     * Concat the uploadUrl with important parameters like saveinc and autosubmit.
     * Loads the .xml file to the SIRIS server.
     */
    public static void loadIntoServer(Patient p, final VolleyCallback callback){
        uploadUrl = uploadUrl + "token="+token+"&saveinc="+saveInc+"&autosubmit="+autoSubmit;
        patient = p;

        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response from Server", "response: " + response);
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Server Error", "error: " + error.toString());
                    }
                })  {
            @Override
            public String getBodyContentType() {
                return "application/xml";
            }

            @Override
            public byte[] getBody() {
                File file = getXmlToUpload();
                byte[] bytesArray = new byte[(int) file.length()];
                try {
                    FileInputStream fis = new FileInputStream(file);
                    fis.read(bytesArray);
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bytesArray;
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Get's the file from the filesystem in the Android Tablet.
     *
     * @return File - the .xml File
     */
    private static File getXmlToUpload(){
        File file = null;
        int patientId = patient.getPatId();

        Log.d("patientId: ", patientId + "");

        switch(patientId){
            case 10006:
                file = new File(Environment.getExternalStorageDirectory() + "/Documents/siris_1.xml");
                break;
            case 10007:
                file = new File(Environment.getExternalStorageDirectory() + "/Documents/siris_2.xml");
                break;
            case 10008:
                file = new File(Environment.getExternalStorageDirectory() + "/Documents/siris_3.xml");
                break;
            case 10009:
                file = new File(Environment.getExternalStorageDirectory() + "/Documents/siris_4.xml");
                break;
            case 10010:
                file = new File(Environment.getExternalStorageDirectory() + "/Documents/siris_5.xml");
                break;
        }
        return file;
    }
}
