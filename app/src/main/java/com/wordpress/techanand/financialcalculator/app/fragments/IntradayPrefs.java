package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;

import com.wordpress.techanand.financialcalculator.R;

/**
 * Created by Anand Rikka on 6/6/2016.
 */
public class IntradayPrefs extends CustomPreferenceFragment {

    public IntradayPrefs(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Brokerage Settings");
        addPreferencesFromResource(R.xml.intraday);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_flat_charges_key),
                R.string.prefs_brokerage_intraday_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_percent_key),
                R.string.prefs_brokerage_intraday_percent_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_maximum_key),
                R.string.prefs_brokerage_intraday_maximum_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Intraday Preferences");
    }
}
