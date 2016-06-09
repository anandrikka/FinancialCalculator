package com.wordpress.techanand.financialcalculator.app.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.MutualFundActivity;
import com.wordpress.techanand.financialcalculator.app.models.MutualFund;

/**
 * A simple {@link Fragment} subclass.
 */
public class MutualFundSIP extends Fragment {

    public interface MutualFundSIPListener {
        public void resetListener();
        public void calculateListener(MutualFund mutualFundData, boolean isFromInitialLoad);
    }

    private MutualFundSIPListener mutualFundSIPListener;
    private EditText monthlyInvestmentText, timePeriodText, annualReturnText;
    private Spinner timePeriodSelect;
    private Button resetButton, calculateButton;

    private MutualFund mutualFundData;

    public MutualFundSIP() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mutualFundData = new MutualFund();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mutual_funds_sip, container, false);
        setRetainInstance(true);
        initialLoad(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mutualFundSIPListener = (MutualFundSIPListener) context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString() + " must implement MutualFundSIPListener");
        }
    }

    private void initialLoad(View view){
        monthlyInvestmentText = (EditText) view.findViewById(R.id.monthly_installment);
        timePeriodText = (EditText) view.findViewById(R.id.investment_time);
        annualReturnText = (EditText) view.findViewById(R.id.expected_return);
        timePeriodSelect = (Spinner) view.findViewById(R.id.time_period_unit);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_dropdown, MutualFundActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        timePeriodSelect.setAdapter(periodUnitAdapter);
        timePeriodSelect.setSelection(1);
        timePeriodSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timePeriodSelect.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthlyInvestmentText.setText("");
                timePeriodText.setText("");
                annualReturnText.setText("");
                timePeriodSelect.setSelection(1);
                mutualFundSIPListener.resetListener();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });
        calculate(true);
    }

    public void calculate(boolean isFromInitialLoad){
        if(!monthlyInvestmentText.getText().toString().equals("") &&
                !timePeriodText.getText().toString().equals("") &&
                !annualReturnText.getText().toString().equals("")){
            mutualFundData.setAmount(Double.parseDouble(monthlyInvestmentText.getText().toString()));
            mutualFundData.setTotalPeriod(Double.parseDouble(timePeriodText.getText().toString()));
            mutualFundData.setAnnualReturns(Double.parseDouble(annualReturnText.getText().toString()));
            mutualFundData.setIsMonthlySIP(((CheckBox) getView().findViewById(R.id.by_sip)).isChecked());
            mutualFundData.setIsTargetAmount(((CheckBox) getView().findViewById(R.id.by_target)).isChecked());
            mutualFundData.setPeriodUnit((String)timePeriodSelect.getSelectedItem());
            mutualFundSIPListener.calculateListener(mutualFundData, isFromInitialLoad);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

}
