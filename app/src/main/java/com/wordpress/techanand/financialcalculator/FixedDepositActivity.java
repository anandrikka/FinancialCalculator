package com.wordpress.techanand.financialcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wordpress.techanand.financialcalculator.ui.listview.adapters.FDPagerAdapter;

public class FixedDepositActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.content_fixed_deposit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setVisibility(FloatingActionButton.INVISIBLE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.content_fixed_deposit_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fixed_deposit_calculate));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fixed_deposit_details));
        /*tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));*/
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager pager = (ViewPager) findViewById(R.id.content_fixed_deposit_pager);
        final PagerAdapter pagerAdapter = new FDPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fab.setVisibility(FloatingActionButton.INVISIBLE);
                pager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 1){
                    fab.setVisibility(FloatingActionButton.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
