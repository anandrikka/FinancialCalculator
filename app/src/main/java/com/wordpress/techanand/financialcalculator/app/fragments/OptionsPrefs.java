package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;

import com.wordpress.techanand.financialcalculator.R;

/**
 * Created by nandu on 6/6/2016.
 */
public class OptionsPrefs extends CustomPreferenceFragment {

    public OptionsPrefs() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.options);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_flat_charges_key),
                R.string.prefs_brokerage_options_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_percent_key),
                R.string.prefs_brokerage_options_percent_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_maximum_key),
                R.string.prefs_brokerage_options_maximum_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Options Preferences");
    }
}
