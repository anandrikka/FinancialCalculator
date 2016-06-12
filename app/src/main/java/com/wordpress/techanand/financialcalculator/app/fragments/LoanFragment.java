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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.LoanActivity;
import com.wordpress.techanand.financialcalculator.app.models.LoanObject;

/**
 * Created by Anand Rikka on 6/12/2016.
 */

public class LoanFragment extends Fragment{

    public interface LoanFragmentListener {
        public void calculate(LoanObject loanData);
        public void reset();
    }

    private LoanFragmentListener loanFragmentListener;
    private LoanObject loanObject;

    private EditText loanAmountText, tenureText, interestText;
    private Spinner tenureUnitSelection;
    private Button resetButton, calculateButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanObject = new LoanObject();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loan_fragment, container, false);
        setRetainInstance(true);
        loanAmountText = (EditText) view.findViewById(R.id.loan_amount);
        tenureText = (EditText) view.findViewById(R.id.tenure);
        interestText = (EditText) view.findViewById(R.id.interest_rate);
        tenureUnitSelection = (Spinner) view.findViewById(R.id.tenure_unit);
        ArrayAdapter<String> tenureUnitAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_dropdown, LoanActivity.TENURE_UNITS);
        tenureUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        tenureUnitSelection.setAdapter(tenureUnitAdapter);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);

        tenureUnitSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenureUnitSelection.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanAmountText.setText("");
                interestText.setText("");
                tenureText.setText("");
                loanFragmentListener.reset();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });

        calculate(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loanFragmentListener = (LoanFragmentListener)getContext();
    }

    private void calculate(boolean isFromInitialLoad){
        String loanAmountString, tenureString, interestString;
        loanAmountString = loanAmountText.getText().toString();
        tenureString = tenureText.getText().toString();
        interestString = interestText.getText().toString();
        if(!loanAmountString.equals("") && !tenureString.equals("") && !interestString.equals("")){
            double loanAmount = Double.parseDouble(loanAmountString);
            double tenure = Double.parseDouble(tenureString);
            double interest = Double.parseDouble(interestString);

            loanObject.setLoanAmount(loanAmount);
            loanObject.setTenure(tenure);
            loanObject.setInterestRate(interest);
            loanObject.setPeriodUnit((String)tenureUnitSelection.getSelectedItem());

            loanFragmentListener.calculate(loanObject);

        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }
}
