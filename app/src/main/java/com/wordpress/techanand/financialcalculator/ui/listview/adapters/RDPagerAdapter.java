package com.wordpress.techanand.financialcalculator.ui.listview.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wordpress.techanand.financialcalculator.RecurringDepositCalcTab;
import com.wordpress.techanand.financialcalculator.RecurringDepositDetailsTab;

/**
 * Created by Anand Rikka on 5/13/2016.
 */
public class RDPagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public RDPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecurringDepositCalcTab();
            case 1:
                return new RecurringDepositDetailsTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
