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

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.activities.RecurringDepositActivity;
import com.wordpress.techanand.financialcalculator.app.models.RecurringDepositObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecurringDepositFragment extends Fragment {


    public interface RecurringDepositListener {
        public void calculate(RecurringDepositObject recurringDepositObject);
        public void reset();
    }


    private EditText installmentOrMaturityText, periodText, roiText;
    private Spinner periodUnitSelect;
    private Button resetButton, calculateButton;

    private RecurringDepositObject recurringDepositObject;
    private RecurringDepositListener recurringDepositListener;

    public RecurringDepositFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recurringDepositObject = new RecurringDepositObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recurring_deposit_fragment, container, false);
        setRetainInstance(true);
        initializeData(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            recurringDepositListener = (RecurringDepositActivity)getContext();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+" must implement RecurringDepositListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        periodUnitSelect.setSelection(1);
    }

    public void initializeData(View view){
        installmentOrMaturityText = (EditText) view.findViewById(R.id.installment_or_maturity);
        periodText = (EditText) view.findViewById(R.id.period);
        periodUnitSelect = (Spinner) view.findViewById(R.id.period_unit);
        ArrayAdapter<String> periodUnitAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, RecurringDepositActivity.PERIOD);
        periodUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        periodUnitSelect.setAdapter(periodUnitAdapter);
        roiText = (EditText) view.findViewById(R.id.roi);
        resetButton = (Button) view.findViewById(R.id.reset);
        calculateButton = (Button) view.findViewById(R.id.calculate);

        periodUnitSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periodUnitSelect.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installmentOrMaturityText.setText("");
                periodText.setText("");
                periodUnitSelect.setSelection(1);
                roiText.setText("");
                recurringDepositListener.reset();
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

    private void calculate(boolean isFromInitialLoad){
        String installmentOrMaturity, maturityAmount, roi, duration;
        installmentOrMaturity = installmentOrMaturityText.getText().toString();
        roi = roiText.getText().toString();
        duration = periodText.getText().toString();
        if(!installmentOrMaturity.equals("") && !roi.equals("") && !duration.equals("")){
            String selectedPeriod = (String)periodUnitSelect.getSelectedItem();
            if(selectedPeriod.equals(RecurringDepositActivity.PERIOD[0])){
                if((Double.parseDouble(duration))%3 == 0){
                }else{
                    AppMain.dialogBuilder(getContext(),
                            "Error",
                            "Term should be multiples of 3 !",
                            "OK").create().show();
                    return;
                }
            }
            boolean isByMonthly = ((CheckBox)getView().findViewById(R.id.by_monthly)).isChecked();
            boolean isByTarget = ((CheckBox)getView().findViewById(R.id.by_target)).isChecked();
            recurringDepositObject.setIsMonthly(isByMonthly);
            recurringDepositObject.setIsByTargetAmount(isByTarget);
            if(isByMonthly){
                recurringDepositObject.setMonthlyInvestment(Double.parseDouble(installmentOrMaturity));
            }
            if(isByTarget){
                recurringDepositObject.setMaturityAmount(Double.parseDouble(installmentOrMaturity));
            }
            recurringDepositObject.setDuration(Double.parseDouble(duration));
            recurringDepositObject.setRoi(Double.parseDouble(roi));
            recurringDepositObject.setDurationUnit(selectedPeriod);
            recurringDepositListener.calculate(recurringDepositObject);
            /*R = Double.parseDouble(installmentText.getText().toString());
            r = Double.parseDouble(roiText.getText().toString());
            period = Double.parseDouble(periodText.getText().toString());


            r = r/400;

            //M = ( R * [(1+r)^n - 1 ] ) / (1-(1+r)^-1/3)
            double x = 1+r;
            x = Math.pow(x, n);
            x = x-1;
            x = R*x;
            double y = 1+r;
            double y1 = 1.0/3.0;
            y = Math.pow(y,y1);
            y = 1.0/y;
            y = 1-y;
            M  = x/y;
            P = R*n*3;
            interestEarned = M-P;*/
            /*investmentAmountText.setText(Math.round(P)+"");
            maturityAmountText.setText(Math.round(M)+"");
            interestEarnedText.setText(Math.round(interestEarned)+"");
            results.setVisibility(View.VISIBLE);
            createPieChart((float)P, (float)interestEarned);
            pieChart.setVisibility(View.VISIBLE);*/
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(getContext(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

    /*private void createPieChart(float P, float I){
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(P, 0));
        entries.add(new Entry(I, 1));

        PieDataSet dataset = new PieDataSet(entries, "");
        ArrayList<String> labels = new ArrayList<String>();

        labels.add("Investment");
        labels.add("Interest");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(data);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setDescription("");
    }*/

}
