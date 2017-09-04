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
import java.util.Map;


public class getUserDetails {

    Context ctx;
    RequestQueue rq;

    private static final String URL="http://139.59.86.83:4000/login/secure/user/getDetails";

    public getUserDetails(Context ctx){
        this.ctx=ctx;
    }

    public void getDetails(final getDetailsofUser  getDetailsofUser){

        rq= Volley.newRequestQueue(ctx);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                             JSONObject jsonObject1=jsonObject.getJSONObject("msg");
                             String email=jsonObject1.getString("email");

                            System.out.println("Email : "+email);

                            getDetailsofUser.result(email);

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

                loginKey l=new loginKey(ctx);
                HashMap<String,String> map=new HashMap<>();
                map.put("Cookie",l.getLoginKey());
                return map;

            }
        };


        rq.add(stringRequest);

    }

    public interface getDetailsofUser{
        public void result(String response);
    }


}
