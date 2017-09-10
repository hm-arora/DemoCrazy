package com.example.anmol.democrazy.BillsData;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class getAllBills {

    RequestQueue rq;
    Context ctx;
    int type;
    int offset;

    private static final String URL = "";

    // Constructor
    public getAllBills(Context ctx, int type){
        this.ctx = ctx;
        this.type = type;
        offset=0;
    }

    // with offset
    public getAllBills(Context ctx,int type,int offset){
        this.ctx=ctx;
        this.type=type;
        this.offset=offset;
    }

    public void getData(){

        rq= Volley.newRequestQueue(ctx);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                             boolean status=jsonObject.getBoolean("status");
                             if (status){
                                 JSONArray jsonArray=jsonObject.getJSONArray("msg");
                                 for (int i=0;i<jsonArray.length();i++){
                                     JSONObject object= (JSONObject) jsonArray.get(i);

                                 }
                             }
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

                Map<String,String> map = new HashMap<>();
                map.put("type",String.valueOf(type));
                map.put("offset",String.valueOf(offset));
                map.put("count","10");
                map.put("SCId","[1]");
                return super.getParams();
            }
        };

        rq.add(stringRequest);
    }

    public interface BillsCallBack{
        void getAll(List<String> li);
    }

}
