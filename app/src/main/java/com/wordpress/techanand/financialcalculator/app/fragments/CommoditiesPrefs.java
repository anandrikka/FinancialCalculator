package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;

import com.wordpress.techanand.financialcalculator.R;

/**
 * Created by Anand Rikka on 6/6/2016.
 */
public class CommoditiesPrefs extends  CustomPreferenceFragment{

    public CommoditiesPrefs() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.commodities);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_commodities_maximum_key),
                R.string.prefs_brokerage_commodities_maximum_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_commodities_percent_key),
                R.string.prefs_brokerage_commodities_percent_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Commodities Preferences");
    }
}
