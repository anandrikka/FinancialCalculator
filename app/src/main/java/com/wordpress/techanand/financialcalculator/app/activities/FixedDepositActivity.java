package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppConstants;
import com.wordpress.techanand.financialcalculator.app.fragments.FDStandardTab;
import com.wordpress.techanand.financialcalculator.app.fragments.FDInterestPayoutTab;

public class FixedDepositActivity extends AppCompatActivity {

    public static final String[] PERIOD = {"Years", "Months", "Days"};
    public static final String[] COMPOUNDING_FREQ = {"Monthly", "Quarterly", "Half Yearly", "Yearly", "Simple Interest"};
    public static final String[] PAYOUT_FREQ = {"Monthly", "Yearly"};

    /*Formula for fixed deposit:
    *
    * A = P * (1+r/n)^nt
    *
    * P - Principle Amount
    * r - Rate of Interest
    * t - Number of Years
    * n - No' of compounding periods, example: for quarterly (4), for half yearly (2) etc..
    *
    * */

    private double P;
    private int t;
    private double r;
    private int n;
    private String tUnit;
    private String nUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Standard"));
        tabLayout.addTab(tabLayout.newTab().setText("Interest Payout"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewTabs);

        FDPagerAdapter pagerAdapter = new FDPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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
    }

    public class FDPagerAdapter extends FragmentStatePagerAdapter {
        int numTabs;

        public FDPagerAdapter(FragmentManager fm, int numTabs) {
            super(fm);
            this.numTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FDStandardTab();
                case 1:
                    return new FDInterestPayoutTab();
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
