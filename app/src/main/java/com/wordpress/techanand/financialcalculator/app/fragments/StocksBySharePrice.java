package com.wordpress.techanand.financialcalculator.app.fragments;


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

import java.util.Map;

import static com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksBySharePrice extends Fragment {

    private String category, exchangeType, NSEString, BSEString;
    private Spinner selectCategory;
    private RadioGroup selectExchange;
    private EditText buyInput, sellInput, quantityInput;
    private Button reset, calculate;
    private SharedPreferences sharedPreferences;
    private TableLayout result;

    private TextView totalTurnoverTextView, breakEvenTextView, profitOrLossTextView,
            brokerageTextView, otherChargesView;

    //Preference Constants
    private boolean _isFlatRateUsed;
    private double _flatBrokerage, _brokeragePercent, _maxBrokerage,_serviceTax, _sebiCharges,
            _sttCharges, _exchangeTxCharges, _stampDutyMinimum, _stampDutyMaximum, _stampDutyPercentage;

    private double tBrokerage, tBuyPrice, tSellPrice, tQuantity, tTurnOver, tStt,
            tExchangeTxCharges, tServiceTax, tSEBICharges, tStampDutyCharges,
            profitOrLoss, breakEvenPrice, tCharges;

    private String[] categories;

    private Map<String, ?> preferences;

    public StocksBySharePrice() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = AppPreferences.preferences(getContext());
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

        result = (TableLayout) view.findViewById(R.id.resultsTable);
        result.setVisibility(View.GONE);
        breakEvenTextView = (TextView) view.findViewById(R.id.breakEvenPrice);
        profitOrLossTextView = (TextView) view.findViewById(R.id.profitOrLoss);
        totalTurnoverTextView = (TextView) view.findViewById(R.id.totalTurnOver);
        brokerageTextView = (TextView) view.findViewById(R.id.brokerage);
        otherChargesView = (TextView) view.findViewById(R.id.otherCharges);
        /*sttTextView = (TextView) view.findViewById(R.id.sttCharges);
        txChargesView = (TextView) view.findViewById(R.id.txCharges);
        serviceTaxView = (TextView) view.findViewById(R.id.serviceTax);
        sebiChargesView = (TextView) view.findViewById(R.id.sebiCharges);*/

        calcBrokeragePrefs();
        calcExchangePrefs();
        calcTurnoverPrefs();
        calcStampDutyPrefs();
        calculateAmount(view, true);

    }

    private void categoryListener(){
        selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = CATEGORIES[position];
                calcBrokeragePrefs();
                calcStampDutyPrefs();
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
                calcTurnoverPrefs();
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
                result.setVisibility(View.GONE);
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
        String quantity1 = quantityInput.getText().toString();
        if(quantity1.equals("")){
            tQuantity = 0;
        }else{
            tQuantity = Double.parseDouble(quantity1);
        }
        boolean isBuyPriceDefined = false, isSellPriceDefined = false;
        if(!buy.equals("")){
            isBuyPriceDefined = true;
            tBuyPrice =  Double.parseDouble(buy) * tQuantity ;
            breakEvenTextView.setVisibility(View.VISIBLE);
            profitOrLossTextView.setVisibility(View.VISIBLE);
        }else{
            tBuyPrice =  0;
            breakEvenTextView.setVisibility(View.INVISIBLE);
            profitOrLossTextView.setVisibility(View.INVISIBLE);
        }
        if(!sell.equals("")){
            isSellPriceDefined = true;
            tSellPrice = Double.parseDouble(sell) * tQuantity;
        }else{
            tSellPrice = 0;
            breakEvenTextView.setVisibility(View.INVISIBLE);
            profitOrLossTextView.setVisibility(View.INVISIBLE);
        }
        if((isBuyPriceDefined || isSellPriceDefined) && !quantity1.equals("")){
            result.setVisibility(View.VISIBLE);

            tTurnOver = (tBuyPrice+tSellPrice);

            //Brokerage
            if(_isFlatRateUsed){
                if(_flatBrokerage <= 0){
                    _flatBrokerage = 0;
                }
                tBrokerage = _flatBrokerage;
            }else {
                tBrokerage = (tTurnOver * _brokeragePercent)/100;
                if(_maxBrokerage > 0){
                    if(tBrokerage > _maxBrokerage){
                        tBrokerage = _maxBrokerage;
                    }
                }
            }

            //Security Transaction Charges
            if(category.equals(categories[0])){
                tStt = (tTurnOver * _sttCharges)/100;
            }else if(category.equals(categories[4]) || category.equals(categories[5])){
                tStt = 0;
            }else{
                tStt = (tSellPrice * _sttCharges)/100;
            }

            //Exchange Charges
            tExchangeTxCharges = (tTurnOver * _exchangeTxCharges)/100;

            //SEBI Charges
            tSEBICharges = (tTurnOver * _sebiCharges)/10000000;

            //Stampduty Charges
            tStampDutyCharges = (tTurnOver * _stampDutyPercentage)/100;
            if(tStampDutyCharges < _stampDutyMinimum){
                tStampDutyCharges = _stampDutyMinimum;
            }
            if(tStampDutyCharges > _stampDutyMaximum){
                tStampDutyCharges = _stampDutyMaximum;
            }

            //Service Tax
            tServiceTax = ((tBrokerage + tExchangeTxCharges) * _serviceTax)/100;

            //Profit or Loss
            profitOrLoss = tSellPrice - tBuyPrice - tBrokerage
                    - tStt - tExchangeTxCharges - tSEBICharges - tStampDutyCharges - tServiceTax;

            totalTurnoverTextView.setText(tTurnOver+"");
            brokerageTextView.setText(DIGIT_FORMAT.format(tBrokerage)+"");
            double otherCharges = tStt+tExchangeTxCharges+tSEBICharges+tStampDutyCharges+tServiceTax;
            otherChargesView.setText(DIGIT_FORMAT.format(otherCharges)+"");
            profitOrLossTextView.setText(DIGIT_FORMAT.format(profitOrLoss)+"");

        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(), "Error", "Fill all fields !!!", "OK").create().show();
        }
    }

    private void calcBrokeragePrefs(){
        if(category.equals(categories[0])){// Delivery
            _isFlatRateUsed = (Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_use_flat_charges_key));
            _flatBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_flat_charges_key));
            _brokeragePercent = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_percent_key));
            _maxBrokerage = -1;
            _sttCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_delivery_key));
        }else if(category.equals(categories[1])){ //Intraday
            _isFlatRateUsed = (Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_use_flat_charges_key));
            _flatBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_flat_charges_key));
            _maxBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_maximum_key));
            _brokeragePercent = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_percent_key));
            _sttCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_intraday_key));
        }else if(category.equals(categories[2])){ //Futures
            _isFlatRateUsed = (Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_use_flat_charges_key));
            _flatBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_flat_rate_key));
            _maxBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_maximum_key));
            _brokeragePercent = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_percent_key));
            _sttCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_futures_key));
        }else if(category.equals(categories[3])){ //Options
            _isFlatRateUsed = (Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_use_flat_charges_key));
            _flatBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_flat_charges_key));
            _maxBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_maximum_key));
            _brokeragePercent = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_percent_key));
            _sttCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_options_key));
        }else if(category.equals(categories[4])){ //Currency - Futures
            _maxBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_futures_maximum_key));
            _brokeragePercent = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_futures_percent_key));
            _sttCharges = 0;
        }else if(category.equals(categories[5])){ //Currency - Options
            _maxBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_options_maximum_key));
            _brokeragePercent = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_options_percent_key));
            _sttCharges = 0;
        }else{ //Commodities
            _flatBrokerage = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_commodities_maximum_key));
            _brokeragePercent = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_commodities_percent_key));
            _sttCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_commodities_key));
        }
    }

    private void calcExchangePrefs(){
        _serviceTax = (Double)preferences.get(AppMain.getResource(getContext(),
                R.string.prefs_exchange_service_tax_key));
        _sebiCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                R.string.prefs_sebi_charges_key));
    }

    private void calcTurnoverPrefs(){
        if(exchangeType.equals(NSEString)){
            if(category.equals(categories[0])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_delivery_key));
            }else if(category.equals(categories[1])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_intraday_key));
            }else if(category.equals(categories[2])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_futures_key));
            }else if(category.equals(categories[3])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_options_key));
            }else if(category.equals(categories[4])){
                _exchangeTxCharges = 0;
            }else if(category.equals(categories[5])){
                _exchangeTxCharges = 0;
            }

        }else if(exchangeType.equals(BSEString)){
            if(category.equals(categories[0])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_delivery_key));
            }else if(category.equals(categories[1])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_intraday_key));
            }else if(category.equals(categories[2])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_futures_key));
            }else if(category.equals(categories[3])){
                _exchangeTxCharges = (Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_options_key));
            }else if(category.equals(categories[4])){
                _exchangeTxCharges = 0;
            }else if(category.equals(categories[5])){
                _exchangeTxCharges = 0;
            }
        }else{
            _exchangeTxCharges = 0;
        }
    }

    private void calcStampDutyPrefs(){
        if(category.equals(categories[0])){
            _stampDutyMinimum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_min_key,
                    R.string.prefs_exchange_stampduty_delivery_min_default);
            _stampDutyMaximum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_max_key,
                    R.string.prefs_exchange_stampduty_delivery_max_default);
            _stampDutyPercentage = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_percent_key,
                    R.string.prefs_exchange_stampduty_delivery_percent_default);
        }else if(category.equals(categories[1])){
            _stampDutyMinimum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_min_key,
                    R.string.prefs_exchange_stampduty_intraday_min_default);
            _stampDutyMaximum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_max_key,
                    R.string.prefs_exchange_stampduty_intraday_max_default);
            _stampDutyPercentage = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_percent_key,
                    R.string.prefs_exchange_stampduty_intraday_percent_default);
        }else if(category.equals(categories[2])){
            _stampDutyMinimum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_futures_min_key,
                    R.string.prefs_exchange_stampduty_futures_min_default);
            _stampDutyMaximum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_futures_max_key,
                    R.string.prefs_exchange_stampduty_futures_max_default);
            _stampDutyPercentage = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_futures_percent_key,
                    R.string.prefs_exchange_stampduty_futures_percent_default);
        }else if(category.equals(categories[3])){
            _stampDutyMinimum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_options_min_key,
                    R.string.prefs_exchange_stampduty_options_min_default);
            _stampDutyMaximum = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_options_max_key,
                    R.string.prefs_exchange_stampduty_options_max_default);
            _stampDutyPercentage = AppMain.getDoublePrefFromString(getContext(),
                    R.string.prefs_exchange_stampduty_options_percent_key,
                    R.string.prefs_exchange_stampduty_options_percent_default);
        }else if(category.equals(categories[4])){
            _stampDutyMinimum = 0;
            _stampDutyMaximum = 0;
            _stampDutyPercentage = 0;
        }else{
            _stampDutyMinimum = 0;
            _stampDutyMaximum = 0;
            _stampDutyPercentage = 0;
        }
    }
}
