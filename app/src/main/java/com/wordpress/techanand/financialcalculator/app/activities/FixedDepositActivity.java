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
import com.wordpress.techanand.financialcalculator.app.models.FixedDepositObject;

public class FixedDepositActivity extends AppCompatActivity {

    public static final String[] PERIOD = {"Years", "Months", "Days"};
    public static final String[] COMPOUNDING_FREQ = {"Monthly", "Quarterly", "Half Yearly", "Yearly"};
    public static final String[] PAYOUT_FREQ = {"Monthly", "Quarterly"};

    private FixedDepositFragment fdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fixed_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fdFragment = (FixedDepositFragment) getSupportFragmentManager().findFragmentById(R.id.fixed_deposit_form);

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
}
