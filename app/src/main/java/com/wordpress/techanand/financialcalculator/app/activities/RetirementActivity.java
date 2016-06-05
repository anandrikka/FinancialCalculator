package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;

public class RetirementActivity extends AppCompatActivity {

    private EditText currentAgeText, retirementAgeText, lifeExpectancyText,
            monthlyExpText, inflationText, existingInvestmentText, expectedReturnsText;
    private Button resetButton, calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentAgeText = (EditText) findViewById(R.id.current_age);
        retirementAgeText = (EditText) findViewById(R.id.retirement_age);
        lifeExpectancyText = (EditText) findViewById(R.id.life_expentency);
        monthlyExpText = (EditText) findViewById(R.id.monthly_expenses);
        inflationText = (EditText) findViewById(R.id.inflation_rate);
        existingInvestmentText = (EditText) findViewById(R.id.existing_investment);
        expectedReturnsText = (EditText) findViewById(R.id.expected_return);
        resetButton = (Button) findViewById(R.id.reset);
        calculateButton = (Button) findViewById(R.id.calculate);
        resetButtonListener();
        calculateButtonListener();
        calculate(true);
    }

    private void initializeData(){
        currentAgeText = (EditText) findViewById(R.id.current_age);
        retirementAgeText = (EditText) findViewById(R.id.retirement_age);
        lifeExpectancyText = (EditText) findViewById(R.id.life_expentency);
        monthlyExpText = (EditText) findViewById(R.id.monthly_expenses);
        inflationText = (EditText) findViewById(R.id.inflation_rate);
        existingInvestmentText = (EditText) findViewById(R.id.existing_investment);
        expectedReturnsText = (EditText) findViewById(R.id.expected_return);
        resetButton = (Button) findViewById(R.id.reset);
        calculateButton = (Button) findViewById(R.id.calculate);
    }

    private void resetButtonListener(){
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAgeText.setText("");
                retirementAgeText.setText("");
                lifeExpectancyText.setText("");
                monthlyExpText.setText("");
                inflationText.setText("");
                existingInvestmentText.setText("");
                expectedReturnsText.setText("");
            }
        });
    }

    private void calculateButtonListener(){
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(false);
            }
        });
    }

    private void calculate(boolean isInitialLoad){
        String currentAgeString, retirementAgeString, lifeExpectancyString,
                monthlyExpString, inflationString, existingInvestmentString, expectedReturnsString;
        currentAgeString = currentAgeText.getText().toString();
        retirementAgeString = retirementAgeText.getText().toString();
        lifeExpectancyString = lifeExpectancyText.getText().toString();
        monthlyExpString = monthlyExpText.getText().toString();
        inflationString = inflationText.getText().toString();;
        existingInvestmentString = existingInvestmentText.getText().toString();
        expectedReturnsString = expectedReturnsText.getText().toString();

        if(!currentAgeString.equals("") && !retirementAgeString.equals("") &&
                !lifeExpectancyString.equals("") && !monthlyExpString.equals("") &&
                !inflationString.equals("") && !expectedReturnsString.equals("")){
            double currentAge, retirementAge, lifeExpectancy, monthlyExpenses, inflation,
                    existingInvestment, expectedReturns, workYearsLeft, yearsToSurvive;
            currentAge = Double.parseDouble(currentAgeString);
            retirementAge = Double.parseDouble(retirementAgeString);
            lifeExpectancy = Double.parseDouble(lifeExpectancyString);
            monthlyExpenses = Double.parseDouble(monthlyExpString);
            inflation = Double.parseDouble(inflationString);
            if(existingInvestmentString.equals("")){
                existingInvestment = 0.0;
            }else{
                existingInvestment = Double.parseDouble(existingInvestmentString);
            }
            expectedReturns = Double.parseDouble(expectedReturnsString);
            workYearsLeft = retirementAge - currentAge;
            yearsToSurvive =  lifeExpectancy - retirementAge;

            double r, n, P, A;

        }else if(!isInitialLoad){
            AppMain.dialogBuilder(this,
                    "Fill Fields",
                    "Give Input for all fields !",
                    "OK").create().show();
        }

    }
}
