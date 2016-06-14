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
import android.widget.SeekBar;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.models.RetirementObject;

/**
 * Created by Anand Rikka on 06/12/2016
 */
public class RetirementFragment extends Fragment {

    public interface RetirementFragmentListener {
        public void calculate(RetirementObject retirementData);
        public void reset();
    }

    private RetirementFragmentListener retirementFragmentListener;
    private RetirementObject retirementData;

    private EditText currentAgeText, retirementAgeText, lifeExpectancyText,
            monthlyExpText, inflationText, existingInvestmentText, expectedReturnsText;
    private Button resetButton, calculateButton;

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
        setRetainInstance(true);
        currentAgeText = (EditText) view.findViewById(R.id.current_age);
        retirementAgeText = (EditText) view.findViewById(R.id.retirement_age);
        lifeExpectancyText = (EditText) view.findViewById(R.id.life_expentency);
        monthlyExpText = (EditText) view.findViewById(R.id.monthly_expenses);
        inflationText = (EditText) view.findViewById(R.id.inflation_rate);
        existingInvestmentText = (EditText) view.findViewById(R.id.existing_investment);
        expectedReturnsText = (EditText) view.findViewById(R.id.expected_return);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);

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
                retirementFragmentListener.reset();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflationText.setError("Test error");
                calculate(false);
            }
        });

        /*SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                *//*int stepSize = 5;
                progress = ((int)Math.round(progress/stepSize))*stepSize;
                seekBar.setProgress(progress);*//*
                lifeExpectancyText.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

        calculate(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        retirementFragmentListener = (RetirementFragmentListener)context;
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

            retirementFragmentListener.calculate(retirementData);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }


}
