package com.wordpress.techanand.financialcalculator.app.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.PreferencesActivity;
import com.wordpress.techanand.financialcalculator.app.activities.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(getActivity() != null){
            setPrefsForBrokerage(key);
            setPrefsForExchange(key);
        }
    }

    protected int setPrefsForBrokerage(String key){
        int defaultId = 0;
        if(getActivity() != null){
            if(getKeyVal(R.string.prefs_brokerage_delivery_flat_charges_key).equals(key)){
                defaultId = R.string.prefs_brokerage_delivery_flat_charges_default;
            }else if(getKeyVal(R.string.prefs_brokerage_delivery_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_delivery_percent_default;
            }else if(getKeyVal(R.string.prefs_brokerage_intraday_flat_charges_key).equals(key)){
                defaultId = R.string.prefs_brokerage_intraday_flat_charges_default;
            }else if(getKeyVal(R.string.prefs_brokerage_intraday_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_intraday_percent_default;
            }else if(getKeyVal(R.string.prefs_brokerage_futures_flat_rate_key).equals(key)){
                defaultId = R.string.prefs_brokerage_futures_flat_charges_default;
            }else if(getKeyVal(R.string.prefs_brokerage_futures_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_futures_percent_default;
            }else if(getKeyVal(R.string.prefs_brokerage_options_flat_charges_key).equals(key)){
                defaultId = R.string.prefs_brokerage_options_flat_charges_default;
            }else if(getKeyVal(R.string.prefs_brokerage_options_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_options_percent_default;
            }else if(getKeyVal(R.string.prefs_brokerage_currency_minimum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_currency_minimum_default;
            }else if(getKeyVal(R.string.prefs_brokerage_currency_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_currency_percent_default;
            }else if(getKeyVal(R.string.prefs_brokerage_commodities_minimum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_commodities_minimum_default;
            }else if(getKeyVal(R.string.prefs_brokerage_commodities_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_commodities_percent_default;
            }/*else if(getKeyVal(R.string.prefs_brokerage_delivery_use_flat_charges_key).equals(key)){
                defaultId = R.string.prefs_brokerage_delivery_use_flat_charges_default;
            }else if(getKeyVal(R.string.prefs_brokerage_intraday_use_flat_charges_key).equals(key)){
                defaultId = R.string.prefs_brokerage_intraday_use_flat_charges_default;
            }else if(getKeyVal(R.string.prefs_brokerage_futures_use_flat_charges_key).equals(key)){
                defaultId = R.string.prefs_brokerage_futures_use_flat_charges_default;
            }else if(getKeyVal(R.string.prefs_brokerage_options_use_flat_charges_key).equals(key)){
                defaultId = R.string.prefs_brokerage_options_use_flat_charges_default;
            }*/
            if(defaultId != 0){
                setPrefSummaryValue(key, defaultId);
            }
        }

        return defaultId;
    }

    protected int setPrefsForExchange(String key){
        int defaultId = 0;
        if(getKeyVal(R.string.prefs_exchange_service_tax_key).equals(key)){
            defaultId = R.string.prefs_exchange_service_tax_default;
        }else if(getKeyVal(R.string.prefs_sebi_charges_key).equals(key)){
            defaultId = R.string.prefs_sebi_charges_default;
        }else if(getKeyVal(R.string.prefs_exchange_stamp_duty_min_key).equals(key)){
            defaultId = R.string.prefs_exchange_stamp_duty_min_default;
        }else if(getKeyVal(R.string.prefs_exchange_stamp_duty_percent_key).equals(key)){
            defaultId = R.string.prefs_exchange_stamp_duty_percent_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_delivery_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_delivery_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_intraday_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_intraday_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_futures_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_futures_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_options_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_options_default;
        }else if(getKeyVal(R.string.prefs_exchange_nsecharges_delivery_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_delivery_default;
        }else if(getKeyVal(R.string.prefs_exchange_nsecharges_intraday_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_intraday_default;
        }else if(getKeyVal(R.string.prefs_exchange_nsecharges_futures_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_futures_default;
        }else if(getKeyVal(R.string.prefs_exchange_nsecharges_options_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_options_default;
        }else if(getKeyVal(R.string.prefs_exchange_bsecharges_delivery_key).equals(key)){
            defaultId = R.string.prefs_exchange_bsecharges_delivery_default;
        }else if(getKeyVal(R.string.prefs_exchange_bsecharges_intraday_key).equals(key)){
            defaultId = R.string.prefs_exchange_bsecharges_intraday_default;
        }else if(getKeyVal(R.string.prefs_exchange_bsecharges_futures_key).equals(key)){
            defaultId = R.string.prefs_exchange_bsecharges_futures_default;
        }else if(getKeyVal(R.string.prefs_exchange_bsecharges_options_key).equals(key)){
            defaultId = R.string.prefs_exchange_bsecharges_options_default;
        }
        if(defaultId != 0){
            setPrefSummaryValue(key, defaultId);
        }
        return defaultId;
    }

    protected String getKeyVal(int id){
        return getActivity().getResources().getString(id);
    }

    protected void setPrefSummaryValue(String key,  int id){
        Preference preference = findPreference(key);
        if(preference != null){
            preference.setSummary(sharedPreferences.getString(key,
                    getActivity().getResources().getString(id)));
        }
    }
}
