package com.example.anmol.democrazy.login;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class getUserDetails {

    private Context ctx;
    private RequestQueue rq;

    private static final String URL = "http://139.59.86.83:4000/login/secure/user/getDetails";

    public getUserDetails(Context ctx) {
        this.ctx = ctx;
    }


    public void getDetails(final getDetailsOfUser getDetailsOfUser) {

        rq = Volley.newRequestQueue(ctx);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            getDetailsOfUser.result(status, jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //getting Network Response
                        NetworkResponse response = error.networkResponse;

                        //checking if response data is not null
                        if (response != null && response.data != null) {

                            switch (response.statusCode) {

                                // Handling 403
                                case 403:
                                    String json = new String(response.data);
                                    try {
                                        Handling403(json, getDetailsOfUser);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;

                            }

                        }

                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LoginKey loginKey = new LoginKey(ctx);
                HashMap<String, String> map = new HashMap<>();
                map.put("Cookie", loginKey.getLoginKey());
                return map;
            }

        };
        rq.add(stringRequest);
    }


    //Handling 403
    public void Handling403(String Json, getDetailsOfUser getDetailsOfUser) throws JSONException {

        System.out.println(Json);
        JSONObject jsonObject = new JSONObject(new String(Json));
        boolean status = jsonObject.getBoolean("status");
        getDetailsOfUser.result(status, jsonObject);

    }

    public interface getDetailsOfUser {
        void result(boolean status, JSONObject jsonObject) throws JSONException;
    }

}
