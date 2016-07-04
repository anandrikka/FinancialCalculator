package com.wordpress.techanand.financialcalculator.app.activities;

/**
 * Created by Anand Rikka on 5/21/2016
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.fragments.LoanFragment;
import com.wordpress.techanand.financialcalculator.app.models.LoanObject;

import java.util.ArrayList;

public class LoanActivity extends AppCompatActivity {

    public static final String[] TENURE_UNITS = {"Years", "Months"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
