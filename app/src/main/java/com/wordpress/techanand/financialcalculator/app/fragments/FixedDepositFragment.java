package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 6/6/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;
import com.wordpress.techanand.financialcalculator.app.models.FixedDepositObject;

import java.util.ArrayList;
import java.util.List;

public class FixedDepositFragment extends Fragment {

    public static final String ID = FixedDepositFragment.class.getName();

    private FixedDepositObject fixedDepositObject;
    private boolean isCalcClicked;

    private EditText fdAmount, period, roi;
    private Spinner periodUnitSpinner, compoundingFreq, payoutSpinner;
    private AlertDialog.Builder builder;
    private Button calculate, reset;
    private CheckBox standard, interestPayout;
    private TableLayout resultTable;
    private PieChart pieChart;

    public FixedDepositFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixedDepositObject = new FixedDepositObject();
        Activity activity = this.getActivity();
        Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Fixed Deposit 123");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fixed_deposit_fragment, container, false);
        //setRetainInstance(true);
        initializeData(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*try{
            fixedDepositListener = (FixedDepositListener)context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString() + "must implement FixedDepositListener");
        }*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        standard.setChecked(standard.isChecked());
        interestPayout.setChecked(interestPayout.isChecked());
        fdAmount.setText(fdAmount.getText().toString());
        period.setText(period.getText().toString());
        roi.setText(roi.getText().toString());
    }

    public void initializeData(View view){
        fdAmount = (EditText) view.findViewById(R.id.fd_amount);
        period = (EditText) view.findViewById(R.id.period_value);
        roi = (EditText) view.findViewById(R.id.roi);

        standard = (CheckBox) view.findViewById(R.id.by_standard);
        interestPayout = (CheckBox) view.findViewById(R.id.by_interest_payout);

        periodUnitSpinner = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSpinner.setAdapter(periodUnitAdapter);

        compoundingFreq = (Spinner) view.findViewById(R.id.compounding_interest_input);
        ArrayAdapter<String> compoundingFreqAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.COMPOUNDING_FREQ);
        compoundingFreqAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        compoundingFreq.setAdapter(compoundingFreqAdapter);
        compoundingFreq.setSelection(1);

        payoutSpinner = (Spinner) view.findViewById(R.id.payout_input);
        ArrayAdapter<String> payoutAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PAYOUT_FREQ);
        payoutAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        payoutSpinner.setAdapter(payoutAdapter);

        reset = (Button) view.findViewById(R.id.reset);
        calculate = (Button) view.findViewById(R.id.calculate);

        resultTable = (TableLayout) view.findViewById(R.id.resultsTable);
        resultTable.setVisibility(View.GONE);

        pieChart = (PieChart) view.findViewById(R.id.chart);
        pieChart.setVisibility(View.GONE);

        if(standard.isChecked()){
            ((TableRow)view.findViewById(R.id.interest_payout_row)).setVisibility(View.GONE);
        }

        periodUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixedDepositObject.setTimeUnit(FixedDepositActivity.PERIOD[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        compoundingFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixedDepositObject.setCompoundingUnit(FixedDepositActivity.COMPOUNDING_FREQ[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        payoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixedDepositObject.setPayoutUnit(FixedDepositActivity.PAYOUT_FREQ[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdAmount.setText(null);
                period.setText(null);
                roi.setText(null);
                periodUnitSpinner.setSelection(0);
                compoundingFreq.setSelection(1);
                isCalcClicked = false;
                resultTable.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFD(false, true);
            }
        });

        calculateFD(true, false);

    }

    public void calculateFD(boolean isFromInitialLoad, boolean isCalcClicked){
        this.isCalcClicked = isCalcClicked;
        if(!this.isCalcClicked){
            return;
        }
        AppMain.hideKeyboard(getActivity(), calculate);
        String fdText = fdAmount.getText().toString();
        String roiText = roi.getText().toString();
        String periodText = period.getText().toString();
        if((fdText != null && fdText.length() > 0) &&
                (roiText != null && roiText.length() > 0) &&
                (periodText !=null && periodText.length() > 0)){

            fixedDepositObject.setFdAmount(Double.parseDouble(fdText));
            fixedDepositObject.setTime(Double.parseDouble(periodText));
            fixedDepositObject.setRoi(Double.parseDouble(roiText));
            fixedDepositObject.setTimeUnit((String)periodUnitSpinner.getSelectedItem());
            fixedDepositObject.setCompoundingUnit((String)compoundingFreq.getSelectedItem());
            fixedDepositObject.setIsStandardFD(((CheckBox)getView().findViewById(R.id.by_standard)).isChecked());
            fixedDepositObject.setIsInterestPayoutFD(((CheckBox)getView().findViewById(R.id.by_interest_payout)).isChecked());

            calculate(fixedDepositObject);
            //fixedDepositListener.calculate(fixedDepositObject);

        } else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    public void calculate(FixedDepositObject fixedDepositObject) {
        /*Formula for fixed deposit:
        *
        * A = P * (1+r/n)^nt
        *
        * P - Principle Amount
        * r - Rate of Interest
        * t - Number of Years
        * n - No' of compounding periods, example: for quarterly (4), for half yearly (2) etc..
        *
        * */
        double P, r, t, n, A;
        String tUnit, nUnit;
        P = fixedDepositObject.getFdAmount();
        r = fixedDepositObject.getRoi() * 0.01;
        t = fixedDepositObject.getTime();
        tUnit = fixedDepositObject.getTimeUnit();
        nUnit = fixedDepositObject.getCompoundingUnit();
        if(tUnit == FixedDepositActivity.PERIOD[0]){

        }else if(tUnit == FixedDepositActivity.PERIOD[1]){
            t = t/12.0;
        }else {
            t = t/365.0;
        }
        if(fixedDepositObject.isInterestPayoutFD()){
            if(fixedDepositObject.getPayoutUnit().equals(FixedDepositActivity.PAYOUT_FREQ[0])){
                t = 1.0/12.0;
            }else{
                t = 3.0/12.0;
            }
        }
        if(nUnit == FixedDepositActivity.COMPOUNDING_FREQ[0]){
            n = 12;
        }else if(nUnit == FixedDepositActivity.COMPOUNDING_FREQ[1]){
            n = 4;
        }else if(nUnit == FixedDepositActivity.COMPOUNDING_FREQ[2]){
            n = 2;
        }else {
            n = 1;
        }
        //A = P * (Math.pow((1+r/n), (n*t)));
        A = P * (Math.pow((1+r/n), (n*t)));
        if(fixedDepositObject.isStandardFD()){
            fixedDepositObject.setMaturityAmount(A);
            fixedDepositObject.setInterest(A-P);
        }
        if(fixedDepositObject.isInterestPayoutFD()){
            double totalMonths, payout;
            payout = A-P;
            fixedDepositObject.setPayoutAmount(payout);
            if(tUnit.equals(FixedDepositActivity.PERIOD[0])){
                totalMonths = fixedDepositObject.getTime() * 12;
            }else if(tUnit.equals(FixedDepositActivity.PERIOD[1])){
                totalMonths = fixedDepositObject.getTime();
            }else {
                totalMonths = fixedDepositObject.getTime()/30.0;
            }
            if(fixedDepositObject.getPayoutUnit().equals(FixedDepositActivity.PAYOUT_FREQ[0])){
                fixedDepositObject.setInterest(payout * totalMonths);
                if(totalMonths < 1){
                    fixedDepositObject.setPayoutAmount(fixedDepositObject.getInterest());
                }
            }else{
                double quarters = totalMonths/3.0;
                if(quarters < 1){
                    fixedDepositObject.setPayoutAmount(fixedDepositObject.getInterest());
                }
                fixedDepositObject.setInterest(payout * quarters);
            }

        }
        displayResult(fixedDepositObject);
        resultTable.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.VISIBLE);
    }

    public void displayResult(FixedDepositObject fixedDepositObject){
        pieChart.clear();
        if(fixedDepositObject.isStandardFD()){
            ((TextView)getView().findViewById(R.id.result_amount_label)).setText("Maturity Amount");
            ((TextView)getView().findViewById(R.id.result_amount)).setText(MainPrefs.getFormattedNumber(fixedDepositObject.getMaturityAmount()));
        }
        if(fixedDepositObject.isInterestPayoutFD()){
            if(fixedDepositObject.getPayoutUnit().equals(FixedDepositActivity.PAYOUT_FREQ[0])){
                ((TextView)getView().findViewById(R.id.result_amount_label)).setText("Monthly Payout");
            }else{
                ((TextView)getView().findViewById(R.id.result_amount_label)).setText("Quarterly Payout");
            }

            ((TextView)getView().findViewById(R.id.result_amount)).setText(MainPrefs.getFormattedNumber(fixedDepositObject.getPayoutAmount()));
        }
        ((TextView)getView().findViewById(R.id.interest_earned)).setText(MainPrefs.getFormattedNumber(fixedDepositObject.getInterest()));
        List entries = new ArrayList();
        entries.add(new Entry((float)fixedDepositObject.getFdAmount(), 0));
        entries.add(new Entry((float)fixedDepositObject.getInterest(), 1));
        List labels = new ArrayList();
        labels.add("Investment");
        labels.add("Interest");
        pieChart = PieChartConfig.createPieChart(getActivity(), pieChart, entries, labels, "");
    }

}
