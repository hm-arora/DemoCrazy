package com.example.anmol.democrazy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.anmol.democrazy.viewpagers.VerticalPager;

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
    ArrayList<String> QuestionID, Questions;
    private static String URL = "http://139.59.86.83:4000/login/secure/opinionPolls/getNew?count=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QuestionID = new ArrayList<>();
        Questions = new ArrayList<>();
        // Full Screen

        getPolls();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_opinion_poll);

        LoginKey loginKey = new LoginKey(OpinionPollActivity.this);
        Log.e(TAG, "onCreate: " + loginKey.getLoginKey());
        VerticalPager pager = (VerticalPager) findViewById(R.id.view_pager);
        List<Fragment> fragmentList = new ArrayList<>();


        for (int i = 0; i < PAGES; i++) {
            // Number of pages in a vertical Pager
//            fragmentList.add(OpinionPollFragment.newInstance(getString(R.string.question)));
            String ID = "1";
            fragmentList.add(OpinionPollFragment.newInstance("Question : " + (i + 1), ID));
        }
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(fragmentList);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(0);

    }


    public void getPolls() {

        URL += "5";
        Log.e(TAG, "getPolls: " + URL);
        RequestQueue rq = Volley.newRequestQueue(OpinionPollActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
