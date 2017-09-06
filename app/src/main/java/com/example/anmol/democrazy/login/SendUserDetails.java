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


public class SendUserDetails {

    private static final String URL = "http://139.59.86.83:4000/login/secure/firstLogin/submitDetails";
    private List<String> list;
    private Context ctx;

    public SendUserDetails(List<String> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    public void sendDetails(final UserCallBack userCallBack) {

        RequestQueue rq = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            userCallBack.getResult(status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("fullName", list.get(0));
                map.put("dob", list.get(1));
                map.put("gender", list.get(2));
                map.put("email", list.get(3));
                map.put("pinCode", list.get(4));
                map.put("phone", list.get(5));
                return map;
            }
//
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//
//                LoginKey LoginKey=new LoginKey(ctx);
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                System.out.println("Key : "+LoginKey.getLoginKey());
//                params.put("Cookie",LoginKey.getLoginKey());
//
//                return (Response<String>) params;
//
//            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                LoginKey loginKey = new LoginKey(ctx);
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                System.out.println("Key : " + loginKey.getLoginKey());
                params.put("Cookie", loginKey.getLoginKey());
                return params;
            }


        };

        rq.add(stringRequest);

    }

    public interface UserCallBack {
        void getResult(boolean status);
    }
}
