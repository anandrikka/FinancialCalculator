package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LoanActivity extends AppCompatActivity {

    /*
    * [P * r * (1+r)^n)]/[((1+r)^n)-1]
    *
    * P = Loan amount;
    * r = rate of interest;
    * n = total months
    *
    * */

    public static final String[] TENURE_UNITS = {"Years", "Months"};


    private EditText loanAmountText, tenureText, interestText;
    private Spinner tenureUnitSelection;
    private Button resetButton, calculateButton;
    private PieChart pieChart;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loanAmountText = (EditText) findViewById(R.id.loan_amount);
        tenureText = (EditText) findViewById(R.id.tenure);
        interestText = (EditText) findViewById(R.id.interest_rate);
        tenureUnitSelection = (Spinner) findViewById(R.id.tenure_unit);
        ArrayAdapter<String> tenureUnitAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_dropdown, TENURE_UNITS);
        tenureUnitAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        tenureUnitSelection.setAdapter(tenureUnitAdapter);
        resetButton = (Button) findViewById(R.id.reset);
        calculateButton = (Button) findViewById(R.id.calculate);
        resultText = (TextView) findViewById(R.id.result_text);
        resultText.setVisibility(View.GONE);
        tenureSelectionListener();
        resetListener();
        calculateListener();

        createPieChart();

        calculate(true);
    }

    private void tenureSelectionListener(){
        tenureUnitSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenureUnitSelection.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void resetListener(){
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanAmountText.setText("");
                interestText.setText("");
                tenureText.setText("");
                pieChart.setVisibility(View.GONE);
                resultText.setVisibility(View.GONE);
            }
        });
    }

    private void calculateListener(){
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });
    }

    private void createPieChart(){
        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart.setVisibility(View.GONE);
        /*RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = 300;
        pieChart.setLayoutParams(layoutParams);
        mainLayout.addView(pieChart);*/

        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    public void setDataIntoChart(float P, float I){
        pieChart.clear();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float)P, 0));
        entries.add(new Entry((float)(I), 1));

        PieDataSet dataset = new PieDataSet(entries, "");
        ArrayList<String> labels = new ArrayList<String>();

        labels.add("Principle");
        labels.add("Interest");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setDescription("");
    }

    private void calculate(boolean isFromInitialLoad){
        resultText.setVisibility(View.VISIBLE);
        String loanAmountString, tenureString, interestString;
        loanAmountString = loanAmountText.getText().toString();
        tenureString = tenureText.getText().toString();
        interestString = interestText.getText().toString();
        if(!loanAmountString.equals("") && !tenureString.equals("") && !interestString.equals("")){
            double loanAmount = Double.parseDouble(loanAmountString);
            double tenure = Double.parseDouble(tenureString);
            double interest = Double.parseDouble(interestString);
            if(tenureUnitSelection.getSelectedItem().equals(LoanActivity.TENURE_UNITS[0])){
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
            AppMain.debug("EMI:"+emi);
            setDataIntoChart((float)P, (float)interestPaid);

            String resultString = String.format(getResources().getString(R.string.app_loan_result_text),
                    Math.round(emi), Math.round(P), Double.toString(interest), Math.round(tenure), Math.round(totalPaid));
            resultText.setText(resultString);
            pieChart.setVisibility(View.VISIBLE);
        }else if(!isFromInitialLoad){
            AppMain.dialogBuilder(this,
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }
    }
}
