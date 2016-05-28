package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.BrokeragePrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.ExchangePrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.StampDutyPrefs;

public class PreferencesActivity extends AppCompatActivity {

    BrokeragePrefs brokeragePrefs;
    ExchangePrefs exchangePrefs;
    StampDutyPrefs stampDutyPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String prefsType = (String)getIntent().getStringExtra("PrefsType");

        if(prefsType.equals("Brokerage")){
            setTitle("Brokerage Preferences");
            brokeragePrefs = new BrokeragePrefs();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, brokeragePrefs)
                    .commit();
        } else if(prefsType.equals("Exchange")){
            setTitle("Exchange Preferences");
            exchangePrefs = new ExchangePrefs();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, exchangePrefs)
                    .commit();
        } /*else if(prefsType.equals("StampDuty")) {
            setTitle("Stamp Duty Preferences");
            stampDutyPrefs = new StampDutyPrefs();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, stampDutyPrefs)
                    .commit();
        }*/
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
