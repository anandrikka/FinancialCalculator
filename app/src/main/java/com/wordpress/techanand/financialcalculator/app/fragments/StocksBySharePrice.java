package com.wordpress.techanand.financialcalculator.app.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity;
import com.wordpress.techanand.financialcalculator.app.models.StockCategory;

import static com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksBySharePrice extends Fragment {

    private String category, exchangeType;
    private double buyPrice, sellPrice, quantity;
    private Spinner selectCategory;
    private RadioGroup selectExchange;
    private EditText buyInput, sellInput, quantityInput;
    private Button reset, calculate;
    private SharedPreferences sharedPreferences;

    public StocksBySharePrice() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.stocks_by_share_price, container, false);
        setRetainInstance(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        selectCategory = (Spinner) view.findViewById(R.id.stock_category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, CATEGORIES);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        selectCategory.setAdapter(adapter);

        initializeData(view);

        categoryListener();
        exchangeListener(view);
        resetListener();
        calculateListener(view);

        return view;
    }

    private void initializeData(View view){

        category = (String)selectCategory.getSelectedItem();
        selectExchange = (RadioGroup) view.findViewById(R.id.stock_exchange_choose);
        RadioButton selectedExchange = (RadioButton) view.findViewById(selectExchange.getCheckedRadioButtonId());
        exchangeType = selectedExchange.getText().toString();
        buyInput = (EditText) view.findViewById(R.id.stock_price_buy);
        sellInput = (EditText) view.findViewById(R.id.stock_price_sell);
        quantityInput = (EditText) view.findViewById(R.id.stock_quantity);
        reset = (Button)view.findViewById(R.id.reset);
        calculate = (Button) view.findViewById(R.id.calculate);
    }

    private void categoryListener(){
        selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = CATEGORIES[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectCategory.setSelection(0);
            }
        });
    }

    private void exchangeListener(final View view){
        selectExchange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedExchange = (RadioButton) view.findViewById(checkedId);
                exchangeType = selectedExchange.getText().toString();
            }
        });
    }

    private void resetListener(){
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory.setSelection(0);
                selectExchange.check(R.id.bseExchange);
                buyInput.setText("");
                sellInput.setText("");
                quantityInput.setText("");
            }
        });
    }

    private void calculateListener(final View view){
        AppMain.hideKeyboard(getActivity(), calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = (String) selectCategory.getSelectedItem();
                exchangeType = ((RadioButton) view.findViewById(
                        selectExchange.getCheckedRadioButtonId())).getText().toString();
                String buy = buyInput.getText().toString();
                String sell = sellInput.getText().toString();
                String quantity1 = quantityInput.getText().toString();
                if(!buy.equals("") && !sell.equals("") && !quantity1.equals("")){
                    buyPrice =  Double.parseDouble(buy);
                    sellPrice = Double.parseDouble(sell);
                    quantity = Double.parseDouble(quantity1);
                    boolean isFlatRateUsed;
                    double brokerageFlatCharges, brokeragePercentage,
                            totalBrokerage, serviceTax,
                            sttCharges, exchangeTransactionCharges, stampDutyCharges;
                    if(category.equals(StockPriceActivity.CATEGORIES[0])){
                        isFlatRateUsed = AppMain.getBoolPref(getContext(), R.string.prefs_brokerage_delivery_use_flat_charges_key,
                                R.bool.prefs_brokerage_delivery_use_flat_charges_default);
                        brokerageFlatCharges = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_delivery_flat_charges_key),
                                getResources().getString(R.string.prefs_brokerage_delivery_flat_charges_default)));
                        brokeragePercentage = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_delivery_percent_key),
                                getResources().getString(R.string.prefs_brokerage_delivery_percent_default)));
                    }else if(category.equals(StockPriceActivity.CATEGORIES[1])){
                        isFlatRateUsed = sharedPreferences.getBoolean(
                                getResources().getString(R.string.prefs_brokerage_intraday_use_flat_charges_key),
                                getResources().getBoolean(R.bool.prefs_brokerage_intraday_use_flat_charges_default));
                        brokerageFlatCharges = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_intraday_flat_charges_key),
                                getResources().getString(R.string.prefs_brokerage_intraday_flat_charges_default)));
                        brokeragePercentage = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_intraday_percent_key),
                                getResources().getString(R.string.prefs_brokerage_intraday_percent_default)));
                    }else if(category.equals(StockPriceActivity.CATEGORIES[2])){
                        isFlatRateUsed = sharedPreferences.getBoolean(
                                getResources().getString(R.string.prefs_brokerage_futures_use_flat_charges_key),
                                getResources().getBoolean(R.bool.prefs_brokerage_futures_use_flat_charges_default));
                        brokerageFlatCharges = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_futures_flat_rate_key),
                                getResources().getString(R.string.prefs_brokerage_futures_flat_charges_default)));
                        brokeragePercentage = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_futures_percent_key),
                                getResources().getString(R.string.prefs_brokerage_futures_percent_default)));
                    }else if(category.equals(StockPriceActivity.CATEGORIES[3])){
                        isFlatRateUsed = sharedPreferences.getBoolean(
                                getResources().getString(R.string.prefs_brokerage_options_use_flat_charges_key),
                                getResources().getBoolean(R.bool.prefs_brokerage_options_use_flat_charges_default));
                        brokerageFlatCharges = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_delivery_flat_charges_key),
                                getResources().getString(R.string.prefs_brokerage_delivery_flat_charges_default)));
                        brokeragePercentage = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_delivery_percent_key),
                                getResources().getString(R.string.prefs_brokerage_delivery_percent_default)));
                    }else if(category.equals(StockPriceActivity.CATEGORIES[4])){
                        brokerageFlatCharges = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_currency_minimum_key),
                                getResources().getString(R.string.prefs_brokerage_currency_minimum_default)));
                        brokeragePercentage = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_currency_percent_key),
                                getResources().getString(R.string.prefs_brokerage_currency_percent_default)));
                    }else{
                        brokerageFlatCharges = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_commodities_minimum_key),
                                getResources().getString(R.string.prefs_brokerage_commodities_minimum_default)));
                        brokeragePercentage = Double.parseDouble(sharedPreferences.getString(
                                getResources().getString(R.string.prefs_brokerage_commodities_percent_key),
                                getResources().getString(R.string.prefs_brokerage_commodities_percent_default)));
                    }
                }else{
                    AppMain.dialogBuilder(getContext(), "Error", "Fill all fields", "OK").create().show();
                }

            }
        });
    }

}
