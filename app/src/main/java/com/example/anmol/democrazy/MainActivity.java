package com.example.anmol.democrazy;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.anmol.democrazy.adapters.RecyclerAdapterMain;
import com.example.anmol.democrazy.login.LogOut;
import com.example.anmol.democrazy.login.getUserDetails;
import com.example.anmol.democrazy.login.loginKey;
import com.example.anmol.democrazy.navigation.AboutUs;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    FrameLayout frameLayout;

    private DrawerLayout drawer;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Menu menu;

    private int itemToResize;
    private int TotalItemsInView = 0;

    Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Used to initialize views
        initViews();

        // Set Toolbar
        setToolbar();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(MainActivity.this);


        //Menu of Navigation View
        menu = navigationView.getMenu();

        // Header of Navigation View
        View navHeader = navigationView.getHeaderView(0);

        frameLayout= (FrameLayout) navHeader.findViewById(R.id.FrameNav);
        final TextView login_text= (TextView) navHeader.findViewById(R.id.Login_text);

        ////////////// GETTING USER DETAILS  ///////////////////
        loginKey l=new loginKey(MainActivity.this);
        String key=l.getLoginKey();
        if (key!=""){

            menu.findItem(R.id.LoginActivity).setTitle("LogOut");

            frameLayout.setVisibility(View.VISIBLE);

            getUserDetails g=new getUserDetails(MainActivity.this);

            g.getDetails(new getUserDetails.getDetailsofUser() {
                @Override
                public void result(String response) {

                    login_text.setText(response);

                }
            });

        }



        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new RecyclerAdapterMain(MainActivity.this);
        // rv.addItemDecoration(new OverlappingDecoration());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);


    }

    private void setToolbar() {
        if (toolbar != null)
            setSupportActionBar(toolbar);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        rv = (RecyclerView) findViewById(R.id.RecyclerView);

    }

    @Override
    public void onBackPressed() {
        // Used to close drawer if active on back button
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Login Click
        if (id==R.id.LoginActivity){

            loginKey l=new loginKey(MainActivity.this);
            if (l.getLoginKey()!=""){

                LogOut logOut=new LogOut(MainActivity.this);

                logOut.logoutUser(new LogOut.LogOutInter() {
                    @Override
                    public void result(boolean status) {

                        System.out.println("status : "+status);

                        if (status){

                            // changing UI
                            item.setTitle("login");
                            frameLayout.setVisibility(View.GONE);

                            // Deleting Cookie
                            loginKey l=new loginKey(MainActivity.this,"");
                            l.setLoginKey();

                        }
                    }
                });

            }
            else{
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        }

        // Share App Click
        else if (id==R.id.ShareApp){
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Democrazy App link on Google Play Store");
            startActivity(Intent.createChooser(share, "Share using"));
        }

        else if(id==R.id.AboutUs){

            Intent i=new Intent(MainActivity.this, AboutUs.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}