package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.models.StockCategory;

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

        final Spinner dropdownList = (Spinner) findViewById(R.id.stock_category_list);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        StockListAdapter adapter = new StockListAdapter(this, R.layout.stock_price_dropdown, getStockCategories());
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


    class StockListAdapter extends ArrayAdapter {

        List<StockCategory> list;

        public StockListAdapter(Context context, int resource, List<StockCategory> objects) {
            super(context, resource, objects);
            this.list = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stock_price_dropdown, null);
            StockCategory stockCategory = list.get(position);
            TextView label = (TextView) convertView.findViewById(R.id.displayText);
            label.setText(stockCategory.getName());
            return convertView;
        }
    }
}
