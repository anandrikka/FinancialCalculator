package com.wordpress.techanand.financialcalculator.activities.stock;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.models.StockCategory;
import com.wordpress.techanand.financialcalculator.ui.listview.adapters.StockListAdapter;

import java.util.ArrayList;
import java.util.List;

public class StockPriceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_price);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        final Spinner dropdownList = (Spinner) findViewById(R.id.content_stock_price_dropdownList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        StockListAdapter adapter = new StockListAdapter(this, R.layout.content_stock_price_dropdown, getStockCategories());
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownList.setAdapter(adapter);
        dropdownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StockCategory item = (StockCategory) parent.getItemAtPosition(position);
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item.getType()+ " "+item.getName(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dropdownList.setSelection(0);
            }
        });

    }

    private List<StockCategory> getStockCategories(){
        List<StockCategory> categories = new ArrayList<>();

        categories.add(new StockCategory(StockCategory.EQUITY_DELIVERY, "Equity - Delivery"));
        categories.add(new StockCategory(StockCategory.EQUITY_INTRADAY, "Equity - Intraday"));
        categories.add(new StockCategory(StockCategory.EQUITY_FUTURES, "Equity - Futures"));
        categories.add(new StockCategory(StockCategory.EQUITY_OPTIONS, "Equity - Options"));
        categories.add(new StockCategory(StockCategory.CURRENCY, "Currency"));
        categories.add(new StockCategory(StockCategory.COMMODITIES, "Commodities"));

        return categories;
    }

}
