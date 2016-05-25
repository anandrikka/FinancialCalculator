package com.wordpress.techanand.financialcalculator.app.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.wordpress.techanand.financialcalculator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrokeragePrefs extends PreferenceFragment {

    private boolean isFlatDelivery, isFlatIntraday, isFlatFutures, isFlatOptions;
    private float flatDeliveryRate, flatIntradayRate, flatFuturesRate, flatOptionsRate;
    private float deliveryPercentage, intradayPercentage, futuresPercentage, optionsPercentage;
    private SharedPreferences preferences;
    private Preference isFlatDeliveryPref, isFlatIntradayPref, isFlatFuturesPref, isFlatOptionsPref;
    private Preference flatDeliveryRatePref, flatIntradayRatePref, flatFuturesPref, flatOptionsPref;
    private Preference deliveryPercentPref, intradayPercentPref, futuresPercentPref, optionsPercentPref;

    public BrokeragePrefs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.brokerage_preferences);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        initializeData();
    }

    private void initializeData(){
        isFlatDeliveryPref = findPreference("delivery_is_flat_rate");
        isFlatIntradayPref = findPreference("intraday_is_flat_rate");
        isFlatFuturesPref = findPreference("futures_is_flat_rate");
        isFlatOptionsPref = findPreference("options_is_flat_rate");

        flatDeliveryRatePref = findPreference("delivery_flat_rate");
        flatIntradayRatePref = findPreference("intraday_flat_rate");
        flatFuturesPref = findPreference("futures_flat_rate");
        flatOptionsPref = findPreference("options_flat_rate");

        deliveryPercentPref = findPreference("delivery_percentage");
        intradayPercentPref = findPreference("intraday_percentage");
        futuresPercentPref = findPreference("futures_percentage");
        optionsPercentPref = findPreference("options_percentage");

    }

    public boolean isFlatDelivery() {
        return isFlatDelivery;
    }

    public boolean isFlatIntraday() {
        return isFlatIntraday;
    }

    public boolean isFlatFutures() {
        return isFlatFutures;
    }

    public boolean isFlatOptions() {
        return isFlatOptions;
    }

    public float getFlatDeliveryRate() {
        return flatDeliveryRate;
    }

    public float getFlatIntradayRate() {
        return flatIntradayRate;
    }

    public float getFlatFuturesRate() {
        return flatFuturesRate;
    }

    public float getFlatOptionsRate() {
        return flatOptionsRate;
    }

    public float getDeliveryPercentage() {
        return deliveryPercentage;
    }

    public float getIntradayPercentage() {
        return intradayPercentage;
    }

    public float getFuturesPercentage() {
        return futuresPercentage;
    }

    public float getOptionsPercentage() {
        return optionsPercentage;
    }
}
