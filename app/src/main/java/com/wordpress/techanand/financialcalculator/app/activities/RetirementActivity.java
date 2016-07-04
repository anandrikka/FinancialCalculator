package com.wordpress.techanand.financialcalculator.app.activities;

/**
 * Created by Anand Rikka on 5/13/2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.RetirementFragment;
import com.wordpress.techanand.financialcalculator.app.models.RetirementObject;

public class RetirementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
