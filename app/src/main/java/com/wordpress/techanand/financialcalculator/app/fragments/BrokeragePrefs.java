package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;
import android.preference.Preference;

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
        String prefScreenKey = getResources().getString(R.string.prefs_brokerage_currency_key);
        findPreference(prefScreenKey).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.prefs, new CurrencyPrefs(), CurrencyPrefs.class.getName())
                        .addToBackStack(CurrencyPrefs.class.getName())
                        .commit();
                return true;
            }
        });
        initializeData();
    }

    private void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_delivery_flat_charges_key),
                R.string.prefs_brokerage_delivery_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_delivery_percent_key),
                R.string.prefs_brokerage_delivery_percent_default);
        /*setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_delivery_maximum_key),
                R.string.prefs_brokerage_delivery_maximum_default);*/

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_flat_charges_key),
                R.string.prefs_brokerage_intraday_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_percent_key),
                R.string.prefs_brokerage_intraday_percent_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_intraday_maximum_key),
                R.string.prefs_brokerage_intraday_maximum_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_flat_rate_key),
                R.string.prefs_brokerage_futures_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_percent_key),
                R.string.prefs_brokerage_futures_percent_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_futures_maximum_key),
                R.string.prefs_brokerage_futures_maximum_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_flat_charges_key),
                R.string.prefs_brokerage_options_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_percent_key),
                R.string.prefs_brokerage_options_percent_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_options_maximum_key),
                R.string.prefs_brokerage_options_maximum_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_commodities_maximum_key),
                R.string.prefs_brokerage_commodities_maximum_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_commodities_percent_key),
                R.string.prefs_brokerage_commodities_percent_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Brokerage Settings");
    }
}
