package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.pm.PackageInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.RDByInstallmentTab;
import com.wordpress.techanand.financialcalculator.app.fragments.RDByMaturityTab;

public class RecurringDepositActivity extends AppCompatActivity {

    public static final String[] COMPOUNDING_FREQ = {"Monthly", "Quarterly", "Yearly"};
    public static final String[] PERIOD = {"Months", "Years"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsLayout);
        tabLayout.addTab(tabLayout.newTab().setText("By Installment"));
        tabLayout.addTab(tabLayout.newTab().setText("By Maturity"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewTabs);

        RDPagerAdapter pagerAdapter = new RDPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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


    class RDPagerAdapter extends FragmentStatePagerAdapter {

        int numTabs;

        public RDPagerAdapter(FragmentManager fm, int numTabs) {
            super(fm);
            this.numTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new RDByInstallmentTab();
                case 1:
                    return new RDByMaturityTab();
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
