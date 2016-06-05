package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FDInterestPayoutTab extends Fragment {

    private EditText FDAmountText, interestText, timePeriodText;
    private Spinner periodUnitSelect, compoundingUnitSelect, interestPayoutSelect;
    private Button resetButton, calculateButton;


    public FDInterestPayoutTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fd_interest_payout, container, false);
        setRetainInstance(true);

        initializeData(view);

        periodUnitSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), FixedDepositActivity.PERIOD[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        compoundingUnitSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), FixedDepositActivity.COMPOUNDING_FREQ[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        interestPayoutSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getContext(), FixedDepositActivity.PAYOUT_FREQ[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FDAmountText.setText("");
                interestText.setText("");
                timePeriodText.setText("");
                periodUnitSelect.setSelection(0);
                compoundingUnitSelect.setSelection(1);
                interestPayoutSelect.setSelection(0);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });

        calculate(true);

        return view;
    }

    private void initializeData(View view){
        FDAmountText = (EditText) view.findViewById(R.id.fd_amount);
        interestText = (EditText) view.findViewById(R.id.roi);
        timePeriodText = (EditText) view.findViewById(R.id.period_value);
        periodUnitSelect = (Spinner) view.findViewById(R.id.period_unit);
        compoundingUnitSelect = (Spinner) view.findViewById(R.id.compounding_interest_input);
        interestPayoutSelect = (Spinner) view.findViewById(R.id.payout_input);

        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSelect.setAdapter(periodUnitAdapter);

        ArrayAdapter<String> compoundingFreqAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.COMPOUNDING_FREQ);
        compoundingFreqAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        compoundingUnitSelect.setAdapter(compoundingFreqAdapter);
        compoundingUnitSelect.setSelection(1);

        ArrayAdapter<String> interestPayoutAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PAYOUT_FREQ);
        interestPayoutAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        interestPayoutSelect.setAdapter(interestPayoutAdapter);

        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);

    }

    private void calculate(boolean isFromInitialLoad){
        String FDString, periodString, interestString,
                periodUnitString, compoundingString, interestPayoutString;

        FDString = FDAmountText.getText().toString();
        periodString = timePeriodText.getText().toString();
        interestString = interestText.getText().toString();
        periodUnitString = (String)periodUnitSelect.getSelectedItem();
        compoundingString = (String)compoundingUnitSelect.getSelectedItem();
        interestPayoutString = (String) interestPayoutSelect.getSelectedItem();
        double FDValue, totalPeriod, interest;
        double A, P, n, t, r, interestPerPayout, totalInterest;

        if(!FDString.equals("") && !periodString.equals("") && !interestString.equals("")){
            FDValue = Double.parseDouble(FDString);
            totalPeriod = Double.parseDouble(periodString);
            interest = Double.parseDouble(interestString);
            P = FDValue;
            r = interest/100.0;
            if(compoundingString == FixedDepositActivity.COMPOUNDING_FREQ[0]){
                n = 12;
            }else if(compoundingString == FixedDepositActivity.COMPOUNDING_FREQ[1]){
                n = 4;
            }else if(compoundingString == FixedDepositActivity.COMPOUNDING_FREQ[2]){
                n = 2;
            }else {
                n = 1;
            }
            if(interestPayoutString.equals(FixedDepositActivity.PAYOUT_FREQ[0])){
                t = 1.0/12.0;
            }else{
                t = 3.0/12.0;
            }
            A = P * (Math.pow((1+r/n), (n*t)));
            interestPerPayout = A-P;
            double totalMonths;
            if(periodUnitString.equals(FixedDepositActivity.PERIOD[0])){
                totalMonths = totalPeriod * 12;
            }else if(periodUnitString.equals(FixedDepositActivity.PERIOD[1])){
                totalMonths = totalPeriod;
            }else {
                totalMonths = totalPeriod/30.0;
            }

            if(interestPayoutString.equals(FixedDepositActivity.PAYOUT_FREQ[0])){
                totalInterest = interestPerPayout * totalMonths;
            }else{
                double quarters = totalMonths/3.0;
                totalInterest = interestPerPayout * quarters;
            }
            AppMain.debug("Total Interest: "+totalInterest);

        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }

    }

}
