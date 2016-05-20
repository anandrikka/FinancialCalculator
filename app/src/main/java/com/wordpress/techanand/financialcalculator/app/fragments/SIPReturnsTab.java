package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.activities.MutualFundActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SIPReturnsTab extends Fragment {


    public SIPReturnsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sip_returns_tab, container, false);

        Spinner periodUnitSpinner = (Spinner) view.findViewById(R.id.time_period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_dropdown, MutualFundActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSpinner.setAdapter(periodUnitAdapter);


        return view;
    }

}
