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

public class LoanActivity extends AppCompatActivity implements LoanFragment.LoanFragmentListener{

    private ViewGroup result;

    public static final String[] TENURE_UNITS = {"Years", "Months"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = (ViewGroup) findViewById(R.id.result);
        result.setVisibility(View.GONE);
    }

    @Override
    public void reset() {
        result.setVisibility(View.GONE);
    }

    @Override
    public void calculate(LoanObject loanData) {
        /*
        * [P * r * (1+r)^n)]/[((1+r)^n)-1]
        *
        * P = Loan amount;
        * r = rate of interest;
        * n = total months
        *
        * */
        double tenure = loanData.getTenure();
        double interest = loanData.getInterestRate();
        double loanAmount = loanData.getLoanAmount();

        if(loanData.getPeriodUnit().equals(LoanActivity.TENURE_UNITS[0])){
            tenure = tenure * 12;
        }
        //[P * r * (1+r)^n)]/[((1+r)^n)-1]
        double r = interest/12.0;
        r = r/100.0;
        double n = tenure;
        double P = loanAmount;
        double x = 1+r;
        x = Math.pow(x, n);
        x = P * r * x;
        double y = 1+r;
        y = Math.pow(y, n);
        y = y - 1;
        double emi = x/y;
        double totalPaid = emi * n;
        double interestPaid = totalPaid - P;
        loanData.setTotalInterestPaid(interestPaid);

        String resultString = String.format(getResources().getString(R.string.app_loan_result_text),
                Math.round(emi), Math.round(P), Double.toString(interest), Math.round(tenure), Math.round(totalPaid));
        CharSequence resultSq = Html.fromHtml(resultString);
        ((TextView)findViewById(R.id.result_text_view)).setText(resultSq);
        createPieChart(loanData);
        result.setVisibility(View.VISIBLE);
    }

    private void createPieChart(LoanObject loanData){
        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float)loanData.getLoanAmount(), 0));
        entries.add(new Entry((float)(loanData.getTotalInterestPaid()), 1));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Principle");
        labels.add("Interest");

        pieChart = PieChartConfig.createPieChart(this, pieChart, entries, labels, "");

    }
}
