package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.models.RecurringDepositObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand Rikka on 6/11/2016.
 */
public class RecurringDepositResult extends Fragment{

    public RecurringDepositResult() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recurring_deposit_result, container, false);
        setRetainInstance(true);
        return view;
    }

    public void displayResults(RecurringDepositObject recurringDepositData){
        View view = getView();
        PieChart pieChart = (PieChart) getView().findViewById(R.id.chart);
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
    }
}
