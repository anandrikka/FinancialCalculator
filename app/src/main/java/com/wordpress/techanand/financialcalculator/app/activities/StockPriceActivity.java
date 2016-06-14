package com.wordpress.techanand.financialcalculator.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksBySharePrice;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksBySharePriceResult;
import com.wordpress.techanand.financialcalculator.app.models.StockObject;
import com.wordpress.techanand.financialcalculator.app.models.StockPreferencesObject;

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
        getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.slide_left, R.anim.slide_right).commit();
        if((stockObjectData.getSellPrice()>0 || stockObjectData.getBuyPrice()>0) && stockObjectData.getQuantity()>0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(stocksBySharePriceResult)
                    .commit();
            stocksBySharePriceResult.displayResult(stockObjectData, stockPreferencesObject);
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
