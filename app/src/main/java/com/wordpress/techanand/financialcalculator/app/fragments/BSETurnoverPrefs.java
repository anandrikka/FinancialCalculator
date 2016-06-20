package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 7/6/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wordpress.techanand.financialcalculator.R;

public class BSETurnoverPrefs extends CustomPreferenceFragment {


    public BSETurnoverPrefs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.bse_turnover_preferences);
        getActivity().setTitle("BSE Turnover charges");
        initializeData();
    }

    public void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_bsecharges_delivery_key),
                R.string.prefs_exchange_bsecharges_delivery_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_bsecharges_intraday_key),
                R.string.prefs_exchange_bsecharges_intraday_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_bsecharges_futures_key),
                R.string.prefs_exchange_bsecharges_futures_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_bsecharges_options_key),
                R.string.prefs_exchange_bsecharges_options_default);
    }
}
