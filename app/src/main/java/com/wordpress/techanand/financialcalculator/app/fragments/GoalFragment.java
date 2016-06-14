package com.wordpress.techanand.financialcalculator.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.models.GoalObject;


public class GoalFragment extends Fragment {

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

    public interface GoalFragmentListener {
        public void calculate(GoalObject goalObject);
        public void reset();
    }

    private EditText todayValueText,alreadySavedText, inflationText, expectedReturnText, goalReachPeriod;
    private Button resetButton, calculateButton;
    private TextView resultMsgText;

    private GoalFragmentListener goalFragmentListener;
    private GoalObject goalObject;

    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_sip, container, false);
        setRetainInstance(true);
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
        goalFragmentListener = (GoalFragmentListener)context;
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
                goalFragmentListener.reset();
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
            goalObject.setTodayValue(Double.parseDouble(todayString));
            if(savedString.length()>0){
                goalObject.setSavedAmount(Double.parseDouble(savedString));
            }else{
                goalObject.setSavedAmount(0.0);
            }
            goalObject.setInflationPercent(Double.parseDouble(inflationString));
            goalObject.setReturnsPercent(Double.parseDouble(expectedReturnString));
            goalObject.setDuration(Double.parseDouble(timePeriodString));
            goalFragmentListener.calculate(goalObject);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Fill All fields",
                    "OK").create().show();
        }
    }
}