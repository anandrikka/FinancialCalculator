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

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class SIPReturnsTab extends Fragment {

    private EditText monthlyInvestmentText, timePeriodText, annualReturnText;
    private Spinner timePeriodSelect;
    private Button resetButton, calculateButton;
    private TextView totalReturnsText, investmentText, wealthGainsText;
    TableLayout results;

    /*
    * P = A = P * [{(1+i)^n – 1 }/i] * (1+i)
    * PMT - yearly investment
    * r - rate of interest
    * n - total installments
    *
    * */

    public SIPReturnsTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sip_returns_tab, container, false);
        setRetainInstance(true);
        initialLoad(view);
        calculate(true);
        return view;
    }

    private void initialLoad(View view){
        results = (TableLayout) view.findViewById(R.id.results);
        results.setVisibility(View.GONE);
        monthlyInvestmentText = (EditText) view.findViewById(R.id.monthly_installment);
        timePeriodText = (EditText) view.findViewById(R.id.investment_time);
        annualReturnText = (EditText) view.findViewById(R.id.expected_return);
        timePeriodSelect = (Spinner) view.findViewById(R.id.time_period_unit);
        timePeriodSelect.setSelection(1);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_dropdown, MutualFundActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        timePeriodSelect.setAdapter(periodUnitAdapter);
        totalReturnsText = (TextView) view.findViewById(R.id.total_returns);
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
                monthlyInvestmentText.setText("");
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
        if(!monthlyInvestmentText.getText().toString().equals("") &&
                !timePeriodText.getText().toString().equals("") &&
                !annualReturnText.getText().toString().equals("")){
            double P, n, i, M, Investment;
            P = Double.parseDouble(monthlyInvestmentText.getText().toString());
            n = Double.parseDouble(timePeriodText.getText().toString());
            if(timePeriodSelect.getSelectedItem().equals(MutualFundActivity.PERIOD[1])){
                n = n*12.0;
            }
            i = Double.parseDouble(annualReturnText.getText().toString());
            i = i/12.0;
            i = i/100.0;
            double x = 1+i;
            x = Math.pow(x, n);
            x = x-1;
            x = x/i;
            x = P*x;
            double y = 1+i;
            x = x*y;
            Investment = P*n;
            totalReturnsText.setText(Math.round(x)+"");
            investmentText.setText(Math.round(Investment)+"");
            wealthGainsText.setText(Math.round(x-Investment)+"");
            results.setVisibility(View.VISIBLE);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

}
