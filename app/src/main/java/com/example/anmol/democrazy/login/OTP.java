package com.example.anmol.democrazy.login;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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


public class OTP {

    private static final String TAG = OTP.class.getSimpleName();
    Context ctx;
    private String phoneNumber;
    private String OTPNum;

    // Login Url
    private static final String URL = "http://139.59.86.83:4000/login/loginNow";

    public OTP(String phoneNumber, String OTPNum, Context ctx) {
        this.phoneNumber = phoneNumber;
        this.OTPNum = OTPNum;
        this.ctx = ctx;
    }


    public void sendOTP(final OTPCallback otpCallback) {

        RequestQueue rq = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            String msg = jsonObject.getString("msg");
                            otpCallback.getStatus(status, msg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: Volley Server error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                System.out.println("Phone No. : " + phoneNumber);
                System.out.println("otp : " + OTPNum);
                map.put("phone", phoneNumber);
                map.put("otp", OTPNum);
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                System.out.println(response.headers);
                // To get cookie from headers
                String key = response.headers.get("Set-Cookie");
                Log.e(TAG, "parseNetworkResponse: " + key);
                LoginKey loginKey = new LoginKey(ctx, key);
                loginKey.setLoginKey();

                System.out.println("Key : " + loginKey.getLoginKey());
                return super.parseNetworkResponse(response);
            }
        };


        rq.add(stringRequest);

    }


    public interface OTPCallback {
        void getStatus(boolean status, String msg);
    }

}
