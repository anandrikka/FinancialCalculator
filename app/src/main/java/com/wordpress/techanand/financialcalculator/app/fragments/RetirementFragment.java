package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 06/12/2016
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;;

import com.github.mikephil.charting.charts.PieChart;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.models.RetirementObject;


public class RetirementFragment extends Fragment {

    public static final String ID = RetirementFragment.class.getName();

    private RetirementObject retirementData;

    private EditText currentAgeText, retirementAgeText, lifeExpectancyText,
            monthlyExpText, inflationText, existingInvestmentText, expectedReturnsText;
    private Button resetButton, calculateButton;
    private CardView results;
    private PieChart pieChart;

    public RetirementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retirementData = new RetirementObject();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.retirement_fragment, container, false);
        currentAgeText = (EditText) view.findViewById(R.id.current_age);
        retirementAgeText = (EditText) view.findViewById(R.id.retirement_age);
        lifeExpectancyText = (EditText) view.findViewById(R.id.life_expentency);
        monthlyExpText = (EditText) view.findViewById(R.id.monthly_expenses);
        inflationText = (EditText) view.findViewById(R.id.inflation_rate);
        existingInvestmentText = (EditText) view.findViewById(R.id.existing_investment);
        expectedReturnsText = (EditText) view.findViewById(R.id.expected_return);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        results = (CardView) view.findViewById(R.id.result_card);
        results.setVisibility(View.GONE);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        pieChart.setVisibility(View.GONE);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAgeText.setText("");
                retirementAgeText.setText("");
                lifeExpectancyText.setText("");
                monthlyExpText.setText("");
                inflationText.setText("");
                existingInvestmentText.setText("");
                expectedReturnsText.setText("");
                results.setVisibility(View.GONE);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflationText.setError("Test error");
                calculate(false);
            }
        });

        calculate(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void calculate(boolean isFromInitialLoad){
        String currentAgeString, retirementAgeString, lifeExpectancyString,
                monthlyExpString, inflationString, existingInvestmentString, expectedReturnsString;
        currentAgeString = currentAgeText.getText().toString();
        retirementAgeString = retirementAgeText.getText().toString();
        lifeExpectancyString = lifeExpectancyText.getText().toString();
        monthlyExpString = monthlyExpText.getText().toString();
        inflationString = inflationText.getText().toString();;
        existingInvestmentString = existingInvestmentText.getText().toString();
        expectedReturnsString = expectedReturnsText.getText().toString();

        if(!currentAgeString.equals("") && !retirementAgeString.equals("") &&
                !lifeExpectancyString.equals("") && !monthlyExpString.equals("") &&
                !inflationString.equals("") && !expectedReturnsString.equals("")){

            retirementData.setCurrentAge(Double.parseDouble(currentAgeString));
            retirementData.setRetirementAge(Double.parseDouble(retirementAgeString));
            retirementData.setLifeExpectancy(Double.parseDouble(lifeExpectancyString));
            retirementData.setMonthlyExpenses(Double.parseDouble(monthlyExpString));
            retirementData.setInflation(Double.parseDouble(inflationString));
            if(existingInvestmentString.equals("")){
                retirementData.setExistingInvestment(0.0);
            }else{
                retirementData.setExistingInvestment(Double.parseDouble(existingInvestmentString));
            }
            retirementData.setExpectedReturns(Double.parseDouble(expectedReturnsString));
            calculateResult(retirementData);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    public void calculateResult(RetirementObject retirementData) {
        //Inflated Monthly Amount at the age of retirement
        //A = P * (1+r)^t
        double P = retirementData.getMonthlyExpenses() * 12;
        double r = retirementData.getInflation() * 0.01;
        double t = retirementData.getWorkYearsLeft();
        double A = 0.0;
        int length = (int)t+(int)retirementData.getYearsToSurvive();
        for(int i=(int)t; i<length; i++){
            A = A + (P * Math.pow((1+r), i));
            //A = P * Math.pow((1+r), i);
            //A = A + ((P * Math.pow(1+r, i)) - A);
            AppMain.debug(MainPrefs.getFormattedNumber(A));
        }
        AppMain.debug("Monthly Exp: "+MainPrefs.getFormattedNumber(A)+" - "+MainPrefs.getFormattedNumber(A*12*retirementData.getYearsToSurvive()));
        results.setVisibility(View.VISIBLE);
    }

}
