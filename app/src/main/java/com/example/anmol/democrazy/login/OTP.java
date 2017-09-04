package com.example.anmol.democrazy.login;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class OTP {

    Context ctx;
    RequestQueue rq;
    String phoneNumber;
    String OTPNum;
    private static final String URL="http://139.59.86.83:4000/login/loginNow";

    public OTP(String phoneNumber,String OTPNum,Context ctx){
        this.phoneNumber=phoneNumber;
        this.OTPNum=OTPNum;
        this.ctx=ctx;
    }


    public void sendOTP(final OTPCallback otpCallback){

        rq= Volley.newRequestQueue(ctx);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        try {


                            JSONObject jsonObject=new JSONObject(response);

                            boolean status=jsonObject.getBoolean("status");
                            String msg=jsonObject.getString("msg");

                            otpCallback.getStatus(status,msg);

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

                Map<String,String> map=new HashMap<>();
                map.put("phone",phoneNumber);
                map.put("otp",OTPNum);
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {


                System.out.println(response.headers);

                //.replace(" Path=/; HttpOnly","")

                String key = response.headers.get("Set-Cookie");
                System.out.println(key);
                loginKey loginKey=new loginKey(ctx,key);
                loginKey.setLoginKey();

                System.out.println("Key : "+loginKey.getLoginKey());
                return super.parseNetworkResponse(response);
            }
        };


        rq.add(stringRequest);

    }


    public interface OTPCallback{
        public void getStatus(boolean status,String msg);
    }


}
