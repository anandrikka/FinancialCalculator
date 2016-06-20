package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 6/6/2016
 */

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;

import com.wordpress.techanand.financialcalculator.R;

import java.text.DecimalFormat;

public class MainPrefs extends CustomPreferenceFragment implements Preference.OnPreferenceClickListener{

    public static final DecimalFormat numberFormatter = new DecimalFormat("#,##,###.00");

    public MainPrefs() {
        // Required empty public constructor
    }

    public static String getFormattedNumber(double number){
        numberFormatter.setMinimumIntegerDigits(1);
        return numberFormatter.format(number);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_preferences);
        setRetainInstance(true);
        if(getActivity() != null){
            findPreference(getResources().
                    getString(R.string.prefs_brokerage_key)).setOnPreferenceClickListener(this);
            findPreference(getResources().
                    getString(R.string.prefs_exchange_key)).setOnPreferenceClickListener(this);
        }

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference.getKey().equals(getString(R.string.prefs_brokerage_key))){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new BrokeragePrefs(), BrokeragePrefs.class.getName())
                    //.addToBackStack(BrokeragePrefs.class.getName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }else if(preference.getKey().equals(getString(R.string.prefs_exchange_key))){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new ExchangePrefs(), ExchangePrefs.class.getName())
                    //.addToBackStack(ExchangePrefs.class.getName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Settings");
    }

    /*@Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

    }*/
}
