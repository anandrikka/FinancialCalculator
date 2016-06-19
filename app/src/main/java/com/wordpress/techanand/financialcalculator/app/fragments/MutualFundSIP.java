package com.wordpress.techanand.financialcalculator.app.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.MutualFundActivity;
import com.wordpress.techanand.financialcalculator.app.models.MutualFundObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MutualFundSIP extends Fragment {

    public interface MutualFundSIPListener {
        public void resetListener();
        public void calculateListener(MutualFundObject mutualFundObjectData, boolean isFromInitialLoad);
    }

    private MutualFundSIPListener mutualFundSIPListener;
    private EditText monthlyInvestmentText, timePeriodText, annualReturnText;
    private Spinner timePeriodSelect;
    private Button resetButton, calculateButton;
    private CheckBox bySip, byTarget;

    private MutualFundObject mutualFundObjectData;

    private boolean isCalcClicked;

    public MutualFundSIP() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mutualFundObjectData = new MutualFundObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mutual_funds_sip, container, false);
        setRetainInstance(true);
        initialLoad(view);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        bySip.setChecked(bySip.isChecked());
        byTarget.setChecked(byTarget.isChecked());
        monthlyInvestmentText.setText(monthlyInvestmentText.getText().toString());
        timePeriodText.setText(timePeriodText.getText().toString());
        annualReturnText.setText(annualReturnText.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mutualFundSIPListener = (MutualFundSIPListener) context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString() + " must implement MutualFundSIPListener");
        }
    }

    private void initialLoad(View view){
        monthlyInvestmentText = (EditText) view.findViewById(R.id.monthly_installment);
        timePeriodText = (EditText) view.findViewById(R.id.investment_time);
        annualReturnText = (EditText) view.findViewById(R.id.expected_return);
        timePeriodSelect = (Spinner) view.findViewById(R.id.time_period_unit);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_dropdown, MutualFundActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        timePeriodSelect.setAdapter(periodUnitAdapter);
        timePeriodSelect.setSelection(1);
        bySip = (CheckBox) view.findViewById(R.id.by_sip);
        byTarget = (CheckBox) view.findViewById(R.id.by_target);
        timePeriodSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timePeriodSelect.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthlyInvestmentText.setText("");
                timePeriodText.setText("");
                annualReturnText.setText("");
                timePeriodSelect.setSelection(1);
                mutualFundSIPListener.resetListener();
                isCalcClicked = false;

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false, true);
            }
        });
        calculate(true, false);
    }

    public void calculate(boolean isFromInitialLoad, boolean isCalcClicked){
        this.isCalcClicked = isCalcClicked;
        if(!this.isCalcClicked){
            return;
        }
        if(!monthlyInvestmentText.getText().toString().equals("") &&
                !timePeriodText.getText().toString().equals("") &&
                !annualReturnText.getText().toString().equals("")){
            mutualFundObjectData.setAmount(Double.parseDouble(monthlyInvestmentText.getText().toString()));
            mutualFundObjectData.setTotalPeriod(Double.parseDouble(timePeriodText.getText().toString()));
            mutualFundObjectData.setAnnualReturns(Double.parseDouble(annualReturnText.getText().toString()));
            mutualFundObjectData.setIsMonthlySIP(((CheckBox) getView().findViewById(R.id.by_sip)).isChecked());
            mutualFundObjectData.setIsTargetAmount(((CheckBox) getView().findViewById(R.id.by_target)).isChecked());
            mutualFundObjectData.setPeriodUnit((String)timePeriodSelect.getSelectedItem());
            mutualFundSIPListener.calculateListener(mutualFundObjectData, isFromInitialLoad);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

}
