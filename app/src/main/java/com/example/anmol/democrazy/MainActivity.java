package com.example.anmol.democrazy;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.anmol.democrazy.adapters.RecyclerAdapterMain;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Menu menu;

    private int itemToResize;
    private int TotalItemsInView = 0;

    Toolbar toolbar;
    private NavigationView navigationView;
=======
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.anmol.democrazy.Adapter.RecyclerAdapterMain;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Menu menu;

    private int itemToResize;
    private int TotalItemsInView=0;
>>>>>>> 2f5d428392ab004ae11cd1873ec68d9aff78f145

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        // Used to initialize views
        initViews();

        // Set Toolbar
        setToolbar();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        menu = navigationView.getMenu();

        View navHeader = navigationView.getHeaderView(0);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new RecyclerAdapterMain(MainActivity.this);
        // rv.addItemDecoration(new OverlappingDecoration());
=======

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.open_drawer,R.string.close_drawer);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        menu=navigationView.getMenu();

        View navHeader = navigationView.getHeaderView(0);


        /////////////Recycler View///////////////////
        rv= (RecyclerView) findViewById(R.id.RecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this);
        adapter=new RecyclerAdapterMain(MainActivity.this);
       // rv.addItemDecoration(new OverlappingDecoration());
>>>>>>> 2f5d428392ab004ae11cd1873ec68d9aff78f145
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);


    }

<<<<<<< HEAD
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
=======
    @Override
    public void onBackPressed() {
>>>>>>> 2f5d428392ab004ae11cd1873ec68d9aff78f145
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
<<<<<<< HEAD

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
=======
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       return true;
>>>>>>> 2f5d428392ab004ae11cd1873ec68d9aff78f145
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
