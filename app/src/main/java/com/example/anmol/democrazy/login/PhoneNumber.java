package com.example.anmol.democrazy.login;


// Sending Phone Number

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anmol.democrazy.R;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumber {

    private Context ctx;
    private String phoneNumber;

    private static final String URL = "http://139.59.86.83:4000/login/otp/sendNew";

    public PhoneNumber(String phoneNumber, Context ctx) {
        this.phoneNumber = phoneNumber;
        this.ctx = ctx;
    }

    public void sendNumber(final phNoCallback callback) {
        RequestQueue rq = Volley.newRequestQueue(ctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean(ctx.getString(R.string.status));
                            String msg = jsonObject.getString(ctx.getString(R.string.msg));
                            callback.getResult(status, msg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, " Error ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phoneNumber);
                return map;
            }
        };

        rq.add(stringRequest);

    }


    public interface phNoCallback {
        void getResult(boolean status, String msg);
    }

}
