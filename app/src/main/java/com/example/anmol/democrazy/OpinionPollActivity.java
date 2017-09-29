package com.example.anmol.democrazy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anmol.democrazy.PhoneNoSave.SavePhoneNo;
import com.example.anmol.democrazy.fragments.OpinionPollFragment;
import com.example.anmol.democrazy.login.LoginKey;
import com.example.anmol.democrazy.opinion.OpinionPoll;
import com.example.anmol.democrazy.viewpagers.VerticalPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OpinionPollActivity extends AppCompatActivity implements OpinionPollFragment.OnSubmitListener, OpinionPollFragment.OnButtonListener {
    private static final int PAGES = 20;
    private static final String TAG = OpinionPollActivity.class.getSimpleName();
    private String URL = "http://139.59.86.83:4000/login/secure/opinionPolls/getNew?count=";
    ProgressBar progressBar;
    VerticalPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_opinion_poll);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pager = (VerticalPager) findViewById(R.id.view_pager);

        // Full Screen

        // Used to populate polls;
        getPolls();

        LoginKey loginKey = new LoginKey(OpinionPollActivity.this);
        Log.e(TAG, "onCreate: " + loginKey.getLoginKey());

    }


    /**
     * Used to fetch polls
     */
    public void getPolls() {
        // show progressBar
        progressBar.setVisibility(View.VISIBLE);
        URL += "5";
        Log.e(TAG, "getPolls: " + URL);
        RequestQueue rq = Volley.newRequestQueue(OpinionPollActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        List<Fragment> fragmentList = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString(getString(R.string.status));
                            if (msg.equals("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("msg");

                                // If length of array is 0 then finish the activity
                                if (jsonArray.length() == 0) {

                                    Intent intent = new Intent(OpinionPollActivity.this, MainActivity.class);
                                    intent.putExtra("POLLS", true);
                                    startActivity(intent);
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String question = object.getString("question");
                                    String stateCentralId = object.getString("state_central_id");
                                    String startDate = object.getString("date_start");
                                    String endDate = object.getString("date_end");

                                    // if it is last page then add Submit button at the end
                                    if (i == jsonArray.length() - 1)
                                        fragmentList.add(OpinionPollFragment.newInstance(
                                                new OpinionPoll(id, question, stateCentralId, startDate, endDate, true), i));
                                        // else just show the same fragment with yes,no and don't know message
                                    else
                                        fragmentList.add(OpinionPollFragment.newInstance(
                                                new OpinionPoll(id, question, stateCentralId, startDate, endDate, false), i));
                                }
                            }
                            Adapter adapter = new Adapter(getSupportFragmentManager());
                            adapter.addFragment(fragmentList);
                            pager.setAdapter(adapter);
                            pager.setOffscreenPageLimit(0);
                            // Alter the behaviour of progressBar
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);

                        // Used to get error response using NetworkResponse
                        NetworkResponse response = error.networkResponse;
                        String json = new String(response.data);
                        Log.e(TAG, "onErrorResponse: " + json);
                        Toast.makeText(OpinionPollActivity.this, " Error ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) {

            // Change here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                LoginKey loginKey = new LoginKey(OpinionPollActivity.this);
                String key = loginKey.getLoginKey();
                Log.e(TAG, "getParams: " + key);
                map.put("Cookie", key);
                return map;
            }
        };

        rq.add(stringRequest);
    }

    @Override
    public void onSubmitButtonListener(Map<String, Integer> object) {
        JSONObject jsonObject = new JSONObject(object);
        submitPolls(jsonObject.toString());
    }

    @Override
    public void onAnyButtonListener(int pos) {
        pager.setScrollDurationFactor(10);
        pager.setCurrentItem(pos + 1, true);
        pager.setScrollDurationFactor(2);
    }


    void submitPolls(final String voteObject) {
        String SUBMIT_URL = "http://139.59.86.83:4000/login/secure/opinionPolls/submitPoll";
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SUBMIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean(getString(R.string.status));
                            if (status) {
                                new SweetAlertDialog(OpinionPollActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Polls has been submitted")
                                        .setContentText("Do you want to fill more polls")
                                        .setConfirmText("Yes")
                                        .setCancelText("No")
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                                finish();
                                            }
                                        })
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                Intent intent = getIntent();
                                                finish();
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                            String msg = jsonObject.getString(getString(R.string.msg));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OpinionPollActivity.this, " Error ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                SavePhoneNo savePhoneNo = new SavePhoneNo(OpinionPollActivity.this);
                String phoneNumber = savePhoneNo.getPhoneNo();
                Log.e(TAG, "phone: " + phoneNumber + "  votes : " + voteObject);
                map.put("phone", phoneNumber);
                map.put("votes", voteObject);
                return new JSONObject(map).toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                LoginKey loginKey = new LoginKey(OpinionPollActivity.this);
                String key = loginKey.getLoginKey();
                Log.e(TAG, "Post getParams: " + key);
                map.put("Cookie", key);
                map.put("Content-Type", "application/json");
                return map;
            }
        };

        rq.add(stringRequest);
    }


    private static class Adapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(List<Fragment> fragmentList) {
            mFragments = fragmentList;
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        // It is the main reason why state remains same because it is destroying fragments...We tell it not to destroy them
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}
