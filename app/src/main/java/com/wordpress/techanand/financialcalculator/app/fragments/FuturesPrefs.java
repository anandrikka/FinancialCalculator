package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;

import com.wordpress.techanand.financialcalculator.R;

/**
 * Created by nandu on 6/6/2016.
 */
public class FuturesPrefs extends CustomPreferenceFragment{

    public FuturesPrefs() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.futures);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_flat_rate_key),
                R.string.prefs_brokerage_futures_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_percent_key),
                R.string.prefs_brokerage_futures_percent_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_maximum_key),
                R.string.prefs_brokerage_futures_maximum_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Futures Preferences");
    }
}
