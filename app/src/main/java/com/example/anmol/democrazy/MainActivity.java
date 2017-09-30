package com.example.anmol.democrazy;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anmol.democrazy.BillsData.UserStates;
import com.example.anmol.democrazy.PhoneNoSave.SavePhoneNo;
import com.example.anmol.democrazy.adapters.RecyclerAdapterMain;
import com.example.anmol.democrazy.login.LogOut;
import com.example.anmol.democrazy.login.LoginKey;
import com.example.anmol.democrazy.login.getUserDetails;
import com.example.anmol.democrazy.navigation.AboutUs;
import com.example.anmol.democrazy.util.TextDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout drawer;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Menu menu;
    TextView email_id, full_name;
    ImageView imageView;
    private int itemToResize;
    private int TotalItemsInView = 0;

    Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If this activity is called by OpinionPoll Activity
        if (getIntent() != null && getIntent().hasExtra("POLLS")) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Polls Available?")
                    .setContentText("We will upload more polls as soon as possible")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
        // CheckPermission
        Permission();
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


        // Header Implementations
        email_id = (TextView) navHeader.findViewById(R.id.email_id);
        full_name = (TextView) navHeader.findViewById(R.id.full_name);
        imageView = (ImageView) navHeader.findViewById(R.id.image);

        email_id.setVisibility(View.GONE);
        full_name.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        ////////////// GETTING USER DETAILS  ///////////////////
        LoginKey l = new LoginKey(MainActivity.this);
        String key = l.getLoginKey();
        if (key != "") {


            email_id.setVisibility(View.VISIBLE);
            full_name.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            menu.findItem(R.id.LoginActivity).setTitle("LogOut");
            //getting details
            getUserDetails g = new getUserDetails(MainActivity.this);

            g.getDetails(new getUserDetails.getDetailsOfUser() {
                @Override
                public void result(boolean status, JSONObject jsonObject) throws JSONException {

                    //status -true := getting details Successfully
                    if (status) {
                        String email = jsonObject.getJSONObject("msg").getString("email");
                        String fullName = jsonObject.getJSONObject("msg").getString("fullName");
                        //getting JsonArray of states
                        JSONArray jsonArray = jsonObject.getJSONObject("msg").getJSONArray("BOStates");

                        //setting jsonArray in pref file
                        UserStates userStates = new UserStates(MainActivity.this, jsonArray);
                        userStates.setUserState();

                        //Setting text in login text


                        String color = getRandomMaterialColor("400");
                        Log.e(TAG, "result: " + color);
                        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                                .textColor(Color.WHITE)
                                .useFont(Typeface.DEFAULT)
                                .toUpperCase()
                                .endConfig()
                                .buildRound(fullName.substring(0, 1), Color.parseColor(color));
                        email_id.setText(email);
                        full_name.setText(fullName);
                        imageView.setImageDrawable(myDrawable);

                        //saving Phone No
                        SavePhoneNo savePhoneNo = new SavePhoneNo(MainActivity.this, jsonObject.getJSONObject("msg").getString("phone"));
                        savePhoneNo.setPhoneNo();
                    }
                }
            });
        }

        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new RecyclerAdapterMain(MainActivity.this);
        // rv.addItemDecoration(new OverlappingDecoration());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    private void Permission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) + ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECEIVE_SMS) + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.RECEIVE_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(findViewById(android.R.id.content),
                        "Please Grant Permissions to read OTP from messages",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        }).show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            // Should we show an explanation?
        }
    }

    private void setToolbar() {
        if (toolbar != null)
            //setSupportActionBar(toolbar);
            toolbar.setTitle("");
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


            // Closing Application
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

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
        if (id == R.id.LoginActivity) {
            LoginKey l = new LoginKey(MainActivity.this);

            // If login key found
            if (!(l.getLoginKey().equals(""))) {

                //calling ConfirmLogout Method
                confirmLogOut(item);
            } else {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        }

        // Share App Click
        else if (id == R.id.ShareApp) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Democrazy App link on Google Play Store");
            startActivity(Intent.createChooser(share, "Share using"));
        } else if (id == R.id.AboutUs) {
            Intent i = new Intent(MainActivity.this, AboutUs.class);
            startActivity(i);

        }

        // FeedBack (Sending To Gmail)
        else if (id == R.id.FeedBack) {
            sendGmail();
        } else if (id == R.id.ContactUs) {
            Intent i = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    boolean read_sms = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean recieve_sms = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean storage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (!read_sms && !recieve_sms && !storage) {
                        Snackbar.make(findViewById(android.R.id.content),
                                "Please Grant Permissions to read OTP from messages",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(MainActivity.this,
                                                new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                    }
                                }).show();
                    }
                }
                break;
            }
        }
    }

    public String getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("colors_" + typeColor, "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return String.format("#%06X", (0xFFFFFF & returnColor));
    }


    // Sending Gmail
    public void sendGmail() {

//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String TO[] = {"bansal.pawan96@gmail.com"};
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack(DemoCrazy)");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please Give Us Feedback");

//        try {
//            startActivity(emailIntent);
//        } catch (android.content.ActivityNotFoundException ex) {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        emailIntent.setType("text/plain");
        final PackageManager pm = getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        startActivity(emailIntent);

    }


    // Confirming LogOut
    public void confirmLogOut(final MenuItem item) {

        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Sure Want To Logout ? ")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                // Confirm
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        LogOut logOut = new LogOut(MainActivity.this);
                        logOut.logoutUser(new LogOut.LogOutInter() {
                            @Override
                            public void result(boolean status) {
                                System.out.println("status : " + status);
                                if (status) {
                                    // changing UI
                                    item.setTitle("login");


                                    email_id.setVisibility(View.GONE);
                                    full_name.setVisibility(View.GONE);
                                    imageView.setVisibility(View.GONE);
                                    // Deleting Cookie
                                    LoginKey l = new LoginKey(MainActivity.this, "");
                                    l.setLoginKey();
                                }
                            }
                        });

                        sDialog.dismissWithAnimation();
                    }
                })
                .show();

    }

}