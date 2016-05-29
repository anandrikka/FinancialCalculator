package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;

import com.wordpress.techanand.financialcalculator.R;

public class BrokeragePrefs extends CustomPreferenceFragment {

    public BrokeragePrefs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.brokerage_preferences);
        getActivity().setTitle("Brokerage Settings");
        initializeData();
    }

    private void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_delivery_flat_charges_key),
                R.string.prefs_brokerage_delivery_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_delivery_percent_key),
                R.string.prefs_brokerage_delivery_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_flat_charges_key),
                R.string.prefs_brokerage_intraday_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_percent_key),
                R.string.prefs_brokerage_intraday_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_flat_rate_key),
                R.string.prefs_brokerage_futures_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_percent_key),
                R.string.prefs_brokerage_futures_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_flat_charges_key),
                R.string.prefs_brokerage_options_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_percent_key),
                R.string.prefs_brokerage_options_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_currency_minimum_key),
                R.string.prefs_brokerage_currency_minimum_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_currency_percent_key),
                R.string.prefs_brokerage_currency_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_commodities_minimum_key),
                R.string.prefs_brokerage_commodities_minimum_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_commodities_percent_key),
                R.string.prefs_brokerage_commodities_percent_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Brokerage Settings");
    }
}
