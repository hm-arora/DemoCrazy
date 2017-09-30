package com.example.anmol.democrazy;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anmol.democrazy.login.OTP;
import com.example.anmol.democrazy.login.getUserDetails;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class OTPVerification extends AppCompatActivity {

    private static final String TAG = OTPVerification.class.getSimpleName();
    EditText OtpEditText;
    String phoneNumber;

    Button btn;

    private TextView PhoneNumberCon;
    // Phone Number Otp Verification content
    String content;

    Button loginBtn;

    // Used to detect SMS
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otp_verification_activity);

        phoneNumber = getIntent().getExtras().getString(getString(R.string.Phone_number));

        OtpEditText = (EditText) findViewById(R.id.OtpEditText);
        OtpEditText.setText("");
        PhoneNumberCon = (TextView) findViewById(R.id.OtpPassWordContent);


        content = "One Time Password(OTP) has been sent to your mobile ******" + getIntent().getExtras().getString(getString(R.string.Phone_number)).substring(6, 10) + " ,please enter the same here to login";

        PhoneNumberCon.setText(content);

        if (phoneNumber.equals("1234567899"))
            OtpEditText.setText("123456");

        btn = (Button) findViewById(R.id.Login_OTP);

        broadcastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent) {


                String senderName = intent.getExtras().getString("phoneNumber");

                String message = intent.getExtras().getString("message");

                //Printing message
                System.out.println(message);

                // Don't change otherwise it will not get right otp
                String otp = message.replace("Hi, your otp for democrazy is: ", "").substring(0, 6);
                Log.e(TAG, "onReceive: " + otp);

                //Printing otp
                OtpEditText.setText(otp);
                System.out.println(otp);

                System.out.println("Phone Number : " + phoneNumber);


                sendOTP(otp);


                sendMessage(otp);

            }
        };


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendButt(OtpEditText.getText().toString());
            }
        });

    }

    //Clicking Butt
    public void sendButt(String otp) {

        if (otp.equals("")) {

            SuperActivityToast.create(OTPVerification.this).setText("Please Put the OTP").setDuration(2000)
                    .setColor(Color.MAGENTA).setFrame(Style.FRAME_LOLLIPOP)
                    .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_DEEP_ORANGE))
                    .setAnimations(Style.ANIMATIONS_POP).show();


        } else {
            sendOTP(otp);
            sendMessage(otp);
        }


    }


    //sendOTP
    public void sendOTP(String otp) {

        OTP otp1 = new OTP(phoneNumber, otp, OTPVerification.this);
        otp1.sendOTP(new OTP.OTPCallback() {
            @Override
            public void getStatus(boolean status, String msg) {

                //checking status - true
                if (status) {
                    // msg - old user
                    if (msg.equals("old user")) {
                        getDetails();
                    }

                    // msg - new user
                    if (msg.equals("request other details")) {
                        Intent i = new Intent(OTPVerification.this, UserDetails.class);
                        i.putExtra("PhoneNumber", phoneNumber);
                        finish();
                        startActivity(i);
                    }
                }
            }
        });
    }


    // checking Details
    public void getDetails() {
        // getUserDetails instance
        getUserDetails getUser = new getUserDetails(OTPVerification.this);

        getUser.getDetails(new getUserDetails.getDetailsOfUser() {
            @Override
            public void result(boolean status, JSONObject jsonObject) throws JSONException {


                /*status - false
                   sending user to submit there details
                 */
                if (!status) {
                    String msg = jsonObject.getString("msg");
                    if (msg.equals("please complete first login process")) {
                        Intent i = new Intent(OTPVerification.this, UserDetails.class);
                        i.putExtra("PhoneNumber", phoneNumber);
                        finish();
                        startActivity(i);
                    }
                }
                /* status - true
                  getting details from database successfully
                 */
                else {
                    // Redirecting To MainActivity
                    Intent i = new Intent(OTPVerification.this, MainActivity.class);
                    finish();
                    startActivity(i);
                }

            }
        });


    }

    private void sendMessage(String otp) {
        OTP otp1 = new OTP(phoneNumber, otp, getApplicationContext());

        otp1.sendOTP(new OTP.OTPCallback() {
            @Override
            public void getStatus(boolean status, String msg) {

                if (status) {
                    Log.e(TAG, "getStatus: " + msg);
                    // If user login first time
                    if (msg.equals("request other details")) {
                        Intent i = new Intent(OTPVerification.this, UserDetails.class);
                        i.putExtra("PhoneNumber", phoneNumber);
                        finish();
                        startActivity(i);
                    } else {
                        System.out.println(msg);
                        Intent i = new Intent(OTPVerification.this, MainActivity.class);
                        finish();
                        startActivity(i);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("smsReceiver");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

}
