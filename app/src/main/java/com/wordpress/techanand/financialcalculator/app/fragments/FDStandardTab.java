package com.wordpress.techanand.financialcalculator.app.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FDStandardTab extends Fragment {

    private double P,t,r,n,A;
    private String tUnit, nUnit;
    private EditText fdAmount, period, roi;
    private Spinner periodUnitSpinner, compoundingFreq;
    private AlertDialog.Builder builder;
    PieChart pieChart;
    TableLayout resultsTable;
    Button calculate, reset;

    public FDStandardTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fd_standard_tab, container, false);
        setRetainInstance(true);

        initializeData(view);

        periodUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tUnit = FixedDepositActivity.PERIOD[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        compoundingFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nUnit = FixedDepositActivity.COMPOUNDING_FREQ[position];
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
                pieChart.clear();
                resultsTable.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //table.setFocusable(false);
                calculateFD(false);

            }
        });

        calculateFD(true);

        return view;
    }

    public void initializeData(View view){
        pieChart = (PieChart) view.findViewById(R.id.chart);
        pieChart.setVisibility(View.GONE);

        resultsTable = (TableLayout) view.findViewById(R.id.resultsTable);
        resultsTable.setVisibility(View.GONE);

        fdAmount = (EditText) view.findViewById(R.id.fd_amount);
        period = (EditText) view.findViewById(R.id.period_value);
        roi = (EditText) view.findViewById(R.id.roi);

        periodUnitSpinner = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSpinner.setAdapter(periodUnitAdapter);

        compoundingFreq = (Spinner) view.findViewById(R.id.compounding_interest_input);
        ArrayAdapter<String> compoundingFreqAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.COMPOUNDING_FREQ);
        compoundingFreqAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        compoundingFreq.setAdapter(compoundingFreqAdapter);
        compoundingFreq.setSelection(1);

        reset = (Button) view.findViewById(R.id.reset_data);
        calculate = (Button) view.findViewById(R.id.calculate);

    }

    public void calculateFD(boolean isFromInitialLoad){
        AppMain.hideKeyboard(getActivity(), calculate);
        String fdText = fdAmount.getText().toString();
        String roiText = roi.getText().toString();
        String periodText = period.getText().toString();
        if((fdText != null && fdText.length() > 0) &&
                (roiText != null && roiText.length() > 0) &&
                (periodText !=null && periodText.length() > 0)){
            P = Double.parseDouble(fdAmount.getText().toString());
            r = Double.parseDouble(roi.getText().toString())/100;
            t = Double.parseDouble(period.getText().toString());
            if(tUnit == FixedDepositActivity.PERIOD[0]){

            }else if(tUnit == FixedDepositActivity.PERIOD[1]){
                t = t/12;
            }else {
                t = t/365;
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

            A = P * (Math.pow((1+r/n), (n*t)));
            ((TextView)getView().findViewById(R.id.maturity_amount)).setText(Math.round(A)+"");
            ((TextView)getView().findViewById(R.id.interest)).setText(Math.round(A-P)+"");

            createPieChart();

            resultsTable.setVisibility(View.VISIBLE);

        } else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    private void createPieChart(){
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float)P, 0));
        entries.add(new Entry((float)(A-P), 1));

        PieDataSet dataset = new PieDataSet(entries, "");
        ArrayList<String> labels = new ArrayList<String>();

        labels.add("Investment");
        labels.add("Interest");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(data);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setDescription("");
    }

}
