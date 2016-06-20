package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 06/12/2016
 */

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

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.models.RetirementObject;


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

        seekBarListeners(view);

        calculate(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        retirementFragmentListener = (RetirementFragmentListener)context;
    }

    private void seekBarListeners(View view){
        SeekBar currentAgeSeekbar = (SeekBar) view.findViewById(R.id.current_age_seekbar);
        currentAgeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int min = 18;
                seekBar.setProgress(progress);
                currentAgeText.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar retirementAge = (SeekBar) view.findViewById(R.id.retirement_age_seekbar);
        retirementAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int min = 18;
                seekBar.setProgress(progress);
                retirementAgeText.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar lifeExpectancy = (SeekBar) view.findViewById(R.id.life_expentency_seekbar);
        lifeExpectancy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int min = 18;
                seekBar.setProgress(progress);
                lifeExpectancyText.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar presentExpenses = (SeekBar) view.findViewById(R.id.monthly_expenses_seekbar);
        presentExpenses.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int stepSize = 1000;
                progress = ((int)Math.round(progress/stepSize))*stepSize;
                seekBar.setProgress(progress);
                monthlyExpText.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar inflation = (SeekBar) view.findViewById(R.id.inflation_seekbar);
        inflation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int min = 18;
                seekBar.setProgress(progress);
                inflationText.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar existingInvestment = (SeekBar) view.findViewById(R.id.existing_investment_seekbar);
        existingInvestment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int min = 18;
                seekBar.setProgress(progress);
                existingInvestmentText.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SeekBar expectedReturns = (SeekBar) view.findViewById(R.id.expected_return_seekbar);
        expectedReturns.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int min = 18;
                seekBar.setProgress(progress);
                expectedReturnsText.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
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
