package com.example.anmol.democrazy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class FinancialInclusionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_inclusions);

        initViews();

        setToolBar();
    }

    private void setToolBar() {
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Financial Inclusions");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
