package com.sira.sira;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jan on 08/05/2018.
 */


/**
 * This class is a static class to connect the SIRA App to the SIRIS memdoc WebServer.
 * It is also used to handle all the REST_API interactions between SIRA and memdoc.
 * The class is used to load the final XML formatted patient data into the database.
 */
public final class ServerConnector extends AsyncTask<Void, Void, Long>{

    public static final String url = "https://memdocdemo.memdoc.org/";
    public static final String authUrl = "https://memdocdemo.memdoc.org/memdocRestServer/rest/demo/auth";
    public static final String username = "bfh";
    public static final String password = "N_g18u3z";

    /**
     * Connects the SIRA App to the memdoc Server and checks if the connection is valid.
     *
     * @throws IOException
     */
    public static void connectToServer(Context context) throws IOException {
        URL sirisURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) sirisURL.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        if(conn.getResponseCode() == 200){
            Log.d("connection status: ", "guuuut");
        } else {
            Log.d("connection status: ", conn.getResponseCode() + "");
            Log.d("connection status: ", "schlecht");
        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE:", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonRequest);
    }

    /**
     * Loads the xml formatted patient data from the SIRA App into the memdoc WebServer.
     *
     * @param patientXML - the formatted patient data
     */
    public void loadToSIRIS(Xml patientXML){

    }

    public void getPatientFromSIRIS(int id){

    }

    @Override
    protected Long doInBackground(Void... voids) {
        return null;
    }
}
