package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 06/12/2016
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
import android.widget.TableRow;
import android.widget.TextView;;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.AppPreferences;
import com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity;
import com.wordpress.techanand.financialcalculator.app.models.StockPreferencesObject;
import com.wordpress.techanand.financialcalculator.app.models.StockObject;

import java.util.Map;

import static com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity.*;

public class StocksBySharePrice extends Fragment {

    public static final String ID = StocksBySharePrice.class.getName();

    private String category, exchangeType, NSEString, BSEString;
    private Spinner selectCategory;
    private RadioGroup selectExchange;
    private EditText buyInput, sellInput, quantityInput;
    private Button reset, calculate;
    private TableLayout resultsTable;

    private SharedPreferences sharedPreferences;

    private StockPreferencesObject stockPreferencesObject;
    private StockObject stockObjectData;

    private String[] categories;
    private Map<String, ?> preferences;

    private boolean isCalcClicked;

    public StocksBySharePrice() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        Activity activity = this.getActivity();
        Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Stock Price Calculator");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.stocks_by_share_price, container, false);
        categories = StockPriceActivity.CATEGORIES;

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

        resultsTable = (TableLayout) view.findViewById(R.id.resultsTable);
        resultsTable.setVisibility(View.GONE);

        NSEString = getResources().getString(R.string.app_stocks_nse);
        BSEString = getResources().getString(R.string.app_stocks_bse);

        preferences = AppPreferences.preferences(getContext());
        stockPreferencesObject = new StockPreferencesObject();
        stockObjectData = new StockObject();

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

        selectExchange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedExchange = (RadioButton) view.findViewById(checkedId);
                exchangeType = selectedExchange.getText().toString();
                calcPreferences();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategory.setSelection(0);
                selectExchange.check(R.id.bseExchange);
                buyInput.setText("");
                sellInput.setText("");
                quantityInput.setText("");
                //stockListener.reset();
                isCalcClicked = false;
                resultsTable.setVisibility(View.GONE);
            }
        });

        AppMain.hideKeyboard(getActivity(), calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAmount(false, true);
            }
        });

        calcPreferences();
        calculateAmount(true, false);

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        buyInput.setText(buyInput.getText().toString());
        sellInput.setText(sellInput.getText().toString());
        quantityInput.setText(quantityInput.getText().toString());
        stockObjectData.getBuyPrice();
        calcPreferences();
        calculateAmount(true, isCalcClicked);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*try{
            //stockListener = (StockListener)context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString() + " must implement CalculateStockListener");
        }*/
    }

    private void calculateAmount(boolean isFromInitialLoad, boolean isCalcClicked){
        this.isCalcClicked = isCalcClicked;
        if(!this.isCalcClicked){
            return;
        }
        String buy = buyInput.getText().toString();
        String sell = sellInput.getText().toString();
        String quantity = quantityInput.getText().toString();
        if(!isFromInitialLoad && (buy.equals("") || sell.equals("")) && quantity.equals("")) {
            AppMain.dialogBuilder(getContext(), "Error", "Fill all fields !!!", "OK").create().show();
        }
        if(buy != null && !buy.equals("")){
            stockObjectData.setBuyPrice(Double.parseDouble(buy));
        }else{
            stockObjectData.setBuyPrice(0);
        }

        if(sell != null && !sell.equals("")){
            stockObjectData.setSellPrice(Double.parseDouble(sell));
        }else{
            stockObjectData.setSellPrice(0);
        }

        if(quantity != null && !quantity.equals("")){
            stockObjectData.setQuantity(Double.parseDouble(quantity));
        }else{
            stockObjectData.setQuantity(0);
        }

        stockObjectData.setCategory(category);
        stockObjectData.setExchange(exchangeType);

        //stockListener.calculate(stockObjectData, stockPreferencesObject, isFromInitialLoad, isCalcClicked);
        calculate(stockObjectData, stockPreferencesObject, isFromInitialLoad, isCalcClicked);

    }

    public void calculate(StockObject stockObjectData, StockPreferencesObject stockPreferencesObject, boolean isFromInitialLoad, boolean isCalcClicked) {
        if(isFromInitialLoad && (stockObjectData.getSellPrice() <=0.0 || stockObjectData.getBuyPrice() <= 0.0) && stockObjectData.getQuantity() <=0.0){
            return;
        }
        double tBuy, tSell, tTurnover, tQuantity, tBrokerage, tStt, tServiceTax, tExchangeTxCharges, tSEBICharges, tStampDutyCharges, profitOrLoss, breakEven;
        tQuantity = stockObjectData.getQuantity();
        tBuy = stockObjectData.getBuyPrice() * tQuantity;
        tSell = stockObjectData.getSellPrice() * tQuantity;
        tTurnover = tBuy + tSell;

        //todo: which text views to  display based on buy price and sell price

        //Brokerage
        if(stockPreferencesObject.isFlatRateUsed()){
            if(stockPreferencesObject.getFlatBrokerage() > 0){
                tBrokerage = stockPreferencesObject.getFlatBrokerage();
            }else{
                tBrokerage = 0;
            }
        }else{
            tBrokerage = stockPreferencesObject.getBrokeragePercent() * 0.01 * tTurnover;
        }
        stockObjectData.setBrokerage(tBrokerage);

        //Stt charges
        if(stockObjectData.getCategory().equals(CATEGORIES[0])){
            tStt = tTurnover * stockPreferencesObject.getSttCharges() * 0.01;
        }else if(stockObjectData.getCategory().equals(CATEGORIES[4]) || stockObjectData.getCategory().equals(CATEGORIES[5])){
            tStt = 0;
        }else{
            tStt = tSell * stockPreferencesObject.getSttCharges() * 0.01;
        }
        stockObjectData.setSttCharges(tStt);

        //Exchange Charges
        tExchangeTxCharges = tTurnover * stockPreferencesObject.getExchangeTxCharges() * 0.01;
        stockObjectData.setExchangeTxCharges(tExchangeTxCharges);

        //SEBI Charges
        tSEBICharges = tTurnover * stockPreferencesObject.getSebiCharges() * 0.0000001;
        stockObjectData.setSebiCharges(tSEBICharges);

        //Stampduty Charges
        tStampDutyCharges = tTurnover * stockPreferencesObject.getStampDutyPercentage() * 0.01;
        if (tStampDutyCharges < stockPreferencesObject.getStampDutyMinimum()) {
            tStampDutyCharges = stockPreferencesObject.getStampDutyMinimum();
        }
        if (tStampDutyCharges > stockPreferencesObject.getStampDutyMaximum()) {
            tStampDutyCharges = stockPreferencesObject.getStampDutyMaximum();
        }
        stockObjectData.setStampDutyCharges(tStampDutyCharges);

        //Service Tax
        tServiceTax = (tBrokerage + tExchangeTxCharges) * stockPreferencesObject.getServiceTax() * 0.01;
        stockObjectData.setServiceCharges(tServiceTax);

        breakEven = (tTurnover + tBrokerage + tStt + tExchangeTxCharges + tSEBICharges + tStampDutyCharges + tServiceTax) / tQuantity;

        if(tBuy > 0 && tSell > 0){
            breakEven = breakEven - (stockObjectData.getBuyPrice()+ stockObjectData.getSellPrice());
        }else{
            double price = 0.0;
            if(tBuy > 0){
                price = stockObjectData.getBuyPrice();
            }
            if(tSell > 0){
                price = stockObjectData.getSellPrice();
            }
            breakEven = breakEven - price;
        }
        stockObjectData.setBreakEven(breakEven);

        profitOrLoss = tSell - tBuy - tBrokerage
                - tStt - tExchangeTxCharges - tSEBICharges - tStampDutyCharges - tServiceTax;
        stockObjectData.setProfitOrLoss(profitOrLoss);
        /*getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.slide_left, R.anim.slide_right).commit();*/
        if((stockObjectData.getSellPrice()>0 || stockObjectData.getBuyPrice()>0) && stockObjectData.getQuantity()>0){
            displayResult(stockObjectData, stockPreferencesObject);
            resultsTable.setVisibility(View.VISIBLE);
        }

    }

    public void displayResult(StockObject stockObjectData, StockPreferencesObject stockPreferencesObject){
        String turnover = MainPrefs.getFormattedNumber((stockObjectData.getBuyPrice()+ stockObjectData.getSellPrice()) * stockObjectData.getQuantity());
        String brokerage = "0";
        String exchgTxPlusTaxes;
        String breakEven;
        String profitLoss;
        double totalCharges = stockObjectData.getBrokerage() + stockObjectData.getSttCharges() +
                stockObjectData.getExchangeTxCharges() + stockObjectData.getSebiCharges() +
                stockObjectData.getServiceCharges() + stockObjectData.getStampDutyCharges();
        ((TextView)getView().findViewById(R.id.totalTurnOver)).setText(turnover);
        if(stockObjectData.getBrokerage() > 0){
            brokerage = MainPrefs.getFormattedNumber(stockObjectData.getBrokerage());
        }
        ((TextView)getView().findViewById(R.id.brokerage)).setText(brokerage);
        exchgTxPlusTaxes = MainPrefs.getFormattedNumber(
                (stockObjectData.getSttCharges()+ stockObjectData.getExchangeTxCharges()+ stockObjectData.getServiceCharges()+ stockObjectData.getSebiCharges()+ stockObjectData.getStampDutyCharges()));
        ((TextView)getView().findViewById(R.id.otherCharges)).setText(exchgTxPlusTaxes);
        TextView breakEvenLabel = (TextView) getView().findViewById(R.id.break_even_label);
        TextView profitLossLabel = (TextView) getView().findViewById(R.id.prfit_loss_label);
        TableRow profitLossRow = (TableRow) getView().findViewById(R.id.net_profit_loss);
        double profitLossVal = stockObjectData.getProfitOrLoss();
        if(stockObjectData.getBuyPrice()>0 && stockObjectData.getSellPrice()>0){
            profitLossLabel.setText("Net Profit/Loss");
            profitLossVal = stockObjectData.getProfitOrLoss();
        }else if(stockObjectData.getBuyPrice()>0){
            profitLossLabel.setText("Net Worth Bought");
            profitLossVal = (stockObjectData.getBuyPrice() * stockObjectData.getQuantity()) - totalCharges;
        }else if(stockObjectData.getSellPrice()>0){
            profitLossLabel.setText("Net Worth Sold");
            profitLossVal = (stockObjectData.getSellPrice() * stockObjectData.getQuantity()) - totalCharges;
        }
        breakEven = MainPrefs.getFormattedNumber(stockObjectData.getBreakEven());
        ((TextView)getView().findViewById(R.id.breakEvenPrice)).setText(breakEven);
        profitLoss = MainPrefs.getFormattedNumber(profitLossVal);
        ((TextView)getView().findViewById(R.id.profitOrLoss)).setText(profitLoss);
    }

    private void calcPreferences(){
        //Brokerage charges
        if(category.equals(categories[0])){// Delivery
            stockPreferencesObject.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_use_flat_charges_key)));
            stockPreferencesObject.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_flat_charges_key)));
            stockPreferencesObject.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_delivery_percent_key)));
            stockPreferencesObject.setMaxBrokerage(-1);
            stockPreferencesObject.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_delivery_key)));
        }else if(category.equals(categories[1])){ //Intraday
            stockPreferencesObject.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_use_flat_charges_key)));
            stockPreferencesObject.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_flat_charges_key)));
            stockPreferencesObject.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_maximum_key)));
            stockPreferencesObject.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_intraday_percent_key)));
            stockPreferencesObject.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_intraday_key)));
        }else if(category.equals(categories[2])){ //Futures
            stockPreferencesObject.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_use_flat_charges_key)));
            stockPreferencesObject.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_flat_rate_key)));
            stockPreferencesObject.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_maximum_key)));
            stockPreferencesObject.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_futures_percent_key)));
            stockPreferencesObject.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_futures_key)));
        }else if(category.equals(categories[3])){ //Options
            stockPreferencesObject.setFlatRateUsed((Boolean)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_use_flat_charges_key)));
            stockPreferencesObject.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_flat_charges_key)));
            stockPreferencesObject.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_maximum_key)));
            stockPreferencesObject.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_options_percent_key)));
            stockPreferencesObject.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_options_key)));
        }else if(category.equals(categories[4])){ //Currency - Futures
            stockPreferencesObject.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_futures_maximum_key)));
            stockPreferencesObject.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_futures_percent_key)));
            stockPreferencesObject.setSttCharges(0);
        }else if(category.equals(categories[5])){ //Currency - Options
            stockPreferencesObject.setMaxBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_options_maximum_key)));
            stockPreferencesObject.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_currency_options_percent_key)));
            stockPreferencesObject.setSttCharges(0);
        }else{ //Commodities
            stockPreferencesObject.setFlatBrokerage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_commodities_maximum_key)));
            stockPreferencesObject.setBrokeragePercent((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_brokerage_commodities_percent_key)));
            stockPreferencesObject.setSttCharges((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stt_commodities_key)));
        }

        stockPreferencesObject.setServiceTax((Double)preferences.get(AppMain.getResource(getContext(),
                R.string.prefs_exchange_service_tax_key)));
        stockPreferencesObject.setSebiCharges((Double)preferences.get(AppMain.getResource(getContext(),
                R.string.prefs_sebi_charges_key)));

        //Exchange charges
        if(exchangeType.equals(NSEString)){
            if(category.equals(categories[0])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_delivery_key)));
            }else if(category.equals(categories[1])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_intraday_key)));
            }else if(category.equals(categories[2])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_futures_key)));
            }else if(category.equals(categories[3])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_nsecharges_options_key)));
            }else if(category.equals(categories[4])){
                stockPreferencesObject.setExchangeTxCharges(0);
            }else if(category.equals(categories[5])){
                stockPreferencesObject.setExchangeTxCharges(0);
            }

        }else if(exchangeType.equals(BSEString)){
            if(category.equals(categories[0])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_delivery_key)));
            }else if(category.equals(categories[1])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_intraday_key)));
            }else if(category.equals(categories[2])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_futures_key)));
            }else if(category.equals(categories[3])){
                stockPreferencesObject.setExchangeTxCharges((Double)preferences.get(AppMain.getResource(getContext(),
                        R.string.prefs_exchange_bsecharges_options_key)));
            }else if(category.equals(categories[4])){
                stockPreferencesObject.setExchangeTxCharges(0);
            }else if(category.equals(categories[5])){
                stockPreferencesObject.setExchangeTxCharges(0);
            }
        }else{
            stockPreferencesObject.setExchangeTxCharges(0);
        }

        //Stampduty charges
        if(category.equals(categories[0])){
            stockPreferencesObject.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_min_key)));
            stockPreferencesObject.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_max_key)));
            stockPreferencesObject.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_delivery_percent_key)));
        }else if(category.equals(categories[1])){
            stockPreferencesObject.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_min_key)));
            stockPreferencesObject.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_max_key)));
            stockPreferencesObject.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_intraday_percent_key)));
        }else if(category.equals(categories[2])){
            stockPreferencesObject.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_futures_min_key)));
            stockPreferencesObject.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_futures_max_key)));
            stockPreferencesObject.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_futures_percent_key)));
        }else if(category.equals(categories[3])){
            stockPreferencesObject.setStampDutyMinimum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_options_min_key)));
            stockPreferencesObject.setStampDutyMaximum((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_options_max_key)));
            stockPreferencesObject.setStampDutyPercentage((Double)preferences.get(AppMain.getResource(getContext(),
                    R.string.prefs_exchange_stampduty_options_percent_key)));
        }else {
            stockPreferencesObject.setStampDutyMinimum(0);
            stockPreferencesObject.setStampDutyMaximum(0);
            stockPreferencesObject.setStampDutyPercentage(0);
        }
    }
}
