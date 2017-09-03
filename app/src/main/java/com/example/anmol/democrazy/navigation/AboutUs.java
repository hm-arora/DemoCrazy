package com.example.anmol.democrazy.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.anmol.democrazy.R;



public class AboutUs extends AppCompatActivity {

    TextView tx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity);

        tx= (TextView) findViewById(R.id.ActualText);

        String text="Democrazy is a free mobile app that will give you features \n \n \n"+
                "1. It will notify you with the bills that are pending \n \n"+
                "2.It will notify you with the bills that are passed \n \n"+
                "3.It will notify you with the Ordinances that are passed as well as lapsed \n \n";

        tx.setText(text);


    }
}
