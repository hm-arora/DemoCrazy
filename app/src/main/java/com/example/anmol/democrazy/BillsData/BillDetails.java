package com.example.anmol.democrazy.BillsData;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anmol.democrazy.login.LoginKey;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class BillDetails {

    RequestQueue rq;
    Context ctx;
    int id;

    private static final String URL = "http://139.59.86.83:4000/login/unSecure/billsOrdinances/detail?BOId=";

    // Constructor : - id of bill
    public BillDetails(Context ctx, int id){
        this.ctx=ctx;
        this.id=id;
    }

    //getting Particular Bill Details
    public void getBillD(final BillsCallBack billsCallBack){

        rq= Volley.newRequestQueue(ctx);

        JsonObjectRequest jsonObjectRequest = null;

        // Checking if Login Key is present or not
        final LoginKey loginKey=new LoginKey(ctx);

        //if not present
        if (loginKey.getLoginKey()==""){
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, (URL + id), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                billsCallBack.getBillDetails(response);
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
        }
        // if present
        else{
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, (URL + id), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                billsCallBack.getBillDetails(response);
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
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> map=new HashMap<>();
                    map.put("Cookie",loginKey.getLoginKey());
                    return map;
                }
            };
        }

        rq.add(jsonObjectRequest);
    }

    public interface BillsCallBack{
        void getBillDetails(JSONObject jsonObject) throws JSONException;
    }



}
