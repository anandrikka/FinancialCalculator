package com.wordpress.techanand.financialcalculator.app.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.activities.PreferencesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExchangePrefs extends CustomPreferenceFragment {

    public ExchangePrefs() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.exchange_preferences);
        initializeData();
    }

    private void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_service_tax_key),
                R.string.prefs_exchange_service_tax_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_sebi_charges_key),
                R.string.prefs_sebi_charges_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stamp_duty_min_key),
                R.string.prefs_exchange_stamp_duty_min_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stamp_duty_min_key),
                R.string.prefs_exchange_stamp_duty_min_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_delivery_key),
                R.string.prefs_exchange_stt_delivery_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_intraday_key),
                R.string.prefs_exchange_stt_intraday_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_futures_key),
                R.string.prefs_exchange_stt_futures_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_options_key),
                R.string.prefs_exchange_stt_options_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_delivery_key),
                R.string.prefs_exchange_nsecharges_delivery_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_intraday_key),
                R.string.prefs_exchange_nsecharges_intraday_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_futures_key),
                R.string.prefs_exchange_nsecharges_futures_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_options_key),
                R.string.prefs_exchange_nsecharges_options_default);

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
