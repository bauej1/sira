package com.sira.sira;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * This class is a static class to connect the SIRA App to the SIRIS memdoc WebServer.
 * It is also used to handle all the REST_API interactions between SIRA and memdoc.
 * The class is used to load the final XML formatted patient data into the database.
 */
public class ServerConnector extends AsyncTask<Context, Void, Long>{

    public static final String authUrl = "https://memdocdemo.memdoc.org/memdocRestServer/rest/demo/auth";
    public static String uploadUrl = "https://memdocdemo.memdoc.org/memdocRestServer/rest/demo/imports?";
    public static final String username = "bfh";
    public static final String password = "N_g18u3z";

    public String token = null;
    public boolean saveInc = true;
    public boolean autoSubmit = true;


    private RequestQueue requestQueue;

    /**
     * Connects the SIRA App to the memdoc Server and checks if the connection is valid.
     * As a response the authentification token is returned by the Server
     *
     * @param context - the App Context
     * @throws JSONException
     */
    public void loginToServer(final Context context) throws JSONException {

        JSONObject credentials = getCredentials();
        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                authUrl,
                credentials,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            token = response.getString("token");
                            Log.d("token:", "token: " + token.toString());
                            ServerHelpService.setTokenAndContext(token, context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Server Error", error.toString());
                    }
                });

        requestQueue.add(jsonRequest);
    }

    /**
     * Gets username and password for the SIRIS Server.
     *
     * @return cred - JSON Object
     * @throws JSONException
     */
    private JSONObject getCredentials() throws JSONException {
        JSONObject cred = new JSONObject();
        cred.put("username", username);
        cred.put("password", password);

        return cred;
    }

    @Override
    protected Long doInBackground(Context... contexts) {
        Context context = contexts[0];
        try {
            loginToServer(context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadXMLIntoServer(){
        uploadUrl = uploadUrl + "token="+token+"&saveinc="+saveInc+"&autosubmit="+autoSubmit;

        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response from Server", "response: " + response.toString());
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
                        String postData = getXmlToUpload().toString();
                        return postData.getBytes();
                    }
        };

        requestQueue.add(stringRequest);
    }

    private File getXmlToUpload(){
        File file = new File(Environment.getExternalStorageDirectory() + "/Documents/siris.xml");
        return file;
    }
}
