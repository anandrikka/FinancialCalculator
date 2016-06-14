package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.RetirementFragment;
import com.wordpress.techanand.financialcalculator.app.models.RetirementObject;

public class RetirementActivity extends AppCompatActivity implements RetirementFragment.RetirementFragmentListener{



    private RetirementFragment retirementFragment;

    private ViewGroup result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = (ViewGroup) findViewById(R.id.result);
        result.setVisibility(View.GONE);

    }

    @Override
    public void calculate(RetirementObject retirementData) {
        //Inflated Monthly Amount at the age of retirement
        //A = P * (1+r)^t
        double P = retirementData.getMonthlyExpenses() * 12;
        double r = retirementData.getInflation() * 0.01;
        double t = retirementData.getWorkYearsLeft();
        double A = 0.0;
        int length = (int)t+(int)retirementData.getYearsToSurvive();
        for(int i=(int)t; i<length; i++){
            A = A + (P * Math.pow((1+r), i));
            //A = P * Math.pow((1+r), i);
            //A = A + ((P * Math.pow(1+r, i)) - A);
            AppMain.debug(MainPrefs.getFormattedNumber(A));
        }
        AppMain.debug("Monthly Exp: "+MainPrefs.getFormattedNumber(A)+" - "+MainPrefs.getFormattedNumber(A*12*retirementData.getYearsToSurvive()));
        result.setVisibility(View.VISIBLE);
    }

    @Override
    public void reset() {
        result.setVisibility(View.GONE);
    }
}
