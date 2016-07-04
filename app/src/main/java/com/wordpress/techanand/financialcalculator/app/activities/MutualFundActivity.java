package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anand Rikka 5/15/2016
 */
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.MutualFundSIP;

public class MutualFundActivity extends AppCompatActivity {

    public static final String[] PERIOD = {"Months", "Years"};

    private MutualFundSIP mutualFundSIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mutual_fund);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mutualFundSIP = (MutualFundSIP) getSupportFragmentManager().findFragmentById(R.id.mutualfund_form);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void mutualFundTypeClicked(View view){
        switch(view.getId()) {
            case R.id.by_sip:
                ((CheckBox)findViewById(R.id.by_sip)).setChecked(true);
                ((CheckBox)findViewById(R.id.by_target)).setChecked(false);
                ((TextView)findViewById(R.id.investment_label)).setText("Monthly Investment");
                break;
            case R.id.by_target:
                ((CheckBox)findViewById(R.id.by_target)).setChecked(true);
                ((CheckBox)findViewById(R.id.by_sip)).setChecked(false);
                ((TextView)findViewById(R.id.investment_label)).setText("Target Amount");
                break;
            default:
                break;
        }
    }
}
