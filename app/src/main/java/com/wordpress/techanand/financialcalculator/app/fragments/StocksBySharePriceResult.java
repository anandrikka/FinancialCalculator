package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.models.Stock;
import com.wordpress.techanand.financialcalculator.app.models.StockPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksBySharePriceResult extends Fragment {


    public StocksBySharePriceResult() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_by_share_price_result, container, false);
        return view;
    }

    public void displayResult(Stock stockData, StockPreferences stockPreferences){
        String turnover = MainPrefs.getFormattedNumber((stockData.getBuyPrice()+stockData.getSellPrice()) * stockData.getQuantity());
        String brokerage = "0";
        String exchgTxPlusTaxes;
        String breakEven;
        String profitLoss;
        double totalCharges = stockData.getBrokerage() + stockData.getSttCharges() +
                stockData.getExchangeTxCharges() + stockData.getSebiCharges() +
                stockData.getServiceCharges() + stockData.getStampDutyCharges();
        ((TextView)getView().findViewById(R.id.totalTurnOver)).setText(turnover);
        if(stockData.getBrokerage() > 0){
            brokerage = MainPrefs.getFormattedNumber(stockData.getBrokerage());
        }
        ((TextView)getView().findViewById(R.id.brokerage)).setText(brokerage);
        exchgTxPlusTaxes = MainPrefs.getFormattedNumber(
                (stockData.getSttCharges()+stockData.getExchangeTxCharges()+stockData.getServiceCharges()+stockData.getSebiCharges()+stockData.getStampDutyCharges()));
        ((TextView)getView().findViewById(R.id.otherCharges)).setText(exchgTxPlusTaxes);
        TextView breakEvenLabel = (TextView) getView().findViewById(R.id.break_even_label);
        TextView profitLossLabel = (TextView) getView().findViewById(R.id.prfit_loss_label);
        TableRow profitLossRow = (TableRow) getView().findViewById(R.id.net_profit_loss);
        double profitLossVal = stockData.getProfitOrLoss();
        if(stockData.getBuyPrice()>0 && stockData.getSellPrice()>0){
            profitLossLabel.setText("Net Profit/Loss");
            profitLossVal = stockData.getProfitOrLoss();
        }else if(stockData.getBuyPrice()>0){
            profitLossLabel.setText("Net Worth Bought");
            profitLossVal = (stockData.getBuyPrice() * stockData.getQuantity()) - totalCharges;
        }else if(stockData.getSellPrice()>0){
            profitLossLabel.setText("Net Worth Sold");
            profitLossVal = (stockData.getSellPrice() * stockData.getQuantity()) - totalCharges;
        }
        breakEven = MainPrefs.getFormattedNumber(stockData.getBreakEven());
        ((TextView)getView().findViewById(R.id.breakEvenPrice)).setText(breakEven);
        profitLoss = MainPrefs.getFormattedNumber(profitLossVal);
        ((TextView)getView().findViewById(R.id.profitOrLoss)).setText(profitLoss);
    }

}
