package com.wordpress.techanand.financialcalculator.app.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.PieChartConfig;
import com.wordpress.techanand.financialcalculator.app.activities.LoanActivity;
import com.wordpress.techanand.financialcalculator.app.models.LoanObject;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Anand Rikka on 6/12/2016.
 */

public class LoanFragment extends Fragment{

    public static final String ID = LoanFragment.class.getName();

    private LoanObject loanObject;

    private EditText loanAmountText, tenureText, interestText;
    private Spinner tenureUnitSelection;
    private Button resetButton, calculateButton;
    private CardView results;
    private PieChart pieChart;
    private TextView resultText;

    private boolean isCalcClicked;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanObject = new LoanObject();
        Toolbar appBarLayout = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Loan Calculator");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        loanAmountText.setText(loanAmountText.getText().toString());
        tenureText.setText(tenureText.getText().toString());
        interestText.setText(tenureText.getText().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loan_fragment, container, false);
        loanAmountText = (EditText) view.findViewById(R.id.loan_amount);
        tenureText = (EditText) view.findViewById(R.id.tenure);
        interestText = (EditText) view.findViewById(R.id.interest_rate);
        tenureUnitSelection = (Spinner) view.findViewById(R.id.tenure_unit);
        ArrayAdapter<String> tenureUnitAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_dropdown, LoanActivity.TENURE_UNITS);
        tenureUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        tenureUnitSelection.setAdapter(tenureUnitAdapter);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);
        results = (CardView) view.findViewById(R.id.result_card);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        results.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
        resultText = (TextView)view.findViewById(R.id.result_text_view);
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
                isCalcClicked = false;
                results.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false, true);
            }
        });

        calculate(true, false);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void calculate(boolean isFromInitialLoad, boolean isCalcClicked){
        this.isCalcClicked = isCalcClicked;
        if(!this.isCalcClicked){
            return;
        }
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

            calculateResult(loanObject);

        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    public void calculateResult(LoanObject loanData) {
        /*
        * [P * r * (1+r)^n)]/[((1+r)^n)-1]
        *
        * P = Loan amount;
        * r = rate of interest;
        * n = total months
        *
        * */
        double tenure = loanData.getTenure();
        double interest = loanData.getInterestRate();
        double loanAmount = loanData.getLoanAmount();

        if(loanData.getPeriodUnit().equals(LoanActivity.TENURE_UNITS[0])){
            tenure = tenure * 12;
        }
        //[P * r * (1+r)^n)]/[((1+r)^n)-1]
        double r = interest/12.0;
        r = r/100.0;
        double n = tenure;
        double P = loanAmount;
        double x = 1+r;
        x = Math.pow(x, n);
        x = P * r * x;
        double y = 1+r;
        y = Math.pow(y, n);
        y = y - 1;
        double emi = x/y;
        double totalPaid = emi * n;
        double interestPaid = totalPaid - P;
        loanData.setTotalInterestPaid(interestPaid);

        String resultString = String.format(getResources().getString(R.string.app_loan_result_text),
                Math.round(emi), Math.round(P), Double.toString(interest), Math.round(tenure), Math.round(totalPaid));
        CharSequence resultSq = Html.fromHtml(resultString);
        resultText.setText(resultSq);
        createPieChart(loanData);
        results.setVisibility(View.VISIBLE);
    }

    private void createPieChart(LoanObject loanData){
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float)loanData.getLoanAmount(), 0));
        entries.add(new Entry((float)(loanData.getTotalInterestPaid()), 1));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Principle");
        labels.add("Interest");

        pieChart = PieChartConfig.createPieChart(getActivity(), pieChart, entries, labels, "");
        pieChart.setVisibility(View.VISIBLE);

    }
}
