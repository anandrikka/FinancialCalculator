package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksByAmount;
import com.wordpress.techanand.financialcalculator.app.fragments.StocksBySharePrice;
import com.wordpress.techanand.financialcalculator.app.models.StockCategory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

    public final static DecimalFormat DIGIT_FORMAT = new DecimalFormat("#.###");

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
