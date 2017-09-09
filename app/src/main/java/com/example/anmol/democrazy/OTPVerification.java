package com.example.anmol.democrazy;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anmol.democrazy.login.OTP;

public class OTPVerification extends AppCompatActivity {

    private static final String TAG = OTPVerification.class.getSimpleName();
    EditText OtpEditText;
    String phoneNumber;
    Button loginBtn;
    // Used to detect SMS
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otp_verification_activity);

        phoneNumber = getIntent().getExtras().getString(getString(R.string.Phone_number));
        OtpEditText = (EditText) findViewById(R.id.OtpEditText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = OtpEditText.getText().toString();
                sendMessage(otp);
            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent) {


                String senderName = intent.getExtras().getString("phoneNumber");

                String message = intent.getExtras().getString("message");

                // Don't change otherwise it will not get right otp
                String otp = message.replace("Hi, your otp for democrazy is: ", "").substring(0, 6);
                Log.e(TAG, "onReceive: " + otp );

                sendMessage(otp);

            }
        };


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
                        startActivity(i);
                    } else {
                        System.out.println(msg);
                        Intent i = new Intent(OTPVerification.this, MainActivity.class);
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
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

}
