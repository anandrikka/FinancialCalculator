package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.MutualFundSIP;
import com.wordpress.techanand.financialcalculator.app.fragments.MutualFundSIPResult;
import com.wordpress.techanand.financialcalculator.app.models.MutualFund;

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

        mutualFundSIP = (MutualFundSIP) getSupportFragmentManager().findFragmentByTag(MutualFundSIP.class.getName());
        mutualFundSIPResult = (MutualFundSIPResult) getSupportFragmentManager().findFragmentByTag(MutualFundSIPResult.class.getName());
        if(mutualFundSIP != null){
            getSupportFragmentManager().beginTransaction().remove(mutualFundSIP).commit();
        }
        if(mutualFundSIPResult != null){
            getSupportFragmentManager().beginTransaction().remove(mutualFundSIPResult).commit();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new MutualFundSIP(), MutualFundSIP.class.getName())
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new MutualFundSIPResult(), MutualFundSIPResult.class.getName())
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mutualFundSIP = (MutualFundSIP) getSupportFragmentManager().findFragmentByTag(MutualFundSIP.class.getName());
        mutualFundSIPResult = (MutualFundSIPResult) getSupportFragmentManager().findFragmentByTag(MutualFundSIPResult.class.getName());
        mutualFundSIPResult.getView().setVisibility(View.GONE);
    }

    public void mutualFundTypeClicked(View view){
        switch(view.getId()) {
            case R.id.by_sip:
                ((CheckBox)findViewById(R.id.by_target)).setChecked(false);
                ((TextView)findViewById(R.id.investment_label)).setText("Monthly Investment");
                break;
            case R.id.by_target:
                ((CheckBox)findViewById(R.id.by_sip)).setChecked(false);
                ((TextView)findViewById(R.id.investment_label)).setText("Target Amount");
                break;
            default:
                break;
        }
    }

    @Override
    public void resetListener() {
        mutualFundSIPResult.getView().setVisibility(View.GONE);
    }

    @Override
    public void calculateListener(MutualFund mutualFundData, boolean isFromInitialLoad) {
        /*
        * P = A = P * [{(1+i)^n – 1 }/i] * (1+i)
        * PMT - Per Month
        * r - rate of interest
        * n - total installments
        *
        * */

        double P, n, i;

        P = mutualFundData.getAmount();
        if(mutualFundData.getPeriodUnit().equals(PERIOD[1])){
            n = mutualFundData.getTotalPeriod() * 12.0;
        }else{
            n = mutualFundData.getTotalPeriod();
        }
        i = mutualFundData.getAnnualReturns();
        i = i/12.0;
        i = i * 0.01;

        if(mutualFundData.isMonthlySIP()){
            double x, y;
            x = 1+i;
            x = Math.pow(x, n);
            x = x-1;
            x = x/i;
            x = P*x;
            y = 1+i;
            x = x*y;
            mutualFundData.setTotalInvestment(P*n);
            mutualFundData.setTotalReturns(x);
            mutualFundData.setWealthGained(x - (P*n));
        }

        if(mutualFundData.isTargetAmount()){
            double x, y;
            x = P*i;
            y = 1+i;
            y = Math.pow(y, n);
            y = y - 1;
            double y1 = 1+i;
            y = y*y1;
            double sip = x/y;
            mutualFundData.setTotalInvestment(sip*n);
            mutualFundData.setMonthlySIP(sip);
            mutualFundData.setWealthGained(mutualFundData.getAmount() - (sip*n));
        }
        mutualFundSIPResult.displayResult(mutualFundData);
        mutualFundSIPResult.getView().setVisibility(View.VISIBLE);

    }
}
