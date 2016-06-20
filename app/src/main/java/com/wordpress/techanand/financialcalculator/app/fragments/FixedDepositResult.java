package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 6/6/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;
import com.wordpress.techanand.financialcalculator.app.models.FixedDepositObject;

import java.util.ArrayList;
import java.util.List;

public class FixedDepositResult extends Fragment {


    public FixedDepositResult() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fixed_deposit_result, container, false);
        return view;
    }

    public void displayResult(FixedDepositObject fixedDepositObject){
        PieChart pieChart = (PieChart) getView().findViewById(R.id.chart);
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
