package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.BSETurnoverPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.BrokeragePrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.ExchangePrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.NSETurnoverPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.StampDutyPrefs;

public class AppPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_preferences);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.prefs, new MainPrefs(), MainPrefs.class.getName())
                .addToBackStack(MainPrefs.class.getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        //generally it should be == 0, however my activity view is a blank view, so skipping it
        int count = getFragmentManager().getBackStackEntryCount();
        if (count < 2) {
            super.onBackPressed();
        } else {
            //Track back fragments by Tag - This will help to switch b/w fragments easily
            //Note : Define fragments with Tag as 3rd parameter will add/replace
            android.app.FragmentManager fm = getFragmentManager();
            if((fm.findFragmentByTag(BrokeragePrefs.class.getName()) != null &&
                    fm.findFragmentByTag(BrokeragePrefs.class.getName()).isVisible()) ||
                    (fm.findFragmentByTag(ExchangePrefs.class.getName()) != null &&
                            fm.findFragmentByTag(ExchangePrefs.class.getName()).isVisible())){
                fm.beginTransaction()
                        .replace(R.id.prefs, new MainPrefs(), MainPrefs.class.getName())
                        .addToBackStack(MainPrefs.class.getName())
                        .commit();
            }else if((fm.findFragmentByTag(StampDutyPrefs.class.getName()) != null &&
                    fm.findFragmentByTag(StampDutyPrefs.class.getName()).isVisible()) ||
                    (fm.findFragmentByTag(BSETurnoverPrefs.class.getName()) != null &&
                            fm.findFragmentByTag(BSETurnoverPrefs.class.getName()).isVisible()) ||
                    (fm.findFragmentByTag(NSETurnoverPrefs.class.getName()) != null &&
                            fm.findFragmentByTag(NSETurnoverPrefs.class.getName()).isVisible())){
                fm.beginTransaction()
                        .replace(R.id.prefs, new ExchangePrefs(), ExchangePrefs.class.getName())
                        .addToBackStack(ExchangePrefs.class.getName())
                        .commit();
            }else{
                super.onBackPressed();
            }
            //getFragmentManager().popBackStack();

        }

    }

    //This is for manually pressing of back-button, no need to add acitivity in AndroidManifest.xml
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // back button in toolbar
            onBackPressed();
            return true;
        }
        return false;
    }
}
