package com.wordpress.techanand.financialcalculator.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wordpress.techanand.financialcalculator.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anand Rikka on 6/5/2016.
 */
public class AppPreferences {

    public static Map<String, ?> preferences(Context context){
        //todo: Do I need to do it manually, getAll() isn't showing all the preferences.
        Map<String, Object> preferences = new HashMap<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        AppMain.debug("shared preferences: "+sharedPreferences.getAll().keySet().size()+"");

        int keyId, defaultId;
        String key;

        //Equity - Delivery
        keyId = R.string.prefs_brokerage_delivery_use_flat_charges_key;
        defaultId = R.bool.prefs_brokerage_delivery_use_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getBoolPref(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_delivery_flat_charges_key;
        defaultId = R.string.prefs_brokerage_delivery_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_delivery_percent_key;
        defaultId = R.string.prefs_brokerage_delivery_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Equity - Intraday
        keyId = R.string.prefs_brokerage_intraday_use_flat_charges_key;
        defaultId = R.bool.prefs_brokerage_intraday_use_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getBoolPref(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_intraday_flat_charges_key;
        defaultId = R.string.prefs_brokerage_intraday_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_intraday_percent_key;
        defaultId = R.string.prefs_brokerage_intraday_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_intraday_maximum_key;
        defaultId = R.string.prefs_brokerage_intraday_maximum_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Equity - Futures
        keyId = R.string.prefs_brokerage_futures_use_flat_charges_key;
        defaultId = R.bool.prefs_brokerage_futures_use_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getBoolPref(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_futures_flat_rate_key;
        defaultId = R.string.prefs_brokerage_futures_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_futures_percent_key;
        defaultId = R.string.prefs_brokerage_futures_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_futures_maximum_key;
        defaultId = R.string.prefs_brokerage_futures_maximum_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Equity - Options
        keyId = R.string.prefs_brokerage_options_use_flat_charges_key;
        defaultId = R.bool.prefs_brokerage_options_use_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getBoolPref(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_options_flat_charges_key;
        defaultId = R.string.prefs_brokerage_options_flat_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_options_percent_key;
        defaultId = R.string.prefs_brokerage_options_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_options_maximum_key;
        defaultId = R.string.prefs_brokerage_options_maximum_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Currency - Futures
        keyId = R.string.prefs_brokerage_currency_futures_maximum_key;
        defaultId = R.string.prefs_brokerage_currency_futures_maximum_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_currency_futures_percent_key;
        defaultId = R.string.prefs_brokerage_currency_futures_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));


        //Currency - Options
        keyId = R.string.prefs_brokerage_currency_options_maximum_key;
        defaultId = R.string.prefs_brokerage_currency_options_maximum_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_currency_options_percent_key;
        defaultId = R.string.prefs_brokerage_currency_options_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Commodities
        keyId = R.string.prefs_brokerage_commodities_maximum_key;
        defaultId = R.string.prefs_brokerage_commodities_maximum_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_brokerage_commodities_percent_key;
        defaultId = R.string.prefs_brokerage_commodities_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Stt charges
        keyId = R.string.prefs_exchange_stt_delivery_key;
        defaultId = R.string.prefs_exchange_stt_delivery_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stt_intraday_key;
        defaultId = R.string.prefs_exchange_stt_intraday_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stt_futures_key;
        defaultId = R.string.prefs_exchange_stt_futures_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stt_options_key;
        defaultId = R.string.prefs_exchange_stt_options_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stt_commodities_key;
        defaultId = R.string.prefs_exchange_stt_commodities_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Service Tax
        keyId = R.string.prefs_exchange_service_tax_key;
        defaultId = R.string.prefs_exchange_service_tax_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //SEBI Charges
        keyId = R.string.prefs_sebi_charges_key;
        defaultId = R.string.prefs_sebi_charges_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Exchange Charges
        keyId = R.string.prefs_exchange_nsecharges_delivery_key;
        defaultId = R.string.prefs_exchange_nsecharges_delivery_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_nsecharges_intraday_key;
        defaultId = R.string.prefs_exchange_nsecharges_intraday_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_nsecharges_futures_key;
        defaultId = R.string.prefs_exchange_nsecharges_futures_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_nsecharges_options_key;
        defaultId = R.string.prefs_exchange_nsecharges_options_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_bsecharges_delivery_key;
        defaultId = R.string.prefs_exchange_bsecharges_delivery_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_bsecharges_intraday_key;
        defaultId = R.string.prefs_exchange_bsecharges_intraday_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_bsecharges_futures_key;
        defaultId = R.string.prefs_exchange_bsecharges_futures_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_bsecharges_options_key;
        defaultId = R.string.prefs_exchange_bsecharges_options_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //Stampduty charges
        keyId = R.string.prefs_exchange_stampduty_delivery_min_key;
        defaultId = R.string.prefs_exchange_stampduty_delivery_min_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_delivery_max_key;
        defaultId = R.string.prefs_exchange_stampduty_delivery_max_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_delivery_percent_key;
        defaultId = R.string.prefs_exchange_stampduty_delivery_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_intraday_min_key;
        defaultId = R.string.prefs_exchange_stampduty_intraday_min_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_intraday_max_key;
        defaultId = R.string.prefs_exchange_stampduty_intraday_max_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_intraday_percent_key;
        defaultId = R.string.prefs_exchange_stampduty_intraday_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_futures_min_key;
        defaultId = R.string.prefs_exchange_stampduty_futures_min_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_futures_max_key;
        defaultId = R.string.prefs_exchange_stampduty_futures_max_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_futures_percent_key;
        defaultId = R.string.prefs_exchange_stampduty_futures_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_options_min_key;
        defaultId = R.string.prefs_exchange_stampduty_options_min_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_options_max_key;
        defaultId = R.string.prefs_exchange_stampduty_options_max_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        keyId = R.string.prefs_exchange_stampduty_options_percent_key;
        defaultId = R.string.prefs_exchange_stampduty_options_percent_default;
        key = getResource(context, keyId);
        preferences.put(key, getDoublePrefFromString(context, keyId, defaultId));

        //return sharedPreferences.getAll();

        return preferences;
    }

    private static String getResource(Context context, int id){
        return context.getResources().getString(id);
    }

    public static boolean getBoolPref(Context context, int keyId, int defaultId){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(
                context.getResources().getString(keyId),
                context.getResources().getBoolean(defaultId));
    }

    public static double getDoublePref(Context context, int keyId, int defaultId){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(sharedPreferences.getString(
                context.getResources().getString(keyId),
                context.getResources().getString(defaultId)));
    }

    public static double getDoublePrefFromString(Context context, int keyId, int defaultId){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String prefVal = sharedPreferences.getString(
                context.getResources().getString(keyId),
                context.getResources().getString(defaultId));
        if(prefVal.equals("")){
            return -1;
        }
        return Double.parseDouble(prefVal);
    }

    public static String getStringPref(Context context, int keyId, int defaultId){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(
                context.getResources().getString(keyId),
                context.getResources().getString(defaultId));
    }

}
