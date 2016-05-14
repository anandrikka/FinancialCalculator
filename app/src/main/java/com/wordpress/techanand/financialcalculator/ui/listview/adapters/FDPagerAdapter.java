package com.wordpress.techanand.financialcalculator.ui.listview.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wordpress.techanand.financialcalculator.activities.fixeddeposits.FixedDepositCalcTab;
import com.wordpress.techanand.financialcalculator.activities.fixeddeposits.FixedDepositDetailsTab;

/**
 * Created by Anand Rikka on 5/13/2016.
 */

public class FDPagerAdapter extends FragmentStatePagerAdapter{

    int numTabs;

    public FDPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FixedDepositCalcTab calcTab = new FixedDepositCalcTab();
                return calcTab;
            case 1:
                FixedDepositDetailsTab detailsTab = new FixedDepositDetailsTab();
                return detailsTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
