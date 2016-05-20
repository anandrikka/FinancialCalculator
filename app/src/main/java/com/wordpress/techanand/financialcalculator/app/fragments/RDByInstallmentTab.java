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
import com.wordpress.techanand.financialcalculator.app.activities.RecurringDepositActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RDByInstallmentTab extends Fragment {


    public RDByInstallmentTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rd_installment_tab, container, false);

        final Spinner compoundingFreq = (Spinner) view.findViewById(R.id.compounding_interest_input);
        ArrayAdapter<String> compoundingFreqAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown,RecurringDepositActivity.COMPOUNDING_FREQ);
        compoundingFreqAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        compoundingFreq.setAdapter(compoundingFreqAdapter);
        compoundingFreq.setSelection(1);

        compoundingFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), RecurringDepositActivity.COMPOUNDING_FREQ[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner periodUnitSpinner = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, RecurringDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSpinner.setAdapter(compoundingFreqAdapter);
        periodUnitSpinner.setSelection(0);

        periodUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), RecurringDepositActivity.PERIOD[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

}
