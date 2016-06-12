package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.RecurringDepositFragment;
import com.wordpress.techanand.financialcalculator.app.fragments.RecurringDepositResult;
import com.wordpress.techanand.financialcalculator.app.models.RecurringDepositObject;

import org.w3c.dom.Text;

public class RecurringDepositActivity extends AppCompatActivity implements RecurringDepositFragment.RecurringDepositListener{

    public static final String[] COMPOUNDING_FREQ = {"Monthly", "Quarterly", "Yearly"};
    public static final String[] PERIOD = {"Months", "Years"};

    private RecurringDepositFragment recurringDepositFragment;
    private RecurringDepositResult recurringDepositResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*getSupportFragmentManager()
                .beginTransaction()
                .remove(recurringDepositFragment)
                .remove(recurringDepositResult).commit();*/
        recurringDepositFragment = (RecurringDepositFragment) getSupportFragmentManager().findFragmentByTag(RecurringDepositFragment.class.getName());
        recurringDepositResult = (RecurringDepositResult) getSupportFragmentManager().findFragmentByTag(RecurringDepositResult.class.getName());

        if(recurringDepositFragment == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new RecurringDepositFragment(), RecurringDepositFragment.class.getName())
                    .commit();
        }

        if(recurringDepositResult == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new RecurringDepositResult(), RecurringDepositResult.class.getName())
                    .commit();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        recurringDepositFragment = (RecurringDepositFragment) getSupportFragmentManager().findFragmentByTag(RecurringDepositFragment.class.getName());
        recurringDepositResult = (RecurringDepositResult) getSupportFragmentManager().findFragmentByTag(RecurringDepositResult.class.getName());
        recurringDepositResult.getView().setVisibility(View.GONE);
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

    @Override
    public void calculate(RecurringDepositObject recurringDepositData) {
        /**
         *   M = ( R * [(1+r)^n - 1 ] ) / (1-(1+r)^-1/3)
         *
         *   M = Maturity value
         *   R = Monthly Installment
         *   r = Rate of Interest (i) / 400
         *   n = Number of Quarters
         * */

        double R, r, n, M, P, I;

        if(recurringDepositData.getDurationUnit().equals(RecurringDepositActivity.PERIOD[0])){
            n = recurringDepositData.getDuration()/3.0;
        }else{
            n = recurringDepositData.getDuration() * 4;
        }

        r = recurringDepositData.getRoi();
        r = r/400;

        if(recurringDepositData.isMonthly()){
            R = recurringDepositData.getMonthlyInvestment();
            double x = 1+r;
            x = Math.pow(x, n);
            x = x-1;
            x = R*x;
            double y = 1+r;
            double y1 = 1.0/3.0;
            y = Math.pow(y,y1);
            y = 1.0/y;
            y = 1-y;
            M  = x/y;
            P = R*n*3;
            I = M-P;
            recurringDepositData.setMaturityAmount(M);
            recurringDepositData.setInvestedAmount(P);
            recurringDepositData.setTotalInterest(I);
        }
        if(recurringDepositData.isByTargetAmount()){
            M = recurringDepositData.getMaturityAmount();
            double x = 1+r;
            double y1 = 1.0/3.0;
            x = Math.pow(x, y1);
            x = 1/x;
            x = 1-x;
            x = M*x;
            double y = 1+r;
            y = Math.pow(y, n);
            y = y-1;
            R = x/y;
            R = Math.round(R);
            P = R*n*3;
            I = M-P;
            recurringDepositData.setMonthlyInvestment(R);
            recurringDepositData.setInvestedAmount(P);
            recurringDepositData.setTotalInterest(I);
        }

        recurringDepositResult.displayResults(recurringDepositData);
        recurringDepositResult.getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void reset() {
        recurringDepositResult.getView().setVisibility(View.GONE);
    }
}
