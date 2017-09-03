package com.example.anmol.democrazy.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;



public class smsReceiver extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {


        final Bundle bundle = intent.getExtras();

        Intent intent1=new Intent("smsReceiver");

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    String format = bundle.getString("format");

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i],format);

                    // Phone Number
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;

                    // Message
                    String message = currentMessage.getDisplayMessageBody();

                    // Sending Phone Number
                    intent1.putExtra("phoneNumber",phoneNumber);

                    // Sending Message OTP
                    intent1.putExtra("message",message);


                    // Sending Broad cast whosoever want to receive this Broadcast will get it like OTP Verification will receive
                    // it
                    context.sendBroadcast(intent1);

                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }


    }
}
