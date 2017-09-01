package com.example.anmol.democrazy;


import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anmol.democrazy.sms.smsReceiver;

public class OTPVerification extends AppCompatActivity {

    EditText OtpEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otp_verification_activity);

        OtpEditText= (EditText) findViewById(R.id.OtpEditText);





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
