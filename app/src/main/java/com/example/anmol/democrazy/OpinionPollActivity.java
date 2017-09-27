package com.example.anmol.democrazy;

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

public class OpinionPollActivity extends AppCompatActivity {
    private static final int PAGES = 20;
    private static final String TAG = OpinionPollActivity.class.getSimpleName();
    private String URL = "http://139.59.86.83:4000/login/secure/opinionPolls/getNew?count=";
    private ArrayList<OpinionPoll> listPolls;
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


    public void getPolls() {
        progressBar.setVisibility(View.VISIBLE);
        listPolls = new ArrayList<>();
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
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String question = object.getString("question");
                                    String stateCentralId = object.getString("state_central_id");
                                    String startDate = object.getString("date_start");
                                    String endDate = object.getString("date_end");

                                    // add fragments to fragmentList
                                    if (i == jsonArray.length() - 1)
                                        fragmentList.add(OpinionPollFragment.newInstance(
                                                new OpinionPoll(id, question, stateCentralId, startDate, endDate, true)));
                                    else
                                        fragmentList.add(OpinionPollFragment.newInstance(
                                                new OpinionPoll(id, question, stateCentralId, startDate, endDate, false)));
                                }
                            }
                            Adapter adapter = new Adapter(getSupportFragmentManager());
                            adapter.addFragment(fragmentList);
                            pager.setAdapter(adapter);
                            pager.setOffscreenPageLimit(0);
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
                        NetworkResponse response = error.networkResponse;
                        String json = new String(response.data);
                        Log.e(TAG, "onErrorResponse: " + json);
                        Toast.makeText(OpinionPollActivity.this, " Error ", Toast.LENGTH_SHORT).show();
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
