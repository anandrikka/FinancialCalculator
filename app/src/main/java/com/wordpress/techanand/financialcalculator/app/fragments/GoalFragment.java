package com.wordpress.techanand.financialcalculator.app.fragments;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.models.GoalObject;

/**
 * Created by Anand Rikka on 6/6/2016
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.models.GoalObject;

import java.util.ArrayList;


public class GoalFragment extends Fragment {

    public static final String ID = GoalFragment.class.getName();

    private EditText todayValueText,alreadySavedText, inflationText, expectedReturnText, goalReachPeriod;
    private Button resetButton, calculateButton;
    private CardView results;
    private PieChart pieChart;
    private TextView resultTextView;

    private GoalObject goalObject;
    private boolean isCalcClicked;

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_sip, container, false);
        Toolbar appBarLayout = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Goal Calculator");
        }
        initializeLoad(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goalObject = new GoalObject();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        todayValueText.setText(todayValueText.getText().toString());
        alreadySavedText.setText(alreadySavedText.getText().toString());
        inflationText.setText(inflationText.getText().toString());
        expectedReturnText.setText(expectedReturnText.getText().toString());
        goalReachPeriod.setText(goalReachPeriod.getText().toString());

    }

    private void initializeLoad(View view){
        todayValueText = (EditText) view.findViewById(R.id.today_value);
        alreadySavedText = (EditText) view.findViewById(R.id.saved_amount);
        inflationText = (EditText) view.findViewById(R.id.inflation);
        expectedReturnText = (EditText) view.findViewById(R.id.expected_return);
        goalReachPeriod = (EditText) view.findViewById(R.id.years_to_reach);
        resetButton = (Button) view.findViewById(R.id.reset);
        results = (CardView) view.findViewById(R.id.results);
        results.setVisibility(View.GONE);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        pieChart.setVisibility(View.GONE);
        resultTextView = (TextView) view.findViewById(R.id.result_text_view);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayValueText.setText("");
                alreadySavedText.setText("");
                inflationText.setText("");
                expectedReturnText.setText("");
                goalReachPeriod.setText("");
                results.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
            }
        });
        calculateButton = (Button) view.findViewById(R.id.calculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false, true);
            }
        });
        calculate(true, false);
    }
    /*Formula for inflated amount:
    *
    * A = P * (1+r)^t
    *
    * P - Principle Amount
    * r - Rate of Interest
    * t - Number of Years
    *
    * Formula for monthly investment
      *
      *  P = (M * r)/((1+r)^n-1)
      *
    * */
    private void calculate(boolean isFromInitialLoad, boolean isCalcClicked){

        this.isCalcClicked = isCalcClicked;
        if(!this.isCalcClicked){
            return;
        }
        String todayString, savedString, inflationString, expectedReturnString, timePeriodString;
        double todayValue, savedValue, inflation, expectedReturn, timePeriod;
        todayString = todayValueText.getText().toString();
        savedString = alreadySavedText.getText().toString();
        inflationString = inflationText.getText().toString();
        expectedReturnString = expectedReturnText.getText().toString();
        timePeriodString = goalReachPeriod.getText().toString();
        if(!todayString.equals("") && !inflationText.equals("") &&
                !expectedReturnString.equals("") && !timePeriodString.equals("")){
            goalObject.setTodayValue(Double.parseDouble(todayString));
            if(savedString.length()>0){
                goalObject.setSavedAmount(Double.parseDouble(savedString));
            }else{
                goalObject.setSavedAmount(0.0);
            }
            goalObject.setInflationPercent(Double.parseDouble(inflationString));
            goalObject.setReturnsPercent(Double.parseDouble(expectedReturnString));
            goalObject.setDuration(Double.parseDouble(timePeriodString));
            calculateResults(goalObject);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Fill All fields",
                    "OK").create().show();
        }
    }

    public void calculateResults(GoalObject goalObject) {
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

        resultTextView.setText(sq);
        results.setVisibility(View.VISIBLE);
        createPieChart(goalObject);
    }

    private void createPieChart(GoalObject goalData){
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float)goalData.getTotalInvestment(), 0));
        entries.add(new Entry((float)(goalData.getWealthCreated()), 1));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Investment");
        labels.add("Wealth Created");

        pieChart = PieChartConfig.createPieChart(this.getActivity(), pieChart, entries, labels, "");
        pieChart.setVisibility(View.VISIBLE);

    }
}
