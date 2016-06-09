package com.wordpress.techanand.financialcalculator.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.techanand.financialcalculator.R;

/**
 * Created by Anand Rikka on 6/6/2016.
 */
public class DeliveryPrefs extends CustomPreferenceFragment{

    public DeliveryPrefs(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.delivery);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_delivery_flat_charges_key),
                R.string.prefs_brokerage_delivery_flat_charges_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_brokerage_delivery_percent_key),
                R.string.prefs_brokerage_delivery_percent_default);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Delivery Preferences");
    }
}
