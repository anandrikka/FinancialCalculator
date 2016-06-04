package com.wordpress.techanand.financialcalculator.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;


public class SIPForGoal extends Fragment {

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

    private EditText todayValueText,alreadySavedText, inflationText, expectedReturnText, goalReachPeriod;
    private Button resetButton, calculateButton;
    private TextView resultMsgText;

    public SIPForGoal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sip_for_goal, container, false);
        setRetainInstance(true);
        initializeLoad(view);
        return view;
    }

    private void initializeLoad(View view){
        todayValueText = (EditText) view.findViewById(R.id.today_value);
        alreadySavedText = (EditText) view.findViewById(R.id.saved_amount);
        inflationText = (EditText) view.findViewById(R.id.inflation);
        expectedReturnText = (EditText) view.findViewById(R.id.expected_return);
        goalReachPeriod = (EditText) view.findViewById(R.id.years_to_reach);
        resetButton = (Button) view.findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayValueText.setText("");
                alreadySavedText.setText("");
                inflationText.setText("");
                expectedReturnText.setText("");
                goalReachPeriod.setText("");
            }
        });
        calculateButton = (Button) view.findViewById(R.id.calculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });
        calculate(true);
        resultMsgText = (TextView) view.findViewById(R.id.result_text);
    }

    private void calculate(boolean isFromInitialLoad){
        String todayString, savedString, inflationString, expectedReturnString, timePeriodString;
        double todayValue, savedValue, inflation, expectedReturn, timePeriod;
        todayString = todayValueText.getText().toString();
        savedString = alreadySavedText.getText().toString();
        inflationString = inflationText.getText().toString();
        expectedReturnString = expectedReturnText.getText().toString();
        timePeriodString = goalReachPeriod.getText().toString();

        if(!todayString.equals("") && !inflationText.equals("") &&
                !expectedReturnString.equals("") && !timePeriodString.equals("")){
            todayValue = Double.parseDouble(todayString);
            if(savedString.length()>0){
                savedValue = Double.parseDouble(savedString);
            }else{
                savedValue = 0.0;
            }
            inflation = Double.parseDouble(inflationString);
            expectedReturn = Double.parseDouble(expectedReturnString);
            timePeriod = Double.parseDouble(timePeriodString);
            //Inflated amount = A = P * (1+r)^t

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
            String resultText =  String.format(getResources().getString(R.string.app_mutualfunds_goal_result_string,
                    Math.round(finalTarget), Math.round(timePeriod),
                    Math.round(monthlyInvestment)));
            resultMsgText.setText(resultText);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Fill All fields",
                    "OK").create().show();
        }
    }
}
