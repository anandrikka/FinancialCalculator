package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.wordpress.techanand.financialcalculator.R;

public class LoanActivity extends AppCompatActivity {

    public static final String[] TENURE_UNITS = {"Years", "Months"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner tenureUnitSpinner = (Spinner) findViewById(R.id.tenure_unit);
        ArrayAdapter<String> tenureUnitAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_dropdown, TENURE_UNITS);
        tenureUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        tenureUnitSpinner.setAdapter(tenureUnitAdapter);

    }
}
