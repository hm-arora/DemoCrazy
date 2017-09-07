package com.example.anmol.democrazy;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.anmol.democrazy.login.LoginKey;
import com.example.anmol.democrazy.login.SendUserDetails;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.N)
public class UserDetails extends AppCompatActivity {

    EditText FullName;
    EditText Dob;
    EditText email;
    EditText PinCode;
    RadioGroup rg;
    Button DatePickerButt;
    Button Submit;

    //DatePicker
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        initViews();
        rg.clearCheck();

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //DatePicker
        DatePickerButt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                showDialog(DATE_PICKER_ID);

            }
        });
        SubmitClick();


    }


    public void initViews() {

        FullName = (EditText) findViewById(R.id.FullNameUserDetailEditText);
        Dob = (EditText) findViewById(R.id.DOBUserDetailEditText);
        email = (EditText) findViewById(R.id.EmailUserDetailEditText);
        PinCode = (EditText) findViewById(R.id.PinCodeUserDetailEditText);
        rg = (RadioGroup) findViewById(R.id.RG);
        DatePickerButt = (Button) findViewById(R.id.DatePicker);
        Submit = (Button) findViewById(R.id.SubmitUserDetails);

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            String month1, day1;
            if (month < 10) {
                month1 = "0" + month;
            } else {
                month1 = "" + month;
            }
            if (day < 10) {
                day1 = "0" + day;
            } else {
                day1 = "" + day;
            }
            Dob.setText(year + "-" + month1 + "-" + day1);
        }
    };


    // submitButton Click

    public void SubmitClick() {

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName = FullName.getText().toString();
                String DOB = Dob.getText().toString();
                String Email = email.getText().toString();
                String pincode = PinCode.getText().toString();

                int selectedId = rg.getCheckedRadioButtonId();

                if (!(fullName.equals("") && DOB.equals("") && Email.equals("") && pincode.equals("") && selectedId == -1)) {
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    String RadioText = radioButton.getText().toString().substring(0, 1);
                    List<String> list = new ArrayList<String>();
                    list.add(fullName);
                    list.add(DOB);
                    list.add(RadioText);
                    list.add(Email);
                    list.add(pincode);
                    list.add(getIntent().getExtras().getString("PhoneNumber"));

                    System.out.println(getIntent().getExtras().getString("PhoneNumber"));
                    //Send Data
                    SendUserDetails sendUserDetails = new SendUserDetails(list, UserDetails.this);

                    sendUserDetails.sendDetails(new SendUserDetails.UserCallBack() {
                        @Override
                        public void getResult(boolean status) {
                            if (status) {
                                //// Keep Track of opening Activity
                                Intent i = new Intent(UserDetails.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill All Details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    // On Back Pressed to Login Activity Not OTPVerification Activity
    @Override
    public void onBackPressed() {

        // Removing Login Key as Process is Not completed.......
        LoginKey l=new LoginKey(UserDetails.this,"");
        l.setLoginKey();

        Intent i = new Intent(UserDetails.this,LoginActivity.class);
        startActivity(i);

    }
}


