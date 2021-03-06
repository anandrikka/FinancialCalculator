package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 5/29/2016.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.wordpress.techanand.financialcalculator.R;

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
            setPrefsForStampDuty(key);
            setPrefsForNSETurnover(key);
            setPrefsForBSETurnover(key);
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
            }else if(getKeyVal(R.string.prefs_brokerage_currency_futures_maximum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_currency_futures_maximum_default;
            }else if(getKeyVal(R.string.prefs_brokerage_currency_futures_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_currency_futures_percent_default;
            }else if(getKeyVal(R.string.prefs_brokerage_currency_options_maximum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_currency_options_maximum_default;
            }else if(getKeyVal(R.string.prefs_brokerage_currency_options_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_currency_options_percent_default;
            }else if(getKeyVal(R.string.prefs_brokerage_commodities_maximum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_commodities_maximum_default;
            }else if(getKeyVal(R.string.prefs_brokerage_commodities_percent_key).equals(key)){
                defaultId = R.string.prefs_brokerage_commodities_percent_default;
            }/*else if(getKeyVal(R.string.prefs_brokerage_delivery_maximum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_delivery_maximum_default;
            }*/else if(getKeyVal(R.string.prefs_brokerage_intraday_maximum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_intraday_maximum_default;
            }else if(getKeyVal(R.string.prefs_brokerage_futures_maximum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_futures_maximum_default;
            }else if(getKeyVal(R.string.prefs_brokerage_options_maximum_key).equals(key)){
                defaultId = R.string.prefs_brokerage_options_maximum_default;
            }

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
        }else if(getKeyVal(R.string.prefs_exchange_stt_delivery_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_delivery_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_intraday_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_intraday_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_futures_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_futures_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_options_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_options_default;
        }else if(getKeyVal(R.string.prefs_exchange_stt_commodities_key).equals(key)){
            defaultId = R.string.prefs_exchange_stt_commodities_default;
        }

        if(defaultId != 0){
            setPrefSummaryValue(key, defaultId);
        }
        return defaultId;
    }

    protected int setPrefsForStampDuty(String key){
        int defaultId = 0;
        if(getKeyVal(R.string.prefs_exchange_stampduty_delivery_min_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_delivery_min_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_delivery_percent_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_delivery_percent_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_delivery_max_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_delivery_max_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_intraday_min_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_intraday_min_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_intraday_percent_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_intraday_percent_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_intraday_max_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_intraday_max_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_futures_min_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_futures_min_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_futures_percent_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_futures_percent_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_futures_max_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_futures_max_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_options_min_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_options_min_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_options_percent_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_options_percent_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_options_max_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_options_max_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_currencies_min_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_currencies_min_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_currencies_percent_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_currencies_percent_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_currencies_max_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_currencies_max_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_commodities_min_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_commodities_min_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_commodities_percent_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_commodities_percent_default;
        }else if(getKeyVal(R.string.prefs_exchange_stampduty_commodities_max_key).equals(key)){
            defaultId = R.string.prefs_exchange_stampduty_commodities_max_default;
        }
        if(defaultId != 0){
            setPrefSummaryValue(key, defaultId);
        }
        return defaultId;
    }

    protected int setPrefsForNSETurnover(String key){
        int defaultId = 0;
        if(getKeyVal(R.string.prefs_exchange_nsecharges_delivery_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_delivery_default;
        }else if(getKeyVal(R.string.prefs_exchange_nsecharges_intraday_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_intraday_default;
        }else if(getKeyVal(R.string.prefs_exchange_nsecharges_futures_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_futures_default;
        }else if(getKeyVal(R.string.prefs_exchange_nsecharges_options_key).equals(key)){
            defaultId = R.string.prefs_exchange_nsecharges_options_default;
        }
        if(defaultId != 0){
            setPrefSummaryValue(key, defaultId);
        }
        return defaultId;
    }

    protected int setPrefsForBSETurnover(String key){
        int defaultId = 0;
        if(getKeyVal(R.string.prefs_exchange_bsecharges_delivery_key).equals(key)){
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

    protected void setPrefSummaryValue(String key,  int defaultId){
        Preference preference = findPreference(key);
        if(preference != null){
            String prefVal = sharedPreferences.getString(key,
                    getActivity().getResources().getString(defaultId));
            preference.setSummary(prefVal);
        }
    }


}
