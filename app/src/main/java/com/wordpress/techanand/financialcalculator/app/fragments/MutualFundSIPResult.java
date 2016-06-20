package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 6/6/2016
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.models.MutualFundObject;


public class MutualFundSIPResult extends Fragment {


    public MutualFundSIPResult() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mutual_funds_sip_results, container, false);
        return view;
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

    }

}
