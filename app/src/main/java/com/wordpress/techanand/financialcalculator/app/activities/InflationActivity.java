package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.models.InflationObject;

public class InflationActivity extends AppCompatActivity {

    private EditText presentValue, inflationRate, numberOfYears;
    private Button reset, calculate;
    private LinearLayout resultLayout;

    private InflationObject inflationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presentValue = (EditText) findViewById(R.id.present_value);
        inflationRate = (EditText) findViewById(R.id.inflation_rate);
        numberOfYears = (EditText) findViewById(R.id.number_of_years);
        reset = (Button) findViewById(R.id.reset);
        calculate = (Button) findViewById(R.id.calculate);
        resultLayout = (LinearLayout) findViewById(R.id.result);
        resultLayout.setVisibility(View.GONE);

        inflationData = new InflationObject();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentValue.setText("");
                inflationRate.setText("");
                numberOfYears.setText("");
                resultLayout.setVisibility(View.GONE);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });

        calculate(true);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        presentValue.setText(presentValue.getText().toString());
        inflationRate.setText(inflationRate.getText().toString());
        numberOfYears.setText(numberOfYears.getText().toString());
    }

    private void calculate(boolean isFromInitialLoad){
        String presentValueText = presentValue.getText().toString();
        String inflationText = inflationRate.getText().toString();
        String numberOfYearsText = numberOfYears.getText().toString();
        if((presentValueText != null && presentValueText.length() > 0) &&
                (inflationText != null && inflationText.length() > 0) &&
                (numberOfYearsText !=null && numberOfYearsText.length() > 0)){

            inflationData.setPresentValue(Double.parseDouble(presentValueText));
            inflationData.setInflationRate(Double.parseDouble(inflationText));
            inflationData.setNumberOfYears(Double.parseDouble(numberOfYearsText));

            double P, r, t, n, A;
            P = inflationData.getPresentValue();
            r = inflationData.getInflationRate() * 0.01;
            t = inflationData.getNumberOfYears();
            n = 1;
            A = P * (Math.pow((1+r/n), (n*t)));
            inflationData.setFutureValue(A);
            String resultString = String.format(getResources().getString(R.string.app_inflation_result_text),
                    MainPrefs.getFormattedNumber(Math.round(A)), Math.round(t));
            CharSequence resultSq = Html.fromHtml(resultString);
            ((TextView)findViewById(R.id.result_text)).setText(resultSq);
            resultLayout.setVisibility(View.VISIBLE);
        } else if(!isFromInitialLoad){
            AppMain.dialogBuilder(this,
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }
}
