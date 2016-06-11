package com.wordpress.techanand.financialcalculator.app.fragments;

import android.content.Context;
import android.net.Uri;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.RecurringDepositActivity;

import java.util.ArrayList;

public class RDByMaturityTab extends Fragment {

    /*
    *   M = ( R * [(1+r)^n - 1 ] ) / (1-(1+r)^-1/3)
    *
    *   M = Maturity value
    *   R = Monthly Installment
    *   r = Rate of Interest (i) / 400
    *   n = Number of Quarters
    * */

    private EditText maturityAmountText, periodText, roiText;
    private Spinner periodUnitSelect;
    private Button resetButton, calculateButton;
    private TableLayout results;
    private TextView interestEarnedText, installmentText, totalInvestmentText;
    private PieChart pieChart;

    public RDByMaturityTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rd_maturity_tab, container, false);
        setRetainInstance(true);

        initializeData(view);

        return view;
    }

    public void initializeData(View view){
        pieChart = (PieChart) view.findViewById(R.id.chart);
        pieChart.setVisibility(View.GONE);
        maturityAmountText = (EditText) view.findViewById(R.id.maturity_amount);
        periodText = (EditText) view.findViewById(R.id.period);
        periodUnitSelect = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, RecurringDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSelect.setAdapter(periodUnitAdapter);
        periodUnitSelect.setSelection(1);
        roiText = (EditText) view.findViewById(R.id.roi);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        results = (TableLayout) view.findViewById(R.id.results);
        interestEarnedText = (TextView) view.findViewById(R.id.interest);
        installmentText = (TextView) view.findViewById(R.id.monthly_installment);
        totalInvestmentText = (TextView) view.findViewById(R.id.total_investment);
        results.setVisibility(View.GONE);

        calculate(true);
        periodUnitSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periodUnitSelect.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maturityAmountText.setText("");
                periodText.setText("");
                periodUnitSelect.setSelection(1);
                roiText.setText("");
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });
    }

    private void calculate(boolean isFromInitialLoad){
        double R, r, period=0, M, interestEarned, n, P;
        if(!maturityAmountText.getText().toString().equals("") &&
                !roiText.getText().toString().equals("") &&
                !periodText.getText().toString().equals("")){
            R = Double.parseDouble(maturityAmountText.getText().toString());
            r = Double.parseDouble(roiText.getText().toString());
            period = Double.parseDouble(periodText.getText().toString());

            String selectedPeriod = (String)periodUnitSelect.getSelectedItem();
            if(selectedPeriod.equals(RecurringDepositActivity.PERIOD[0])){
                if(period%3 == 0){
                    n = period/3;
                    n = period;
                }else{
                    AppMain.dialogBuilder(getContext(),
                            "Error",
                            "Term should be multiples of 3 !",
                            "OK").create().show();
                    return;
                }
            }else{
                n = period * 4;
            }
            r = r/400;
            double x = 1+r;
            double y1 = 1.0/3.0;
            x = Math.pow(x, y1);
            x = 1/x;
            x = 1-x;
            x = R*x;
            double y = 1+r;
            y = Math.pow(y, n);
            y = y-1;
            M = x/y;
            M = Math.round(M);
            P = M*n*3;
            interestEarned = R-P;
            installmentText.setText(Math.round(M)+"");
            totalInvestmentText.setText(P+"");
            interestEarnedText.setText(Math.round(interestEarned)+"");
            results.setVisibility(View.VISIBLE);
            createPieChart((float)P, (float)interestEarned);
            pieChart.setVisibility(View.VISIBLE);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    private void createPieChart(float P, float I){
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(P, 0));
        entries.add(new Entry(I, 1));

        PieDataSet dataset = new PieDataSet(entries, "");
        ArrayList<String> labels = new ArrayList<String>();

        labels.add("Investment");
        labels.add("Interest");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);

        pieChart.setData(data);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setDescription("");
    }
}
