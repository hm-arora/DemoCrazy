package com.example.anmol.democrazy.BillsData;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getAllStates {

    RequestQueue rq;
    Context ctx;

    private static final String URL = "http://139.59.86.83:4000/login/unsecure/locations/getAllStates";

    // Constructor : - Type
    public getAllStates(Context ctx){
        this.ctx=ctx;
    }


    public void getData(){

        rq=Volley.newRequestQueue(ctx);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean("status");
                            System.out.println("Status : "+status);
                            //checking status -true
                            if (status){
                                JSONArray jsonArray=response.getJSONArray("msg");
                                for (int i=0;i<jsonArray.length();i++){
                                    int id=jsonArray.getJSONObject(i).getInt("id");
                                    String name=jsonArray.getJSONObject(i).getString("name");
                                    statesData statesData=new statesData(ctx,id,name);
                                    statesData.setState();
                                }
                            }
                            statesData statesData=new statesData(ctx);
                            System.out.println(statesData.getStates());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );


        rq.add(jsonObjectRequest);

    }
}

