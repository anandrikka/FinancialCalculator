package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StampDutyPrefs extends CustomPreferenceFragment {


    public StampDutyPrefs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.stampduty_preferences);
        getActivity().setTitle("Stampduty charges");
        initializeData();
    }

    private void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_delivery_min_key),
                R.string.prefs_exchange_stampduty_delivery_min_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_delivery_max_key),
                R.string.prefs_exchange_stampduty_delivery_max_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_delivery_percent_key),
                R.string.prefs_exchange_stampduty_delivery_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_intraday_min_key),
                R.string.prefs_exchange_stampduty_intraday_min_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_intraday_max_key),
                R.string.prefs_exchange_stampduty_intraday_max_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_intraday_percent_key),
                R.string.prefs_exchange_stampduty_intraday_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_futures_min_key),
                R.string.prefs_exchange_stampduty_futures_min_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_futures_max_key),
                R.string.prefs_exchange_stampduty_futures_max_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_futures_percent_key),
                R.string.prefs_exchange_stampduty_futures_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_options_min_key),
                R.string.prefs_exchange_stampduty_options_min_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_options_max_key),
                R.string.prefs_exchange_stampduty_options_max_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_options_percent_key),
                R.string.prefs_exchange_stampduty_options_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_currencies_min_key),
                R.string.prefs_exchange_stampduty_currencies_min_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_currencies_max_key),
                R.string.prefs_exchange_stampduty_currencies_max_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_currencies_percent_key),
                R.string.prefs_exchange_stampduty_currencies_percent_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_commodities_min_key),
                R.string.prefs_exchange_stampduty_commodities_min_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_commodities_max_key),
                R.string.prefs_exchange_stampduty_commodities_max_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stampduty_commodities_percent_key),
                R.string.prefs_exchange_stampduty_commodities_percent_default);
    }

}
