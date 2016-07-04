package com.wordpress.techanand.financialcalculator.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wordpress.techanand.financialcalculator.R;

public class StockPriceActivity extends AppCompatActivity {

    public static final String[] CATEGORIES = {
            "Equity - Delivery",
            "Equity - Intraday",
            "Equity - Futures",
            "Equity - Options",
            "Currency - Futures",
            "Currency - Options",
            "Commodities"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_price);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
