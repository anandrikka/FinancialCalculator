package com.wordpress.techanand.financialcalculator.app.fragments;

/**
 * Created by Anand Rikka on 6/6/2016.
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;
import com.wordpress.techanand.financialcalculator.app.models.FixedDepositObject;

public class FixedDepositFragment extends Fragment {

    public interface FixedDepositListener {
        public void calculate(FixedDepositObject fixedDepositObject);
        public void reset();
    }

    private EditText fdAmount, period, roi;
    private Spinner periodUnitSpinner, compoundingFreq, payoutSpinner;
    private AlertDialog.Builder builder;
    private Button calculate, reset;
    CheckBox standard, interestPayout;

    private FixedDepositObject fixedDepositObject;
    private FixedDepositListener fixedDepositListener;

    private boolean isCalcClicked;

    public FixedDepositFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixedDepositObject = new FixedDepositObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fixed_deposit_fragment, container, false);
        setRetainInstance(true);
        initializeData(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            fixedDepositListener = (FixedDepositListener)context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString() + "must implement FixedDepositListener");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        standard.setChecked(standard.isChecked());
        interestPayout.setChecked(interestPayout.isChecked());
        fdAmount.setText(fdAmount.getText().toString());
        period.setText(period.getText().toString());
        roi.setText(roi.getText().toString());
    }

    public void initializeData(View view){
        fdAmount = (EditText) view.findViewById(R.id.fd_amount);
        period = (EditText) view.findViewById(R.id.period_value);
        roi = (EditText) view.findViewById(R.id.roi);

        standard = (CheckBox) view.findViewById(R.id.by_standard);
        interestPayout = (CheckBox) view.findViewById(R.id.by_interest_payout);

        periodUnitSpinner = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSpinner.setAdapter(periodUnitAdapter);

        compoundingFreq = (Spinner) view.findViewById(R.id.compounding_interest_input);
        ArrayAdapter<String> compoundingFreqAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.COMPOUNDING_FREQ);
        compoundingFreqAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        compoundingFreq.setAdapter(compoundingFreqAdapter);
        compoundingFreq.setSelection(1);

        payoutSpinner = (Spinner) view.findViewById(R.id.payout_input);
        ArrayAdapter<String> payoutAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, FixedDepositActivity.PAYOUT_FREQ);
        payoutAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        payoutSpinner.setAdapter(payoutAdapter);

        reset = (Button) view.findViewById(R.id.reset);
        calculate = (Button) view.findViewById(R.id.calculate);

        if(standard.isChecked()){
            ((TableRow)view.findViewById(R.id.interest_payout_row)).setVisibility(View.GONE);
        }

        periodUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixedDepositObject.setTimeUnit(FixedDepositActivity.PERIOD[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        compoundingFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixedDepositObject.setCompoundingUnit(FixedDepositActivity.COMPOUNDING_FREQ[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        payoutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fixedDepositObject.setPayoutUnit(FixedDepositActivity.PAYOUT_FREQ[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fdAmount.setText(null);
                period.setText(null);
                roi.setText(null);
                periodUnitSpinner.setSelection(0);
                compoundingFreq.setSelection(1);
                isCalcClicked = false;
                fixedDepositListener.reset();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFD(false, true);
            }
        });

        calculateFD(true, false);

    }

    public void calculateFD(boolean isFromInitialLoad, boolean isCalcClicked){
        this.isCalcClicked = isCalcClicked;
        if(!this.isCalcClicked){
            return;
        }
        AppMain.hideKeyboard(getActivity(), calculate);
        String fdText = fdAmount.getText().toString();
        String roiText = roi.getText().toString();
        String periodText = period.getText().toString();
        if((fdText != null && fdText.length() > 0) &&
                (roiText != null && roiText.length() > 0) &&
                (periodText !=null && periodText.length() > 0)){

            fixedDepositObject.setFdAmount(Double.parseDouble(fdText));
            fixedDepositObject.setTime(Double.parseDouble(periodText));
            fixedDepositObject.setRoi(Double.parseDouble(roiText));
            fixedDepositObject.setTimeUnit((String)periodUnitSpinner.getSelectedItem());
            fixedDepositObject.setCompoundingUnit((String)compoundingFreq.getSelectedItem());
            fixedDepositObject.setIsStandardFD(((CheckBox)getView().findViewById(R.id.by_standard)).isChecked());
            fixedDepositObject.setIsInterestPayoutFD(((CheckBox)getView().findViewById(R.id.by_interest_payout)).isChecked());

            fixedDepositListener.calculate(fixedDepositObject);

        } else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

}
