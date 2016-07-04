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
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.activities.RecurringDepositActivity;
import com.wordpress.techanand.financialcalculator.app.models.RecurringDepositObject;

import java.util.ArrayList;
import java.util.List;

public class RecurringDepositFragment extends Fragment {

    public static final String ID = RecurringDepositFragment.class.getName();

    private EditText installmentOrMaturityText, periodText, roiText;
    private Spinner periodUnitSelect;
    private Button resetButton, calculateButton;
    private CheckBox byMonthly, byTarget;
    private TableLayout resultsTable;
    private PieChart pieChart;

    private RecurringDepositObject recurringDepositObject;

    private boolean isCalcClicked;

    public RecurringDepositFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recurringDepositObject = new RecurringDepositObject();
        Toolbar appBarLayout = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Recurring Deposit Calculator");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recurring_deposit_fragment, container, false);
        initializeData(view);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        byMonthly.setChecked(byMonthly.isChecked());
        byTarget.setChecked(byTarget.isChecked());
        installmentOrMaturityText.setText(installmentOrMaturityText.getText().toString());
        periodText.setText(periodText.getText().toString());
        roiText.setText(roiText.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        periodUnitSelect.setSelection(1);
    }

    public void initializeData(View view){
        installmentOrMaturityText = (EditText) view.findViewById(R.id.installment_or_maturity);
        periodText = (EditText) view.findViewById(R.id.period);
        periodUnitSelect = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, RecurringDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSelect.setAdapter(periodUnitAdapter);
        roiText = (EditText) view.findViewById(R.id.roi);
        byMonthly = (CheckBox) view.findViewById(R.id.by_monthly);
        byTarget = (CheckBox) view.findViewById(R.id.by_target);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        resultsTable = (TableLayout) view.findViewById(R.id.results);
        resultsTable.setVisibility(View.GONE);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        pieChart.setVisibility(View.GONE);

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
                installmentOrMaturityText.setText("");
                periodText.setText("");
                periodUnitSelect.setSelection(1);
                roiText.setText("");
                isCalcClicked = false;
                resultsTable.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
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

    private void calculate(boolean isFromInitialLoad, boolean isCalcClicked){
        this.isCalcClicked = isCalcClicked;
        if(!this.isCalcClicked){
            return;
        }
        String installmentOrMaturity, maturityAmount, roi, duration;
        installmentOrMaturity = installmentOrMaturityText.getText().toString();
        roi = roiText.getText().toString();
        duration = periodText.getText().toString();
        if(!installmentOrMaturity.equals("") && !roi.equals("") && !duration.equals("")){
            String selectedPeriod = (String)periodUnitSelect.getSelectedItem();
            if(selectedPeriod.equals(RecurringDepositActivity.PERIOD[0])){
                if((Double.parseDouble(duration))%3 == 0){
                }else{
                    AppMain.dialogBuilder(getContext(),
                            "Error",
                            "Term should be multiples of 3 !",
                            "OK").create().show();
                    return;
                }
            }
            boolean isByMonthly = ((CheckBox)getView().findViewById(R.id.by_monthly)).isChecked();
            boolean isByTarget = ((CheckBox)getView().findViewById(R.id.by_target)).isChecked();
            recurringDepositObject.setIsMonthly(isByMonthly);
            recurringDepositObject.setIsByTargetAmount(isByTarget);
            if(isByMonthly){
                recurringDepositObject.setMonthlyInvestment(Double.parseDouble(installmentOrMaturity));
            }
            if(isByTarget){
                recurringDepositObject.setMaturityAmount(Double.parseDouble(installmentOrMaturity));
            }
            recurringDepositObject.setDuration(Double.parseDouble(duration));
            recurringDepositObject.setRoi(Double.parseDouble(roi));
            recurringDepositObject.setDurationUnit(selectedPeriod);
            calculate(recurringDepositObject);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    private void calculate(RecurringDepositObject recurringDepositData) {
        /**
         *   M = ( R * [(1+r)^n - 1 ] ) / (1-(1+r)^-1/3)
         *
         *   M = Maturity value
         *   R = Monthly Installment
         *   r = Rate of Interest (i) / 400
         *   n = Number of Quarters
         * */

        double R, r, n, M, P, I;

        if(recurringDepositData.getDurationUnit().equals(RecurringDepositActivity.PERIOD[0])){
            n = recurringDepositData.getDuration()/3.0;
        }else{
            n = recurringDepositData.getDuration() * 4;
        }

        r = recurringDepositData.getRoi();
        r = r/400;

        if(recurringDepositData.isMonthly()){
            R = recurringDepositData.getMonthlyInvestment();
            double x = 1+r;
            x = Math.pow(x, n);
            x = x-1;
            x = R*x;
            double y = 1+r;
            double y1 = 1.0/3.0;
            y = Math.pow(y,y1);
            y = 1.0/y;
            y = 1-y;
            M  = x/y;
            P = R*n*3;
            I = M-P;
            recurringDepositData.setMaturityAmount(M);
            recurringDepositData.setInvestedAmount(P);
            recurringDepositData.setTotalInterest(I);
        }
        if(recurringDepositData.isByTargetAmount()){
            M = recurringDepositData.getMaturityAmount();
            double x = 1+r;
            double y1 = 1.0/3.0;
            x = Math.pow(x, y1);
            x = 1/x;
            x = 1-x;
            x = M*x;
            double y = 1+r;
            y = Math.pow(y, n);
            y = y-1;
            R = x/y;
            R = Math.round(R);
            P = R*n*3;
            I = M-P;
            recurringDepositData.setMonthlyInvestment(R);
            recurringDepositData.setInvestedAmount(P);
            recurringDepositData.setTotalInterest(I);
        }
        displayResults(recurringDepositData);
    }

    private void displayResults(RecurringDepositObject recurringDepositData){
        View view = getView();
        pieChart.clear();

        if(recurringDepositData.isMonthly()){
            ((TextView)view.findViewById(R.id.monthly_or_maturity_label)).setText("Maturity Amount");
            ((TextView)view.findViewById(R.id.monthly_or_maturity_amount)).setText(
                    MainPrefs.getFormattedNumber(recurringDepositData.getMaturityAmount()));
        }
        if(recurringDepositData.isByTargetAmount()){
            ((TextView)view.findViewById(R.id.monthly_or_maturity_label)).setText("Monthly Investment");
            ((TextView)view.findViewById(R.id.monthly_or_maturity_amount)).setText(
                    MainPrefs.getFormattedNumber(recurringDepositData.getMonthlyInvestment()));
        }

        ((TextView)view.findViewById(R.id.investment_amount)).setText(
                MainPrefs.getFormattedNumber(recurringDepositData.getInvestedAmount()));
        ((TextView)view.findViewById(R.id.interest)).setText(
                MainPrefs.getFormattedNumber(recurringDepositData.getTotalInterest()));

        List entries = new ArrayList();
        entries.add(new Entry((float)recurringDepositData.getInvestedAmount(), 0));
        entries.add(new Entry((float)recurringDepositData.getTotalInterest(), 1));
        List labels = new ArrayList();
        labels.add("Investment");
        labels.add("Interest");
        pieChart = PieChartConfig.createPieChart(getActivity(), pieChart, entries, labels, "");
        resultsTable.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.VISIBLE);
    }

}
