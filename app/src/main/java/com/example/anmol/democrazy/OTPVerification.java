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
import android.widget.EditText;

import com.example.anmol.democrazy.login.OTP;

public class OTPVerification extends AppCompatActivity {

    EditText OtpEditText;
    String phoneNumber;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otp_verification_activity);

        phoneNumber=getIntent().getExtras().getString("PhoneNumber");

        OtpEditText= (EditText) findViewById(R.id.OtpEditText);


        broadcastReceiver=new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent) {


                String senderName=intent.getExtras().getString("phoneNumber");

                String message=intent.getExtras().getString("message");

                // Dont change otherwise it will not get right otp
                String otp=message.replace("Hi, your otp for democrazy is: ","").substring(0,6);

                OTP otp1=new OTP(phoneNumber,otp,getApplicationContext());

                otp1.sendOTP(new OTP.OTPCallback() {
                    @Override
                    public void getStatus(boolean status, String msg) {

                        if (status==true){

                            // If user login first time
                            if (msg.equals("request other details")){
                                Intent i=new Intent(OTPVerification.this,UserDetails.class);
                                i.putExtra("PhoneNumber",phoneNumber);
                                startActivity(i);
                            }

                            else{

                                System.out.println("JsonObjects");
                                System.out.println(msg);

                                Intent i=new Intent(OTPVerification.this,MainActivity.class);
                                startActivity(i);

                            }

                        }

                    }
                });

            }
        };




    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter("smsReceiver");
        registerReceiver(broadcastReceiver,intentFilter);
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
