package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.fragments.FixedDepositCalcTab;
import com.wordpress.techanand.financialcalculator.app.fragments.FixedDepositDetailsTab;

public class FixedDepositActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Calculate"));
        tabLayout.addTab(tabLayout.newTab().setText("Details"));

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
                    return new FixedDepositCalcTab();
                case 1:
                    return new FixedDepositDetailsTab();
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
