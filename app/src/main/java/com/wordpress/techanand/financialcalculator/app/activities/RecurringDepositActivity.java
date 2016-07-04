package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anand Rikka on 5/21/2016.
 */

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.RecurringDepositFragment;

public class RecurringDepositActivity extends AppCompatActivity {

    public static final String[] COMPOUNDING_FREQ = {"Monthly", "Quarterly", "Yearly"};
    public static final String[] PERIOD = {"Months", "Years"};

    private RecurringDepositFragment recurringDepositFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recurringDepositFragment = (RecurringDepositFragment) getSupportFragmentManager().findFragmentById(R.id.recurring_deposit_form);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void recurringDepositTypeClicked(View view){
        switch(view.getId()) {
            case R.id.by_monthly:
                ((CheckBox)findViewById(R.id.by_monthly)).setChecked(true);
                ((CheckBox)findViewById(R.id.by_target)).setChecked(false);
                ((TextView)findViewById(R.id.installment_or_maturity_label)).setText("Monthly Installment");
                break;
            case R.id.by_target:
                ((CheckBox)findViewById(R.id.by_target)).setChecked(true);
                ((CheckBox)findViewById(R.id.by_monthly)).setChecked(false);
                ((TextView)findViewById(R.id.installment_or_maturity_label)).setText("Maturity Amount");
                break;
            default:
                break;
        }
    }
}
