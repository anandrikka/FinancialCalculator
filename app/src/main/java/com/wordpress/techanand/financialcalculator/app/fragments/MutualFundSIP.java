package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 6/6/2016
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.MutualFundActivity;
import com.wordpress.techanand.financialcalculator.app.models.MutualFundObject;

public class MutualFundSIP extends Fragment {

    public static final String ID = MutualFundSIP.class.getName();

    private EditText monthlyInvestmentText, timePeriodText, annualReturnText;
    private Spinner timePeriodSelect;
    private Button resetButton, calculateButton;
    private CheckBox bySip, byTarget;
    private TableLayout resultsTable;
    private PieChart pieChart;

    private MutualFundObject mutualFundObjectData;

    private boolean isCalcClicked;

    public MutualFundSIP() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mutualFundObjectData = new MutualFundObject();
        Toolbar appBarLayout = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Mutual Fund Calculator");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mutual_funds_sip, container, false);
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

        resultsTable = (TableLayout) view.findViewById(R.id.results);
        resultsTable.setVisibility(View.GONE);

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
                isCalcClicked = false;
                resultsTable.setVisibility(View.GONE);
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
            calculateResult(mutualFundObjectData, isFromInitialLoad);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    public void calculateResult(MutualFundObject mutualFundObjectData, boolean isFromInitialLoad) {
        /*
        * P = A = P * [{(1+i)^n – 1 }/i] * (1+i)
        * PMT - Per Month
        * r - rate of interest
        * n - total installments
        *
        * */

        double P, n, i;

        P = mutualFundObjectData.getAmount();
        if(mutualFundObjectData.getPeriodUnit().equals(MutualFundActivity.PERIOD[1])){
            n = mutualFundObjectData.getTotalPeriod() * 12.0;
        }else{
            n = mutualFundObjectData.getTotalPeriod();
        }
        i = mutualFundObjectData.getAnnualReturns();
        i = i/12.0;
        i = i * 0.01;

        if(mutualFundObjectData.isMonthlySIP()){
            double x, y;
            x = 1+i;
            x = Math.pow(x, n);
            x = x-1;
            x = x/i;
            x = P*x;
            y = 1+i;
            x = x*y;
            mutualFundObjectData.setTotalInvestment(P*n);
            mutualFundObjectData.setTotalReturns(x);
            mutualFundObjectData.setWealthGained(x - (P*n));
        }

        if(mutualFundObjectData.isTargetAmount()){
            double x, y;
            x = P*i;
            y = 1+i;
            y = Math.pow(y, n);
            y = y - 1;
            double y1 = 1+i;
            y = y*y1;
            double sip = x/y;
            mutualFundObjectData.setTotalInvestment(sip*n);
            mutualFundObjectData.setMonthlySIP(sip);
            mutualFundObjectData.setWealthGained(mutualFundObjectData.getAmount() - (sip*n));
        }
        displayResult(mutualFundObjectData);
    }

    public void displayResult(MutualFundObject mutualFundObjectData){
        if(mutualFundObjectData.isTargetAmount()){
            ((TextView)getView().findViewById(R.id.returns_or_sip)).setText("Monthly SIP");
            ((TextView)getView().findViewById(R.id.total_returns)).setText(MainPrefs.getFormattedNumber(mutualFundObjectData.getMonthlySIP()));
        }

        if(mutualFundObjectData.isMonthlySIP()){
            ((TextView)getView().findViewById(R.id.returns_or_sip)).setText("Total Returns");
            ((TextView)getView().findViewById(R.id.total_returns)).setText(MainPrefs.getFormattedNumber(mutualFundObjectData.getTotalReturns()));
        }

        ((TextView)getView().findViewById(R.id.investment)).setText(MainPrefs.getFormattedNumber(mutualFundObjectData.getTotalInvestment()));
        ((TextView)getView().findViewById(R.id.gain)).setText(MainPrefs.getFormattedNumber(mutualFundObjectData.getWealthGained()));

        resultsTable.setVisibility(View.VISIBLE);
    }

}
