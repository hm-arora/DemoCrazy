package com.example.anmol.democrazy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anmol.democrazy.BillsData.TemporaryUserStates;
import com.example.anmol.democrazy.BillsData.UserStates;
import com.example.anmol.democrazy.BillsData.getAllStates;
import com.example.anmol.democrazy.BillsData.statesData;
import com.example.anmol.democrazy.fragments.BillsLaid;
import com.example.anmol.democrazy.fragments.BillsPassed;
import com.example.anmol.democrazy.fragments.OrdinancesElacted;
import com.example.anmol.democrazy.fragments.OrdinancesEllapsed;
import com.example.anmol.democrazy.login.LoginKey;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    static BillActivity billActivity;

    String US;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bills_main);

        // Setting static bill Activity instance
        billActivity=this;

        // Initiate views
        initViews();
        // Set Toolbar
        setToolbar();


        System.out.println("ON CREATE _________________________ Called");
        //gettingUserStates
        gettingUserStates();
        // Set ViewPager
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Bills Data");
        } else
            throw new NullPointerException("Toolbar not initialized ");
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarBills);
        viewPager = (ViewPager) findViewById(R.id.viewpagerBills);
        tabLayout = (TabLayout) findViewById(R.id.tabsBills);

    }


    // getting User States
    private void gettingUserStates(){

        //Firstly I am Checking Login Key is prsesnt or not
        LoginKey loginKey=new LoginKey(BillActivity.this);

        if (loginKey.getLoginKey()!=""){
            //Getting User States as there is login key that implies there must be user states
            UserStates userStates=new UserStates(BillActivity.this);
            US=userStates.getUserStates();
        }

        // If No Login Key is present then show them centeral Bills
        //Showing Temporary User States
        else{
            TemporaryUserStates temporaryUserStates=new TemporaryUserStates(BillActivity.this);
            US=temporaryUserStates.getTempUserStates();
        }
    }


    /**
     * used to set up ViewPager
     * @param viewPager ViewPagers's object
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new BillsLaid(BillActivity.this,US), "Bills Laid");
        adapter.addFragment(new BillsPassed(BillActivity.this,US), "Bills Passed");
        adapter.addFragment(new OrdinancesElacted(BillActivity.this,US), "Ordinances Enacted");
        adapter.addFragment(new OrdinancesEllapsed(BillActivity.this,US), "Ordinances Lapsed");

        viewPager.setAdapter(adapter);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.location, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Handling back button
            case android.R.id.home:
                onBackPressed();
                return true;
            // Handling click location
            case R.id.AllStates:

                statesData st=new statesData(BillActivity.this);
                String s=st.getStates();
                if (s.equals("")){
                    //getting all states from server
                    getAllStates getAllStates=new getAllStates(BillActivity.this);
                    getAllStates.getData();
                    Intent i=new Intent(BillActivity.this,StateSet.class);
                    startActivity(i);
                }
                else{
                    Intent i=new Intent(BillActivity.this,StateSet.class);
                    startActivity(i);
                }

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    // Getting Instance of BillActivity
    public static BillActivity getInstance(){
        return  billActivity ;
    }

}
