package com.wordpress.techanand.financialcalculator.app.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.AppPreferencesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExchangePrefs extends CustomPreferenceFragment implements Preference.OnPreferenceClickListener{

    public ExchangePrefs() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Exchange Settings");
        addPreferencesFromResource(R.xml.exchange_preferences);
        setRetainInstance(true);
        initializeData();
        findPreference("stampduty_screen").setOnPreferenceClickListener(this);
        findPreference("turnover_nse_screen").setOnPreferenceClickListener(this);
        findPreference("turnover_bse_screen").setOnPreferenceClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Exchange Settings");
    }

    private void initializeData(){
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_service_tax_key),
                R.string.prefs_exchange_service_tax_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_sebi_charges_key),
                R.string.prefs_sebi_charges_default);

        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_delivery_key),
                R.string.prefs_exchange_stt_delivery_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_intraday_key),
                R.string.prefs_exchange_stt_intraday_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_futures_key),
                R.string.prefs_exchange_stt_futures_default);
        setPrefSummaryValue(getKeyVal(R.string.prefs_exchange_stt_options_key),
                R.string.prefs_exchange_stt_options_default);

        setSummaryForStampDuty();
        setSummaryForNSEPrefs();
        setSummaryForBSEPrefs();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference.getKey().equals("stampduty_screen")){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new StampDutyPrefs(), StampDutyPrefs.class.getName())
                    .addToBackStack(StampDutyPrefs.class.getName())
                    .commit();
        }else if(preference.getKey().equals("turnover_bse_screen")){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new BSETurnoverPrefs(), BSETurnoverPrefs.class.getName())
                    .addToBackStack(BSETurnoverPrefs.class.getName())
                    .commit();
        }else if(preference.getKey().equals("turnover_nse_screen")){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.prefs, new NSETurnoverPrefs(), NSETurnoverPrefs.class.getName())
                    .addToBackStack(NSETurnoverPrefs.class.getName())
                    .commit();
        }
        return true;
    }

    private void setSummaryForStampDuty(){
        Preference stampDuty = findPreference(getResources().getString(R.string.prefs_exchange_stampduty_screen));
        stampDuty.setSummary("Click to see charges for Equity, Currency & Commodities");
        /*StringBuilder stampDutySummary = new StringBuilder();
        stampDutySummary
                .append("\n")
                .append("Delivery      :\t\t"+
                    AppMain.getStringPref(getActivity(),
                            R.string.prefs_exchange_stampduty_delivery_min_key,
                            R.string.prefs_exchange_stampduty_delivery_min_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                            R.string.prefs_exchange_stampduty_delivery_max_key,
                            R.string.prefs_exchange_stampduty_delivery_max_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                            R.string.prefs_exchange_stampduty_delivery_percent_key,
                            R.string.prefs_exchange_stampduty_delivery_percent_default))
                .append("\n")
                .append("Intraday     :\t\t"+
                        AppMain.getStringPref(getActivity(),
                                R.string.prefs_exchange_stampduty_intraday_min_key,
                                R.string.prefs_exchange_stampduty_intraday_min_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_intraday_max_key,
                        R.string.prefs_exchange_stampduty_intraday_max_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_intraday_percent_key,
                        R.string.prefs_exchange_stampduty_intraday_percent_default))
                .append("\n")
                .append("Futures       :\t\t"+
                        AppMain.getStringPref(getActivity(),
                                R.string.prefs_exchange_stampduty_futures_min_key,
                                R.string.prefs_exchange_stampduty_futures_min_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_futures_max_key,
                        R.string.prefs_exchange_stampduty_futures_max_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_futures_percent_key,
                        R.string.prefs_exchange_stampduty_futures_percent_default))
                .append("\n")
                .append("Options      :\t\t"+
                        AppMain.getStringPref(getActivity(),
                                R.string.prefs_exchange_stampduty_options_min_key,
                                R.string.prefs_exchange_stampduty_options_min_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_options_max_key,
                        R.string.prefs_exchange_stampduty_options_max_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_options_percent_key,
                        R.string.prefs_exchange_stampduty_options_percent_default))
                .append("\n")
                .append("Currency    :\t\t"+
                        AppMain.getStringPref(getActivity(),
                                R.string.prefs_exchange_stampduty_currencies_min_key,
                                R.string.prefs_exchange_stampduty_currencies_min_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_currencies_max_key,
                        R.string.prefs_exchange_stampduty_currencies_max_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_currencies_percent_key,
                        R.string.prefs_exchange_stampduty_currencies_percent_default))
                .append("\n")
                .append("Commodities:\t\t"+
                        AppMain.getStringPref(getActivity(),
                                R.string.prefs_exchange_stampduty_currencies_min_key,
                                R.string.prefs_exchange_stampduty_currencies_min_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_commodities_max_key,
                        R.string.prefs_exchange_stampduty_commodities_max_default))
                .append("\t\t")
                .append(AppMain.getStringPref(getActivity(),
                        R.string.prefs_exchange_stampduty_commodities_percent_key,
                        R.string.prefs_exchange_stampduty_commodities_percent_default))
                .toString();
        stampDuty.setSummary(stampDutySummary.toString());*/
    }

    private void setSummaryForNSEPrefs(){
        Preference nseTurnover = findPreference(getResources().getString(R.string.prefs_exchange_turnover_nse_screen));
        nseTurnover.setSummary("Click to see charges for Equity, Currency & Commodities");
    }

    private void setSummaryForBSEPrefs(){
        Preference bseTurnOver = findPreference(getResources().getString(R.string.prefs_exchange_turnover_bse_screen));
        bseTurnOver.setSummary("Click to see charges for Equity, Currency & Commodities");
    }

}
