package com.example.anmol.democrazy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anmol.democrazy.login.PhoneNumber;


public class LoginActivity extends AppCompatActivity {

    EditText editText;
    Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // initViews
        initViews();

        // Add Listener to button
        submitButton.setOnClickListener(submitListener);
    }

    public void initViews() {
        editText = (EditText) findViewById(R.id.PhoneNumber);
        submitButton = (Button) findViewById(R.id.SubmitPhoneNumber);
    }

    View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String number = editText.getText().toString();
            Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
            final PhoneNumber phoneNumber = new PhoneNumber(number, LoginActivity.this);
            phoneNumber.sendNumber(new PhoneNumber.phNoCallback() {
                @Override
                public void getResult(boolean status, String msg) {

                    if (status && msg.equals("message(s) queued")) {
                        Intent i = new Intent(LoginActivity.this, OTPVerification.class);
                        i.putExtra("PhoneNumber", number);
                        startActivity(i);

                    }

                }
            });
        }
    };

}
