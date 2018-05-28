package com.sira.sira;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is a static class to connect the SIRA App to the SIRIS memdoc WebServer.
 * It is also used to handle all the REST_API interactions between SIRA and memdoc.
 * The class is used to load the final XML formatted patient data into the database.
 */
public class ServerConnector extends AsyncTask<Context, Void, Long>{

    public static final String authUrl = "https://memdocdemo.memdoc.org/memdocRestServer/rest/demo/auth";
    public static final String username = "bfh";
    public static final String password = "N_g18u3z";

    public String token = null;

    /**
     * Connects the SIRA App to the memdoc Server and checks if the connection is valid.
     * As a response the authentification token is returned by the Server
     *
     * @param context - the App Context
     * @throws JSONException
     */
    public void loginToServer(Context context) throws JSONException {

        JSONObject credentials = getCredentials();
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                authUrl,
                credentials,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            token = response.getString("token");
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
}
