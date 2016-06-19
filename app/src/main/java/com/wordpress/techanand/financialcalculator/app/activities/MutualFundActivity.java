package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.MutualFundSIP;
import com.wordpress.techanand.financialcalculator.app.fragments.MutualFundSIPResult;
import com.wordpress.techanand.financialcalculator.app.models.MutualFundObject;

public class MutualFundActivity extends AppCompatActivity implements MutualFundSIP.MutualFundSIPListener{

    public static final String[] PERIOD = {"Months", "Years"};

    private MutualFundSIP mutualFundSIP;
    private MutualFundSIPResult mutualFundSIPResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_fund);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mutualFundSIP = (MutualFundSIP) getSupportFragmentManager().findFragmentById(R.id.mutualfund_form);
        mutualFundSIPResult = (MutualFundSIPResult) getSupportFragmentManager().findFragmentById(R.id.mutualfund_result);

        getSupportFragmentManager().beginTransaction().hide(mutualFundSIPResult).commit();

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

    @Override
    public void resetListener() {
        getSupportFragmentManager().beginTransaction().hide(mutualFundSIPResult).commit();
    }

    @Override
    public void calculateListener(MutualFundObject mutualFundObjectData, boolean isFromInitialLoad) {
        /*
        * P = A = P * [{(1+i)^n – 1 }/i] * (1+i)
        * PMT - Per Month
        * r - rate of interest
        * n - total installments
        *
        * */

        double P, n, i;

        P = mutualFundObjectData.getAmount();
        if(mutualFundObjectData.getPeriodUnit().equals(PERIOD[1])){
            n = mutualFundObjectData.getTotalPeriod() * 12.0;
        }else{
            n = mutualFundObjectData.getTotalPeriod();
        }
        i = mutualFundObjectData.getAnnualReturns();
        i = i/12.0;
        i = i * 0.01;

        if(mutualFundObjectData.isMonthlySIP()){
            double x, y;
            x = 1+i;
            x = Math.pow(x, n);
            x = x-1;
            x = x/i;
            x = P*x;
            y = 1+i;
            x = x*y;
            mutualFundObjectData.setTotalInvestment(P*n);
            mutualFundObjectData.setTotalReturns(x);
            mutualFundObjectData.setWealthGained(x - (P*n));
        }

        if(mutualFundObjectData.isTargetAmount()){
            double x, y;
            x = P*i;
            y = 1+i;
            y = Math.pow(y, n);
            y = y - 1;
            double y1 = 1+i;
            y = y*y1;
            double sip = x/y;
            mutualFundObjectData.setTotalInvestment(sip*n);
            mutualFundObjectData.setMonthlySIP(sip);
            mutualFundObjectData.setWealthGained(mutualFundObjectData.getAmount() - (sip*n));
        }
        mutualFundSIPResult.displayResult(mutualFundObjectData);

        getSupportFragmentManager().beginTransaction().show(mutualFundSIPResult).commit();

    }
}
