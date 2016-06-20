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
import com.wordpress.techanand.financialcalculator.app.models.StockObject;
import com.wordpress.techanand.financialcalculator.app.models.StockPreferencesObject;

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

}
