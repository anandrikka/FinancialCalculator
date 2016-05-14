package com.wordpress.techanand.financialcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wordpress.techanand.financialcalculator.ui.listview.adapters.RDPagerAdapter;

public class RecurringDeposityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_deposity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setVisibility(FloatingActionButton.INVISIBLE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.content_recurring_deposit_tabsLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Calculate"));
        tabLayout.addTab(tabLayout.newTab().setText("Details"));

        final ViewPager pager = (ViewPager) findViewById(R.id.content_recurring_deposit_pager);
        RDPagerAdapter pagerAdapter = new RDPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
