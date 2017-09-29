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
import com.example.anmol.democrazy.PhoneNoSave.SavePhoneNo;
import com.example.anmol.democrazy.login.LoginKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sendPoll {


    RequestQueue rq;
    Context ctx;
    int id;
    int vote;

    private static final String URL = "http://139.59.86.83:4000/login/secure/billsOrdinances/submitPoll";

    // Constructor : - id of bill
    public sendPoll(Context ctx, int id, int vote) {
        this.ctx = ctx;
        this.id = id;
        this.vote = vote;
    }

    // Sending Vote

    public void sendingVote(final callBack callBack){

        rq=Volley.newRequestQueue(ctx);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            callBack.getResult(jsonObject);

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
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap=new HashMap<>();
                //Getting Phone Number
                SavePhoneNo savePhoneNo=new SavePhoneNo(ctx);
                //Getting Boid
                String Boid=String.valueOf(id);
                //Getting vote
                String voti=String.valueOf(vote);
                hashMap.put("phone",savePhoneNo.getPhoneNo());
                hashMap.put("BOId",Boid);
                hashMap.put("vote",voti);
                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                LoginKey loginKey=new LoginKey(ctx);
                map.put("Cookie",loginKey.getLoginKey());
                return map;
            }
        };
        rq.add(stringRequest);
    }

    public interface callBack{
        void getResult(JSONObject jsonObject) throws JSONException;
    }

}