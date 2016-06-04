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
import android.widget.TableLayout;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.MutualFundActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SIPCalculateTab extends Fragment {

    private EditText targetAmountText, timePeriodText, annualReturnText;
    private Spinner timePeriodSelect;
    private Button resetButton, calculateButton;
    private TextView monthlySIPText, investmentText, wealthGainsText;
    TableLayout results;

    public SIPCalculateTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sip_calculate_tab, container, false);
        setRetainInstance(true);
        initialLoad(view);
        calculate(true);
        return view;
    }

    private void initialLoad(View view){
        results = (TableLayout) view.findViewById(R.id.results);
        results.setVisibility(View.GONE);
        targetAmountText = (EditText) view.findViewById(R.id.target_amount);
        timePeriodText = (EditText) view.findViewById(R.id.investment_time);
        annualReturnText = (EditText) view.findViewById(R.id.expected_return);
        timePeriodSelect = (Spinner) view.findViewById(R.id.time_period_unit);
        timePeriodSelect.setSelection(1);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_dropdown, MutualFundActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        timePeriodSelect.setAdapter(periodUnitAdapter);
        monthlySIPText = (TextView) view.findViewById(R.id.monthly_sip);
        investmentText = (TextView) view.findViewById(R.id.investment);
        wealthGainsText = (TextView) view.findViewById(R.id.gain);
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
                targetAmountText.setText("");
                timePeriodText.setText("");
                annualReturnText.setText("");
                timePeriodSelect.setSelection(1);
                results.setVisibility(View.GONE);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });
    }

    public void calculate(boolean isFromInitialLoad){
        if(!targetAmountText.getText().toString().equals("") &&
                !timePeriodText.getText().toString().equals("") &&
                !annualReturnText.getText().toString().equals("")){
            double M, n, i, Investment, P;
            M = Double.parseDouble(targetAmountText.getText().toString());
            n = Double.parseDouble(timePeriodText.getText().toString());
            if(timePeriodSelect.getSelectedItem().equals(MutualFundActivity.PERIOD[1])){
                n = n*12.0;
            }
            i = Double.parseDouble(annualReturnText.getText().toString());
            i = i/12.0;
            i = i/100.0;
            double x = M*i;
            double y = 1+i;
            y = Math.pow(y, n);
            y = y - 1;
            double y1 = 1+i;
            y = y*y1;
            P = x/y;
            Investment = P*n;
            monthlySIPText.setText(Math.round(P)+"");
            investmentText.setText(Math.round(Investment)+"");
            wealthGainsText.setText(Math.round(M-Investment)+"");
            results.setVisibility(View.VISIBLE);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

}
