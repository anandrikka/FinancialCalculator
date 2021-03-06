package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anand Rikka on 5/29/2016
 */

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.BSETurnoverPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.BrokeragePrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.CommoditiesPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.CurrencyPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.DeliveryPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.ExchangePrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.FuturesPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.IntradayPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.NSETurnoverPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.OptionsPrefs;
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
                //.addToBackStack(MainPrefs.class.getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        android.app.FragmentManager fm = getFragmentManager();
        if((fm.findFragmentByTag(BrokeragePrefs.class.getName()) != null &&
                fm.findFragmentByTag(BrokeragePrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(ExchangePrefs.class.getName()) != null &&
                        fm.findFragmentByTag(ExchangePrefs.class.getName()).isVisible())){
            fm.beginTransaction()
                    .replace(R.id.prefs, new MainPrefs(), MainPrefs.class.getName())
                    //.addToBackStack(MainPrefs.class.getName())
                    .commit();
        }else if((fm.findFragmentByTag(StampDutyPrefs.class.getName()) != null &&
                fm.findFragmentByTag(StampDutyPrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(BSETurnoverPrefs.class.getName()) != null &&
                        fm.findFragmentByTag(BSETurnoverPrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(NSETurnoverPrefs.class.getName()) != null &&
                        fm.findFragmentByTag(NSETurnoverPrefs.class.getName()).isVisible())){
            fm.beginTransaction()
                    .replace(R.id.prefs, new ExchangePrefs(), ExchangePrefs.class.getName())
                    //.addToBackStack(ExchangePrefs.class.getName())
                    .commit();
        }else if((fm.findFragmentByTag(DeliveryPrefs.class.getName()) != null &&
                fm.findFragmentByTag(DeliveryPrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(IntradayPrefs.class.getName()) != null &&
                        fm.findFragmentByTag(IntradayPrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(FuturesPrefs.class.getName()) != null &&
                        fm.findFragmentByTag(FuturesPrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(OptionsPrefs.class.getName()) != null &&
                        fm.findFragmentByTag(OptionsPrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(CurrencyPrefs.class.getName()) != null &&
                        fm.findFragmentByTag(CurrencyPrefs.class.getName()).isVisible()) ||
                (fm.findFragmentByTag(CommoditiesPrefs.class.getName()) != null &&
                        fm.findFragmentByTag(CommoditiesPrefs.class.getName()).isVisible())){
            fm.beginTransaction()
                    .replace(R.id.prefs, new BrokeragePrefs(), BrokeragePrefs.class.getName())
                    //.addToBackStack(BrokeragePrefs.class.getName())
                    .commit();
        }else{
                /*Intent intent = new Intent(AppPreferencesActivity.this, MainActivity.class);
                startActivity(intent);*/
            super.onBackPressed();
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
