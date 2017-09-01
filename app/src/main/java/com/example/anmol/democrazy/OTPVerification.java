package com.example.anmol.democrazy;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anmol.democrazy.login.OTP;
import com.example.anmol.democrazy.sms.smsReceiver;

public class OTPVerification extends AppCompatActivity {

    EditText OtpEditText;
    String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otp_verification_activity);

        phoneNumber=getIntent().getExtras().getString("PhoneNumber");

        OtpEditText= (EditText) findViewById(R.id.OtpEditText);

        EnableBroadCastSms();

        BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle b=intent.getExtras();

                String phNo=b.getString("phoneNumber");

                String message=b.getString("message");


                // Buggy Code I guess
                // If Incoming Phone Number equals to input Phone number the
                if (phNo.equals(phoneNumber)){

                    DisableBroadCastSms();

                }



                Toast.makeText(OTPVerification.this,"Phone Number : "+phNo+" Message : " + message,Toast.LENGTH_LONG);

            }
        };

        registerReceiver(broadcastReceiver,new IntentFilter("smsReceiver"));




    }

    public void EnableBroadCastSms(){

        ComponentName receiver=new ComponentName(this,smsReceiver.class);
        PackageManager pm=this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
        Toast.makeText(this,"Enable Sms service", Toast.LENGTH_SHORT).show();

    }

    public void DisableBroadCastSms(){

        ComponentName receiver=new ComponentName(this,smsReceiver.class);
        PackageManager pm=this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
        Toast.makeText(this,"Disabled Sms service", Toast.LENGTH_SHORT).show();

    }

}
