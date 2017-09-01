package com.example.anmol.democrazy.login;


// Sending Phone Number

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumber {

    Context ctx;
    RequestQueue rq;
    String phoneNumber;

    private static final String URL="";

    public PhoneNumber(String phoneNumber,Context ctx){

        this.phoneNumber=phoneNumber;
        this.ctx=ctx;

    }

    public void sendNumber(){

        rq=Volley.newRequestQueue(ctx);

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
                map.put("phone",phoneNumber);
                return  map;
            }
        };

        rq.add(stringRequest);

    }

//
//    interface VolleyCallback{
//        void getResult();
//    }

}
