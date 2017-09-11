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
    String scid;

    private static final String URL = "http://139.59.86.83:4000/login/unsecure/billsOrdinances/get";

    // Constructor : - Type
    public getAllBills(Context ctx, int type){
        this.ctx = ctx;
        this.type = type;
        offset=0;
        scid="[1]";
    }

    // with offset and type
    public getAllBills(Context ctx,int type,int offset){
        this.ctx=ctx;
        this.type=type;
        this.offset=offset;
        scid="[1]";
    }

    // with offset and type and scid
    public getAllBills(Context ctx,int type,int offset,String scid){
        this.ctx=ctx;
        this.type=type;
        this.offset=offset;
        this.scid=scid;
    }


    public void getData(final BillsCallBack billsCallBack){

        rq= Volley.newRequestQueue(ctx);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                             boolean status=jsonObject.getBoolean("status");
                             JSONArray jsonArray=jsonObject.getJSONArray("msg");

                            // sending status and msg array

                            billsCallBack.getAll(status,jsonArray);


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
                map.put("SCId",scid);
                return map;
            }
        };

        rq.add(stringRequest);
    }

    public interface BillsCallBack{
        void getAll(boolean status,JSONArray jsonArray) throws JSONException;
    }

}
