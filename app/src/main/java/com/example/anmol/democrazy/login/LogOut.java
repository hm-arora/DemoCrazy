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

/**
 * Created by anmol on 4/9/17.
 */

public class LogOut {

    Context ctx;
    RequestQueue rq;

    private static final String URL="http://139.59.86.83:4000/login/secure/user/logout";

    public LogOut(Context ctx){

        this.ctx=ctx;

    }

    public void logoutUser(final LogOutInter logOutInter){

        rq= Volley.newRequestQueue(ctx);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        try {

                            JSONObject jsonObject=new JSONObject(response);

                            boolean status=jsonObject.getBoolean("status");

                            logOutInter.result(status);

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

    public interface LogOutInter{
        public void result(boolean status);
    }

}
