package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.wordpress.techanand.financialcalculator.app.fragments.FDInterestPayoutTab;
import com.wordpress.techanand.financialcalculator.app.fragments.FDStandardTab;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksByAmount;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksBySharePrice;
import com.wordpress.techanand.financialcalculator.app.models.StockCategory;

import java.util.ArrayList;
import java.util.List;

public class StockPriceActivity extends AppCompatActivity {

    public static final String[] CATEGORIES = {
            "Equity - Delivery",
            "Equity - Intraday",
            "Equity - Equity Futures",
            "Equity - Options",
            "Currency",
            "Commodities"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_price);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsLayout);
        tabLayout.addTab(tabLayout.newTab().setText("By Share Price"));
        tabLayout.addTab(tabLayout.newTab().setText("By Amount"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewTabs);

        StockPricePagerAdapter pagerAdapter = new StockPricePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

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


    public static class StockListAdapter extends ArrayAdapter {

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

    public class StockPricePagerAdapter extends FragmentStatePagerAdapter {
        int numTabs;

        public StockPricePagerAdapter(FragmentManager fm, int numTabs) {
            super(fm);
            this.numTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new StocksBySharePrice();
                case 1:
                    return new StocksByAmount();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numTabs;
        }
    }
}
