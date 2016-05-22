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
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FDStandardTab extends Fragment {

    private double P;
    private double t;
    private double r;
    private int n;
    private double A;
    private String tUnit;
    private String nUnit;

    private EditText fdAmount;
    private EditText period;
    private EditText roi;
    private Spinner periodUnitSpinner;
    private Spinner compoundingFreq;
    private AlertDialog.Builder builder;
    TextView maturityAmount;
    TableRow row;
    PieChart pieChart;

    public FDStandardTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fd_standard_tab, container, false);

        pieChart = (PieChart) view.findViewById(R.id.chart);
        pieChart.setVisibility(View.INVISIBLE);

        final TableLayout table = (TableLayout) view.findViewById(R.id.table);
        row = (TableRow) table.findViewById(R.id.maturity_row);
        row.setVisibility(View.INVISIBLE);

        maturityAmount = (TextView) table.findViewById(R.id.maturity_amount);

        /*final TableRow maturityRow = new TableRow(view.getContext());
        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        maturityRow.setPadding(0, 16, 0, 8);
        maturityRow.setLayoutParams(layoutParams);

        TextView maturityLabel = new TextView(view.getContext());
        TableRow.LayoutParams maturityLabelParams = new TableRow.LayoutParams();
        maturityLabelParams.weight = 1;
        maturityLabelParams.width = 0;
        maturityLabelParams.height = TableRow.LayoutParams.WRAP_CONTENT;
        maturityLabel.setLayoutParams(maturityLabelParams);
        maturityLabel.setText("Maturity Amount");
        maturityLabel.setTextSize(20);
        maturityRow.addView(maturityLabel);

        final TextView maturityAmount = new TextView(view.getContext());
        TableRow.LayoutParams maturityAmountParams = new TableRow.LayoutParams();
        maturityAmountParams.weight = 1;
        maturityAmountParams.width = 0;
        maturityAmountParams.height = TableRow.LayoutParams.WRAP_CONTENT;
        maturityLabel.setLayoutParams(maturityAmountParams);
        maturityLabel.setTextSize(20);
        maturityRow.addView(maturityAmount);

        table.addView(maturityRow);
        maturityRow.setVisibility(View.INVISIBLE);*/

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

        Button reset = (Button) view.findViewById(R.id.reset_data);
        Button calculate = (Button) view.findViewById(R.id.calculate);

        calculateFD(true);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdAmount.setText(null);
                period.setText(null);
                roi.setText(null);
                periodUnitSpinner.setSelection(0);
                compoundingFreq.setSelection(1);
                row.setVisibility(View.INVISIBLE);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.setFocusable(false);
                calculateFD(false);

            }
        });

        builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Error");
        builder.setMessage("Fill all fields !");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return view;

    }

    public void calculateFD(boolean isFromInitialLoad){
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
            maturityAmount.setText(Math.round(A)+"");
            row.setVisibility(View.VISIBLE);

            ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry((float)P, 0));
            entries.add(new Entry((float)(A-P), 1));

            PieDataSet dataset = new PieDataSet(entries, "");
            ArrayList<String> labels = new ArrayList<String>();

            labels.add("Investment");
            labels.add("Interest");

            PieData data = new PieData(labels, dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart.clear();
            pieChart.setData(data);
            pieChart.setVisibility(View.VISIBLE);
            pieChart.setDescription("FD Details");
        } else if(!isFromInitialLoad){
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
