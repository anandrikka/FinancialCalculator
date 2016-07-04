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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.models.GoalObject;
import com.wordpress.techanand.financialcalculator.app.models.InflationObject;

/**
 * Created by Anand Rikka on 7/4/2016.
 */
public class InflationFragment extends Fragment {

    public static final String ID = InflationFragment.class.getName();

    private EditText presentValue, inflationRate, numberOfYears;
    private Button reset, calculate;
    private CardView resultCard;
    private TextView resultText;

    private InflationObject inflationObject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inflation_fragment, container, false);
        Toolbar appBarLayout = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Inflation Calculator");
        }
        initializeLoad(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflationObject = new InflationObject();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        presentValue.setText(presentValue.getText().toString());
        inflationRate.setText(inflationRate.getText().toString());
        numberOfYears.setText(numberOfYears.getText().toString());
    }

    private void initializeLoad(View view){
        presentValue = (EditText) view.findViewById(R.id.present_value);
        inflationRate = (EditText) view.findViewById(R.id.inflation_rate);
        numberOfYears = (EditText) view.findViewById(R.id.number_of_years);
        reset = (Button) view.findViewById(R.id.reset);
        calculate = (Button) view.findViewById(R.id.calculate);
        resultCard = (CardView) view.findViewById(R.id.result_card);
        resultText = (TextView)view.findViewById(R.id.result_text);
        resultCard.setVisibility(View.GONE);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentValue.setText("");
                inflationRate.setText("");
                numberOfYears.setText("");
                resultCard.setVisibility(View.GONE);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult(false);
            }
        });

        calculateResult(true);
    }


    private void calculateResult(boolean isFromInitialLoad){
        String presentValueText = presentValue.getText().toString();
        String inflationText = inflationRate.getText().toString();
        String numberOfYearsText = numberOfYears.getText().toString();
        if((presentValueText != null && presentValueText.length() > 0) &&
                (inflationText != null && inflationText.length() > 0) &&
                (numberOfYearsText !=null && numberOfYearsText.length() > 0)){

            inflationObject.setPresentValue(Double.parseDouble(presentValueText));
            inflationObject.setInflationRate(Double.parseDouble(inflationText));
            inflationObject.setNumberOfYears(Double.parseDouble(numberOfYearsText));

            double P, r, t, n, A;
            P = inflationObject.getPresentValue();
            r = inflationObject.getInflationRate() * 0.01;
            t = inflationObject.getNumberOfYears();
            n = 1;
            A = P * (Math.pow((1+r/n), (n*t)));
            inflationObject.setFutureValue(A);
            String resultString = String.format(getResources().getString(R.string.app_inflation_result_text),
                    MainPrefs.getFormattedNumber(Math.round(A)), Math.round(t));
            CharSequence resultSq = Html.fromHtml(resultString);
            resultText.setText(resultSq);
            resultCard.setVisibility(View.VISIBLE);
        } else if(!isFromInitialLoad){
            AppMain.dialogBuilder(this.getActivity(),
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }

}
