package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.SIPCalculateTab;
import com.wordpress.techanand.financialcalculator.app.fragments.SIPForGoal;
import com.wordpress.techanand.financialcalculator.app.fragments.SIPReturnsTab;

public class MutualFundActivity extends AppCompatActivity {

    public static final String[] PERIOD = {"Months", "Years"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_fund);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsLayout);
        tabLayout.addTab(tabLayout.newTab().setText("SIP Returns"));
        tabLayout.addTab(tabLayout.newTab().setText("Calculate SIP"));
        tabLayout.addTab(tabLayout.newTab().setText("Goal Calculator"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewTabs);
        MutualFundViewAdapter adapter = new MutualFundViewAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

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


    class MutualFundViewAdapter extends FragmentStatePagerAdapter {

        int numTabs;

        public MutualFundViewAdapter(FragmentManager fm, int numTabs) {
            super(fm);
            this.numTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new SIPReturnsTab();
                case 1:
                    return new SIPCalculateTab();
                case 2:
                    return new SIPForGoal();
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
