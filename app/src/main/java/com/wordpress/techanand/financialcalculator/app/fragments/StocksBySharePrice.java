package com.wordpress.techanand.financialcalculator.app.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
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
import android.widget.TableLayout;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.AppPreferences;
import com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity;
import com.wordpress.techanand.financialcalculator.app.models.StockPreferences;
import com.wordpress.techanand.financialcalculator.app.models.Stock;

import java.util.Map;

import static com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksBySharePrice extends Fragment {

    private StockListener stockListener;

    private String category, exchangeType, NSEString, BSEString;
    private Spinner selectCategory;
    private RadioGroup selectExchange;
    private EditText buyInput, sellInput, quantityInput;
    private Button reset, calculate;
    private SharedPreferences sharedPreferences;
    private TableLayout result;

    private TextView totalTurnoverTextView, breakEvenTextView, profitOrLossTextView,
            brokerageTextView, otherChargesView;

    private StockPreferences stockPreferences;
    private Stock stockData;
    //Preference Constants
   /* private boolean _isFlatRateUsed;
    private double _flatBrokerage, _brokeragePercent, _maxBrokerage,_serviceTax, _sebiCharges,
            _sttCharges, _exchangeTxCharges, _stampDutyMinimum, _stampDutyMaximum, _stampDutyPercentage;*/

    private double tBrokerage, tBuyPrice, tSellPrice, tQuantity, tTurnOver, tStt,
            tExchangeTxCharges, tServiceTax, tSEBICharges, tStampDutyCharges,
            profitOrLoss, breakEvenPrice, tCharges;

    private String[] categories;

    private Map<String, ?> preferences;

    public StocksBySharePrice() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            preferences = AppPreferences.preferences(getContext());
            stockPreferences = new StockPreferences();
            stockData = new Stock();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.stocks_by_share_price, container, false);
        setRetainInstance(true);
        categories = StockPriceActivity.CATEGORIES;
        initializeData(view);
        categoryListener();
        exchangeListener(view);
        resetListener();
        calculateListener(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            stockListener = (StockListener)context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString() + " must implement CalculateStockListener");
        }
    }

    private void initializeData(View view){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        selectCategory = (Spinner) view.findViewById(R.id.stock_category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, CATEGORIES);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        selectCategory.setAdapter(adapter);

        category = (String)selectCategory.getSelectedItem();
        selectExchange = (RadioGroup) view.findViewById(R.id.stock_exchange_choose);
        RadioButton selectedExchange = (RadioButton) view.findViewById(selectExchange.getCheckedRadioButtonId());
        exchangeType = selectedExchange.getText().toString();
        buyInput = (EditText) view.findViewById(R.id.stock_price_buy);
        sellInput = (EditText) view.findViewById(R.id.stock_price_sell);
        quantityInput = (EditText) view.findViewById(R.id.stock_quantity);
        reset = (Button)view.findViewById(R.id.reset);
        calculate = (Button) view.findViewById(R.id.calculate);

        NSEString = getResources().getString(R.string.app_stocks_nse);
        BSEString = getResources().getString(R.string.app_stocks_bse);

        calcPreferences();
        calculateAmount(view, true);

    }

    private void categoryListener(){
        selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = CATEGORIES[position];
                calcPreferences();
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
                calcPreferences();
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
                stockListener.reset();
                //result.setVisibility(View.GONE);
            }
        });
    }

    private void calculateListener(final View view){
        AppMain.hideKeyboard(getActivity(), calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAmount(view, false);
            }
        });
    }

    private void calculateAmount(View view, boolean isFromInitialLoad){
        String buy = buyInput.getText().toString();
        String sell = sellInput.getText().toString();
        String quantity = quantityInput.getText().toString();
        if(!isFromInitialLoad && (buy.equals("") || sell.equals("")) && quantity.equals("")) {
            AppMain.dialogBuilder(getContext(), "Error", "Fill all fields !!!", "OK").create().show();
        }
        if(buy != null && !buy.equals("")){
            stockData.setBuyPrice(Double.parseDouble(buy));
        }else{
            stockData.setBuyPrice(0);
        }

        if(sell != null && !sell.equals("")){
            stockData.setSellPrice(Double.parseDouble(sell));
        }else{
            stockData.setSellPrice(0);
        }

        if(quantity != null && !quantity.equals("")){
            stockData.setQuantity(Double.parseDouble(quantity));
        }else{
            stockData.setQuantity(0);
        }

        stockData.setCategory(category);
        stockData.setExchange(exchangeType);

        stockListener.calculate(stockData, stockPreferences, isFromInitialLoad);

    }

    private void calcPreferences(){
        //Brokerage charges
        if(category.equals(categories[0])){// Delivery
            stockPreferences.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_use_flat_charges_key)));
            stockPreferences.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_flat_charges_key)));
            stockPreferences.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_percent_key)));
            stockPreferences.setMaxBrokerage(-1);
            stockPreferences.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_delivery_key)));
        }else if(category.equals(categories[1])){ //Intraday
            stockPreferences.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_use_flat_charges_key)));
            stockPreferences.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_flat_charges_key)));
            stockPreferences.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_maximum_key)));
            stockPreferences.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_percent_key)));
            stockPreferences.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_intraday_key)));
        }else if(category.equals(categories[2])){ //Futures
            stockPreferences.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_use_flat_charges_key)));
            stockPreferences.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_flat_rate_key)));
            stockPreferences.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_maximum_key)));
            stockPreferences.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_percent_key)));
            stockPreferences.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_futures_key)));
        }else if(category.equals(categories[3])){ //Options
            stockPreferences.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_use_flat_charges_key)));
            stockPreferences.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_flat_charges_key)));
            stockPreferences.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_maximum_key)));
            stockPreferences.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_percent_key)));
            stockPreferences.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_options_key)));
        }else if(category.equals(categories[4])){ //Currency - Futures
            stockPreferences.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_futures_maximum_key)));
            stockPreferences.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_futures_percent_key)));
            stockPreferences.setSttCharges(0);
        }else if(category.equals(categories[5])){ //Currency - Options
            stockPreferences.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_options_maximum_key)));
            stockPreferences.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_options_percent_key)));
            stockPreferences.setSttCharges(0);
        }else{ //Commodities
            stockPreferences.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_commodities_maximum_key)));
            stockPreferences.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_commodities_percent_key)));
            stockPreferences.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_commodities_key)));
        }

        stockPreferences.setServiceTax((Double)preferences.get(AppMain.getResource(getContext(),
                R.string.prefs_exchange_service_tax_key)));
        stockPreferences.setSebiCharges((Double)preferences.get(AppMain.getResource(getContext(),
                R.string.prefs_sebi_charges_key)));

        //Exchange charges
        if(exchangeType.equals(NSEString)){
            if(category.equals(categories[0])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_delivery_key)));
            }else if(category.equals(categories[1])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_intraday_key)));
            }else if(category.equals(categories[2])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_futures_key)));
            }else if(category.equals(categories[3])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_options_key)));
            }else if(category.equals(categories[4])){
                stockPreferences.setExchangeTxCharges(0);
            }else if(category.equals(categories[5])){
                stockPreferences.setExchangeTxCharges(0);
            }

        }else if(exchangeType.equals(BSEString)){
            if(category.equals(categories[0])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_delivery_key)));
            }else if(category.equals(categories[1])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_intraday_key)));
            }else if(category.equals(categories[2])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_futures_key)));
            }else if(category.equals(categories[3])){
                stockPreferences.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_options_key)));
            }else if(category.equals(categories[4])){
                stockPreferences.setExchangeTxCharges(0);
            }else if(category.equals(categories[5])){
                stockPreferences.setExchangeTxCharges(0);
            }
        }else{
            stockPreferences.setExchangeTxCharges(0);
        }

        //Stampduty charges
        if(category.equals(categories[0])){
            stockPreferences.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_min_key)));
            stockPreferences.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_max_key)));
            stockPreferences.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_percent_key)));
        }else if(category.equals(categories[1])){
            stockPreferences.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_min_key)));
            stockPreferences.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_max_key)));
            stockPreferences.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_percent_key)));
        }else if(category.equals(categories[2])){
            stockPreferences.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_futures_min_key)));
            stockPreferences.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_futures_max_key)));
            stockPreferences.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_futures_percent_key)));
        }else if(category.equals(categories[3])){
            stockPreferences.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_options_min_key)));
            stockPreferences.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_options_max_key)));
            stockPreferences.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_options_percent_key)));
        }else {
            stockPreferences.setStampDutyMinimum(0);
            stockPreferences.setStampDutyMaximum(0);
            stockPreferences.setStampDutyPercentage(0);
        }
    }

    public interface StockListener {
        public void calculate(Stock stock, StockPreferences stockPreferences, boolean isFromInitialLoad);
        public void reset();
    }
}
