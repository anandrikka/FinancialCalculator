package com.wordpress.techanand.financialcalculator.app.fragments;

import android.preference.Preference;

/**
 * Created by Anand Rikka on 5/26/2016.
 */

import android.preference.Preference;
import android.os.Bundle;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppPreferences;

import java.util.Map;

public class BrokeragePrefs extends CustomPreferenceFragment implements Preference.OnPreferenceClickListener{

    Preference delivery, intraday, futures, options, currency, commodities;

    public BrokeragePrefs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.brokerage_preferences);
        getActivity().setTitle("Brokerage Settings");
        delivery = (Preference) findPreference(getResources().getString(R.string.prefs_brokerage_delivery_key));
        delivery.setOnPreferenceClickListener(this);
        intraday = (Preference) findPreference(getResources().getString(R.string.prefs_brokerage_intraday_key));
        intraday.setOnPreferenceClickListener(this);
        futures = (Preference) findPreference(getResources().getString(R.string.prefs_brokerage_futures_key));
        futures.setOnPreferenceClickListener(this);
        options = (Preference) findPreference(getResources().getString(R.string.prefs_brokerage_options_key));
        options.setOnPreferenceClickListener(this);
        currency = (Preference) findPreference(getResources().getString(R.string.prefs_brokerage_currency_key));
        currency.setOnPreferenceClickListener(this);
        commodities = (Preference) findPreference(getResources().getString(R.string.prefs_brokerage_commodities_key));
        commodities.setOnPreferenceClickListener(this);
        Map<String, ?> preferences = AppPreferences.preferences(getActivity());
        String summary = "";
        double charges;
        //Delivery
        if((Boolean)preferences.get(getResources().getString(R.string.prefs_brokerage_delivery_use_flat_charges_key))){
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_delivery_flat_charges_key));
            if(charges > 0){
                summary = String.format(getResources().getString(R.string.app_stocks_using_flat_brokerage, Math.round(charges)));
            }else{
                summary = String.format(getResources().getString(R.string.app_stocks_no_brokerage_charges));
            }
        }else{
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_delivery_percent_key));
            summary = String.format(getResources().getString(R.string.app_stocks_delivery_using_percent_brokerage, Double.toString(charges)));
        }
        delivery.setSummary(summary);
        //Intraday
        double maxCharges;
        if((Boolean)preferences.get(getResources().getString(R.string.prefs_brokerage_intraday_use_flat_charges_key))){
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_intraday_flat_charges_key));
            if(charges > 0){
                summary = String.format(getResources().getString(R.string.app_stocks_using_flat_brokerage, Math.round(charges)));
            }else{
                summary = String.format(getResources().getString(R.string.app_stocks_no_brokerage_charges));
            }
        }else{
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_intraday_percent_key));
            maxCharges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_intraday_maximum_key));
            summary = String.format(getResources().getString(R.string.app_stocks_using_percent_brokerage, Double.toString(charges), Double.toString(maxCharges)));
        }
        intraday.setSummary(summary);
        //Futures
        if((Boolean)preferences.get(getResources().getString(R.string.prefs_brokerage_futures_use_flat_charges_key))){
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_futures_flat_rate_key));
            if(charges > 0){
                summary = String.format(getResources().getString(R.string.app_stocks_using_flat_brokerage, Math.round(charges)));
            }else{
                summary = String.format(getResources().getString(R.string.app_stocks_no_brokerage_charges));
            }
        }else{
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_futures_percent_key));
            maxCharges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_futures_maximum_key));
            summary = String.format(getResources().getString(R.string.app_stocks_using_percent_brokerage, Double.toString(charges), Double.toString(maxCharges)));
        }
        futures.setSummary(summary);
        //Options
        if((Boolean)preferences.get(getResources().getString(R.string.prefs_brokerage_options_use_flat_charges_key))){
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_options_flat_charges_key));
            if(charges > 0){
                summary = String.format(getResources().getString(R.string.app_stocks_using_flat_brokerage, Math.round(charges)));
            }else{
                summary = String.format(getResources().getString(R.string.app_stocks_no_brokerage_charges));
            }
        }else{
            charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_options_percent_key));
            maxCharges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_options_maximum_key));
            summary = String.format(getResources().getString(R.string.app_stocks_using_percent_brokerage, Double.toString(charges), Double.toString(maxCharges)));
        }
        options.setSummary(summary);
        //Commodities
        charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_commodities_percent_key));
        maxCharges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_commodities_maximum_key));
        summary = String.format(getResources().getString(R.string.app_stocks_using_percent_brokerage, Double.toString(charges), Double.toString(maxCharges)));
        commodities.setSummary(summary);
        //Currency
        charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_currency_futures_percent_key));
        maxCharges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_currency_futures_maximum_key));
        summary = String.format(getResources().getString(R.string.app_stocks_currency_futures_using_percent_brokerage, Double.toString(charges), Double.toString(maxCharges)));
        summary = summary + "\n\n";
        charges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_currency_options_percent_key));
        maxCharges = (Double)preferences.get(getResources().getString(R.string.prefs_brokerage_currency_options_maximum_key));
        summary = summary + String.format(getResources().getString(R.string.app_stocks_currency_options_using_percent_brokerage, Double.toString(charges), Double.toString(maxCharges)));
        currency.setSummary(summary);
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Brokerage Settings");
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if(delivery.getKey().equals(key)){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new DeliveryPrefs(), DeliveryPrefs.class.getName())
                    //.addToBackStack(DeliveryPrefs.class.getName())
                    .commit();
        }else if(intraday.getKey().equals(key)){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new IntradayPrefs(), IntradayPrefs.class.getName())
                    //.addToBackStack(IntradayPrefs.class.getName())
                    .commit();
        }else if(futures.getKey().equals(key)){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new FuturesPrefs(), FuturesPrefs.class.getName())
                    //.addToBackStack(FuturesPrefs.class.getName())
                    .commit();
        }else if(options.getKey().equals(key)){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new OptionsPrefs(), OptionsPrefs.class.getName())
                    //.addToBackStack(OptionsPrefs.class.getName())
                    .commit();
        }else if(currency.getKey().equals(key)){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new CurrencyPrefs(), CurrencyPrefs.class.getName())
                    //.addToBackStack(CurrencyPrefs.class.getName())
                    .commit();
        }else if(commodities.getKey().equals(key)){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new CommoditiesPrefs(), CommoditiesPrefs.class.getName())
                    //.addToBackStack(CommoditiesPrefs.class.getName())
                    .commit();
        }else {
            Toast.makeText(getActivity(), preference.getKey()+" Preference doesn't exist !", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
