package com.wordpress.techanand.financialcalculator.app.activities;

/**
 * Created by Anand Rikka on 5/15/2016
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableRow;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.FixedDepositFragment;
import com.wordpress.techanand.financialcalculator.app.fragments.FixedDepositResult;
import com.wordpress.techanand.financialcalculator.app.models.FixedDepositObject;

public class FixedDepositActivity extends AppCompatActivity implements FixedDepositFragment.FixedDepositListener{

    public static final String[] PERIOD = {"Years", "Months", "Days"};
    public static final String[] COMPOUNDING_FREQ = {"Monthly", "Quarterly", "Half Yearly", "Yearly"};
    public static final String[] PAYOUT_FREQ = {"Monthly", "Quarterly"};

    private FixedDepositFragment fdFragment;
    private FixedDepositResult fdResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fixed_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fdFragment = (FixedDepositFragment) getSupportFragmentManager().findFragmentById(R.id.fixed_deposit_form);
        fdResult = (FixedDepositResult) getSupportFragmentManager().findFragmentById(R.id.fixed_deposit_result);

        getSupportFragmentManager().beginTransaction().hide(fdResult).commit();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void fixedDepositTypeClicked(View view){
        switch(view.getId()) {
            case R.id.by_standard:
                ((CheckBox)findViewById(R.id.by_standard)).setChecked(true);
                ((CheckBox)findViewById(R.id.by_interest_payout)).setChecked(false);
                ((TableRow)findViewById(R.id.interest_payout_row)).setVisibility(View.GONE);
                break;
            case R.id.by_interest_payout:
                ((CheckBox)findViewById(R.id.by_interest_payout)).setChecked(true);
                ((CheckBox)findViewById(R.id.by_standard)).setChecked(false);
                ((TableRow)findViewById(R.id.interest_payout_row)).setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void reset() {
       getSupportFragmentManager().beginTransaction().hide(fdResult).commit();
    }

    @Override
    public void calculate(FixedDepositObject fixedDepositObject) {
        /*Formula for fixed deposit:
        *
        * A = P * (1+r/n)^nt
        *
        * P - Principle Amount
        * r - Rate of Interest
        * t - Number of Years
        * n - No' of compounding periods, example: for quarterly (4), for half yearly (2) etc..
        *
        * */
        double P, r, t, n, A;
        String tUnit, nUnit;
        P = fixedDepositObject.getFdAmount();
        r = fixedDepositObject.getRoi() * 0.01;
        t = fixedDepositObject.getTime();
        tUnit = fixedDepositObject.getTimeUnit();
        nUnit = fixedDepositObject.getCompoundingUnit();
        if(tUnit == FixedDepositActivity.PERIOD[0]){

        }else if(tUnit == FixedDepositActivity.PERIOD[1]){
            t = t/12.0;
        }else {
            t = t/365.0;
        }
        if(fixedDepositObject.isInterestPayoutFD()){
            if(fixedDepositObject.getPayoutUnit().equals(FixedDepositActivity.PAYOUT_FREQ[0])){
                t = 1.0/12.0;
            }else{
                t = 3.0/12.0;
            }
        }
        if(nUnit == FixedDepositActivity.COMPOUNDING_FREQ[0]){
            n = 12;
        }else if(nUnit == FixedDepositActivity.COMPOUNDING_FREQ[1]){
            n = 4;
        }else if(nUnit == FixedDepositActivity.COMPOUNDING_FREQ[2]){
            n = 2;
        }else {
            n = 1;
        }
        //A = P * (Math.pow((1+r/n), (n*t)));
        A = P * (Math.pow((1+r/n), (n*t)));
        if(fixedDepositObject.isStandardFD()){
            fixedDepositObject.setMaturityAmount(A);
            fixedDepositObject.setInterest(A-P);
        }
        if(fixedDepositObject.isInterestPayoutFD()){
            double totalMonths, payout;
            payout = A-P;
            fixedDepositObject.setPayoutAmount(payout);
            if(tUnit.equals(FixedDepositActivity.PERIOD[0])){
                totalMonths = fixedDepositObject.getTime() * 12;
            }else if(tUnit.equals(FixedDepositActivity.PERIOD[1])){
                totalMonths = fixedDepositObject.getTime();
            }else {
                totalMonths = fixedDepositObject.getTime()/30.0;
            }
            if(fixedDepositObject.getPayoutUnit().equals(FixedDepositActivity.PAYOUT_FREQ[0])){
                fixedDepositObject.setInterest(payout * totalMonths);
                if(totalMonths < 1){
                    fixedDepositObject.setPayoutAmount(fixedDepositObject.getInterest());
                }
            }else{
                double quarters = totalMonths/3.0;
                if(quarters < 1){
                    fixedDepositObject.setPayoutAmount(fixedDepositObject.getInterest());
                }
                fixedDepositObject.setInterest(payout * quarters);
            }

        }
        fdResult.displayResult(fixedDepositObject);
        getSupportFragmentManager().beginTransaction().show(fdResult).commit();
    }
}
