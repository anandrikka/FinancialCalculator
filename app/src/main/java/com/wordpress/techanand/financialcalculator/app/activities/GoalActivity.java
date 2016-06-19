package com.wordpress.techanand.financialcalculator.app.activities;

import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.fragments.GoalFragment;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.models.GoalObject;
import com.wordpress.techanand.financialcalculator.app.models.LoanObject;

import java.util.ArrayList;

public class GoalActivity extends AppCompatActivity implements GoalFragment.GoalFragmentListener{

    private GoalFragment goalFragment;
    private LinearLayout resultLayout;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goalFragment = (GoalFragment) getSupportFragmentManager().findFragmentById(R.id.goal_form);
        resultLayout = (LinearLayout) findViewById(R.id.result);
        resultLayout.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void calculate(GoalObject goalObject) {
        //Inflated amount = A = P * (1+r)^t
        double inflation, timePeriod, todayValue, savedValue, expectedReturn;
        inflation = goalObject.getInflationPercent();
        timePeriod = goalObject.getDuration();
        todayValue = goalObject.getTodayValue();
        savedValue = goalObject.getSavedAmount();
        expectedReturn = goalObject.getReturnsPercent();
        double r = inflation/100.0;
        double x = 1+r;
        x = Math.pow(x, timePeriod);
        x = todayValue * x;
        double targetAmount = x;
        double finalTarget = x; // Final Target Amount
        if(savedValue > 0){
            x = 0.0;
            r = 0.0;
            r = expectedReturn/100.0;
            x = 1+r;
            x = Math.pow(x, timePeriod);
            x = savedValue * x;
            targetAmount = targetAmount-x;
        }

        //P = (M * r)/(((1+r)^n)-1)(1+r)
        x = 0.0;
        r = 0.0;
        r = expectedReturn/12.0; //monthly interest
        r = r/100.0;
        double n = timePeriod * 12;
        x = targetAmount * r;
        double y = 1+r;
        y = Math.pow(y, n);
        y = y-1;
        double y1 = 1+r;
        y = y*y1;
        double monthlyInvestment = x/y;

        double totalInvestment = Math.round(monthlyInvestment)*goalObject.getDuration()*12;

        goalObject.setTotalInvestment(totalInvestment);
        goalObject.setTotalReturns(finalTarget);
        goalObject.setWealthCreated(finalTarget-totalInvestment);

        String resultText =  String.format(getResources().getString(R.string.app_mutualfunds_goal_result_string,
                MainPrefs.getFormattedNumber(Math.round(goalObject.getTotalReturns())), Math.round(timePeriod),
                MainPrefs.getFormattedNumber(Math.round(monthlyInvestment))));

        CharSequence sq = Html.fromHtml(resultText);

        resultTextView = (TextView) findViewById(R.id.result_text_view);
        resultTextView.setText(sq);
        resultLayout.setVisibility(View.VISIBLE);
        createPieChart(goalObject);
    }

    @Override
    public void reset() {
       resultLayout.setVisibility(View.GONE);
    }

    private void createPieChart(GoalObject goalData){
        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float)goalData.getTotalInvestment(), 0));
        entries.add(new Entry((float)(goalData.getWealthCreated()), 1));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Investment");
        labels.add("Wealth Created");

        pieChart = PieChartConfig.createPieChart(this, pieChart, entries, labels, "");

    }
}
