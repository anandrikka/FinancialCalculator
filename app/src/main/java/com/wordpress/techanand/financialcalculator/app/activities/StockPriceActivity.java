package com.wordpress.techanand.financialcalculator.app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksBySharePrice;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksBySharePriceResult;
import com.wordpress.techanand.financialcalculator.app.models.StockPreferences;
import com.wordpress.techanand.financialcalculator.app.models.Stock;

public class StockPriceActivity extends AppCompatActivity implements StocksBySharePrice.StockListener{

    public static final String[] CATEGORIES = {
            "Equity - Delivery",
            "Equity - Intraday",
            "Equity - Futures",
            "Equity - Options",
            "Currency - Futures",
            "Currency - Options",
            "Commodities"
    };

    private StocksBySharePrice stocksBySharePrice;
    private StocksBySharePriceResult stocksBySharePriceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_price);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        stocksBySharePrice = (StocksBySharePrice)getSupportFragmentManager().findFragmentById(R.id.stock_form);
        stocksBySharePriceResult = (StocksBySharePriceResult) getSupportFragmentManager().findFragmentById(R.id.stock_result);

        fragmentTransaction.hide(stocksBySharePriceResult).commit();

        /*getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.appArea, new StocksBySharePrice(), StockPriceActivity.class.getName())
                .addToBackStack(StockPriceActivity.class.getName())
                .commit();*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public void calculate(Stock stockData, StockPreferences stockPreferences, boolean isFromInitialLoad) {
        if(isFromInitialLoad && (stockData.getSellPrice() <=0.0 || stockData.getBuyPrice() <= 0.0) && stockData.getQuantity() <=0.0){
            return;
        }
        double tBuy, tSell, tTurnover, tQuantity, tBrokerage, tStt, tServiceTax, tExchangeTxCharges, tSEBICharges, tStampDutyCharges, profitOrLoss, breakEven;
        tQuantity = stockData.getQuantity();
        tBuy = stockData.getBuyPrice() * tQuantity;
        tSell = stockData.getSellPrice() * tQuantity;
        tTurnover = tBuy + tSell;

        //todo: which text views to  display based on buy price and sell price

        //Brokerage
        if(stockPreferences.isFlatRateUsed()){
            if(stockPreferences.getFlatBrokerage() > 0){
                tBrokerage = stockPreferences.getFlatBrokerage();
            }else{
                tBrokerage = 0;
            }
        }else{
            tBrokerage = stockPreferences.getBrokeragePercent() * 0.01 * tTurnover;
        }
        stockData.setBrokerage(tBrokerage);

        //Stt charges
        if(stockData.getCategory().equals(CATEGORIES[0])){
            tStt = tTurnover * stockPreferences.getSttCharges() * 0.01;
        }else if(stockData.getCategory().equals(CATEGORIES[4]) || stockData.getCategory().equals(CATEGORIES[5])){
            tStt = 0;
        }else{
            tStt = tSell * stockPreferences.getSttCharges() * 0.01;
        }
        stockData.setSttCharges(tStt);

        //Exchange Charges
        tExchangeTxCharges = tTurnover * stockPreferences.getExchangeTxCharges() * 0.01;
        stockData.setExchangeTxCharges(tExchangeTxCharges);

        //SEBI Charges
        tSEBICharges = tTurnover * stockPreferences.getSebiCharges() * 0.0000001;
        stockData.setSebiCharges(tSEBICharges);

        //Stampduty Charges
        tStampDutyCharges = tTurnover * stockPreferences.getStampDutyPercentage() * 0.01;
        if (tStampDutyCharges < stockPreferences.getStampDutyMinimum()) {
            tStampDutyCharges = stockPreferences.getStampDutyMinimum();
        }
        if (tStampDutyCharges > stockPreferences.getStampDutyMaximum()) {
            tStampDutyCharges = stockPreferences.getStampDutyMaximum();
        }
        stockData.setStampDutyCharges(tStampDutyCharges);

        //Service Tax
        tServiceTax = (tBrokerage + tExchangeTxCharges) * stockPreferences.getServiceTax() * 0.01;
        stockData.setServiceCharges(tServiceTax);

        breakEven = (tTurnover + tBrokerage + tStt + tExchangeTxCharges + tSEBICharges + tStampDutyCharges + tServiceTax) / tQuantity;

        if(tBuy > 0 && tSell > 0){
            breakEven = breakEven - (stockData.getBuyPrice()+stockData.getSellPrice());
        }else{
            double price = 0.0;
            if(tBuy > 0){
                price = stockData.getBuyPrice();
            }
            if(tSell > 0){
                price = stockData.getSellPrice();
            }
            breakEven = breakEven - price;
        }
        stockData.setBreakEven(breakEven);

        profitOrLoss = tSell - tBuy - tBrokerage
                - tStt - tExchangeTxCharges - tSEBICharges - tStampDutyCharges - tServiceTax;
        stockData.setProfitOrLoss(profitOrLoss);
        getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.slide_left, R.anim.slide_right).commit();
        if((stockData.getSellPrice()>0 || stockData.getBuyPrice()>0) && stockData.getQuantity()>0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(stocksBySharePriceResult)
                    .commit();
            stocksBySharePriceResult.displayResult(stockData, stockPreferences);
        }

    }

    @Override
    public void reset() {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(stocksBySharePriceResult)
                .commit();
    }


}
