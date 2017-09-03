package com.example.anmol.democrazy.login;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anmol on 3/9/17.
 */

public class SendUserDetails {

    List<String> list;
    RequestQueue rq;
    Context ctx;

    private static final String URL="http://139.59.86.83:4000/secure/firstLogin/submitDetails";

    public SendUserDetails(List<String> list,Context ctx){
        this.list=list;
        this.ctx=ctx;
    }

    public void sendDetails(){

        rq= Volley.newRequestQueue(ctx);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<String,String>();
                map.put("fullName",list.get(0));
                map.put("dob",list.get(1));
                map.put("gender",list.get(2));
                map.put("email",list.get(3));
                map.put("pinCode",list.get(4));
                map.put("phone",list.get(5));
                return  map;
            }
        };

        rq.add(stringRequest);

    }


}
