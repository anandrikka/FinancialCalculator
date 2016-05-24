package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FDInterestPayoutTab extends Fragment {


    public FDInterestPayoutTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fd_interest_payout, container, false);

        final Spinner periodUnitSpinner = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSpinner.setAdapter(periodUnitAdapter);

        final Spinner compoundingFreq = (Spinner) view.findViewById(R.id.compounding_interest_input);
        ArrayAdapter<String> compoundingFreqAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.COMPOUNDING_FREQ);
        compoundingFreqAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        compoundingFreq.setAdapter(compoundingFreqAdapter);
        compoundingFreq.setSelection(1);

        final Spinner interestPayout = (Spinner) view.findViewById(R.id.payout_input);
        ArrayAdapter<String> interestPayoutAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PAYOUT_FREQ);
        interestPayoutAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        interestPayout.setAdapter(interestPayoutAdapter);

        periodUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), FixedDepositActivity.PERIOD[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        compoundingFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), FixedDepositActivity.COMPOUNDING_FREQ[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        interestPayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getContext(), FixedDepositActivity.PAYOUT_FREQ[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

}
