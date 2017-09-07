package com.example.anmol.democrazy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.anmol.democrazy.fragments.OpinionPollFragment;
import com.example.anmol.democrazy.login.LoginKey;
import com.example.anmol.democrazy.viewpagers.VerticalPager;

import java.util.ArrayList;
import java.util.List;

public class OpinionPollActivity extends AppCompatActivity {
    private static final int PAGES = 20;
    private static final String TAG = OpinionPollActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full Screen
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
            fragmentList.add(OpinionPollFragment.newInstance("Question : " + (i + 1)));
        }
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(fragmentList);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(0);

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
    }
}
