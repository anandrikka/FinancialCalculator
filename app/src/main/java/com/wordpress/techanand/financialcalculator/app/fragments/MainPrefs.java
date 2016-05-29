package com.wordpress.techanand.financialcalculator.app.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.activities.AppPreferencesActivity;

public class MainPrefs extends CustomPreferenceFragment implements Preference.OnPreferenceClickListener{

    //todo: Screen Orientation not staying on fragment, going to first fragment.

    public MainPrefs() {
        // Required empty public constructor
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
                    .addToBackStack(BrokeragePrefs.class.getName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }else if(preference.getKey().equals(getString(R.string.prefs_exchange_key))){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new ExchangePrefs(), ExchangePrefs.class.getName())
                    .addToBackStack(ExchangePrefs.class.getName())
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
