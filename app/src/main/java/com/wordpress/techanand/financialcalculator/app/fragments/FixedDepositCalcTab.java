package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordpress.techanand.financialcalculator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FixedDepositCalcTab extends Fragment {


    public FixedDepositCalcTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fixed_deposit_calc_tab, container, false);
    }

}
