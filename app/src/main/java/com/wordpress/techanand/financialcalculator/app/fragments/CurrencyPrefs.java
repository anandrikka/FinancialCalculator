package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 7/6/2016.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wordpress.techanand.financialcalculator.R;

public class CurrencyPrefs extends CustomPreferenceFragment {


    public CurrencyPrefs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.currency_preferences);
        initializeData();
    }

    private void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_currency_futures_maximum_key),
                R.string.prefs_brokerage_currency_futures_maximum_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_currency_futures_percent_key),
                R.string.prefs_brokerage_currency_futures_percent_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_currency_options_maximum_key),
                R.string.prefs_brokerage_currency_options_maximum_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_currency_options_percent_key),
                R.string.prefs_brokerage_currency_options_percent_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Currency Preferences");
    }
}
