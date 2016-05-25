package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.BrokeragePrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.ExchangePrefs;

public class PreferencesActivity extends AppCompatActivity {

    BrokeragePrefs brokeragePrefs;
    ExchangePrefs exchangePrefs;

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
        } /*else if(prefsType.equals("BSE")) {
            bsePrefs = new BSEPrefs();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, bsePrefs)
                    .commit();
        }*/
    }
}
