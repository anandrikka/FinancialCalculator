package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NSETurnoverPrefs extends CustomPreferenceFragment {


    public NSETurnoverPrefs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.nse_turnover_preferences);
        getActivity().setTitle("NSE Turnover charges");
        initializeData();
    }

    public void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_delivery_key),
                R.string.prefs_exchange_nsecharges_delivery_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_intraday_key),
                R.string.prefs_exchange_nsecharges_intraday_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_futures_key),
                R.string.prefs_exchange_nsecharges_futures_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_nsecharges_options_key),
                R.string.prefs_exchange_nsecharges_options_default);
    }
}
