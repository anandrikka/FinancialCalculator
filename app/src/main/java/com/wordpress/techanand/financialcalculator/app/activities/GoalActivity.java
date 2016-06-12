package com.wordpress.techanand.financialcalculator.app.activities;

import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.app.fragments.GoalFragment;
import com.wordpress.techanand.financialcalculator.app.fragments.MainPrefs;
import com.wordpress.techanand.financialcalculator.app.models.GoalObject;

public class GoalActivity extends AppCompatActivity implements GoalFragment.GoalFragmentListener{

    private GoalFragment goalFragment;
    private CardView resultCard;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goalFragment = (GoalFragment) getSupportFragmentManager().findFragmentByTag(GoalFragment.class.getName());

        if(goalFragment == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new GoalFragment(), GoalFragment.class.getName())
                    .commit();
        }
        if(resultCard != null){
            resultCard.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        goalFragment = (GoalFragment) getSupportFragmentManager().findFragmentByTag(GoalFragment.class.getName());
    }

    @Override
    public void calculate(GoalObject goalObject) {
        //Inflated amount = A = P * (1+r)^t
        double inflation, timePeriod, todayValue, savedValue, expectedReturn;
        inflation = goalObject.getInflationPercent();
        timePeriod = goalObject.getDuration();
        todayValue = goalObject.getTodayValue();
        savedValue = goalObject.getSavedAmount();
        expectedReturn = goalObject.getReturnsPercent();
        double r = inflation/100.0;
        double x = 1+r;
        x = Math.pow(x, timePeriod);
        x = todayValue * x;
        double targetAmount = x;
        double finalTarget = x; // Final Target Amount
        if(savedValue > 0){
            x = 0.0;
            r = 0.0;
            r = expectedReturn/100.0;
            x = 1+r;
            x = Math.pow(x, timePeriod);
            x = savedValue * x;
            targetAmount = targetAmount-x;
        }

        //P = (M * r)/(((1+r)^n)-1)(1+r)
        x = 0.0;
        r = 0.0;
        r = expectedReturn/12.0; //monthly interest
        r = r/100.0;
        double n = timePeriod * 12;
        x = targetAmount * r;
        double y = 1+r;
        y = Math.pow(y, n);
        y = y-1;
        double y1 = 1+r;
        y = y*y1;
        double monthlyInvestment = x/y;
        String resultText =  String.format(getResources().getString(R.string.app_mutualfunds_goal_result_string,
                MainPrefs.getFormattedNumber(Math.round(finalTarget)), Math.round(timePeriod),
                MainPrefs.getFormattedNumber(Math.round(monthlyInvestment))));

        CharSequence sq = Html.fromHtml(resultText);

        resultCard = (CardView) findViewById(R.id.app_goal_result_card_id);
        resultTextView = (TextView) findViewById(R.id.app_goal_result_card_text_id);
        if(resultCard == null){
            resultCard = new CardView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            resultCard.setLayoutParams(layoutParams);
            if(resultTextView == null){
                resultTextView = new TextView(this);
            }
            resultCard.setId(R.id.app_goal_result_card_id);
            resultCard.setPadding(16, 16, 16, 16);
            resultTextView.setPadding(8, 8, 8, 8);
            //resultTextView.setPadding(8, 8, 8, 8);
            resultTextView.setBackgroundColor(Color.WHITE);
            resultTextView.setText(sq);
            resultTextView.setId(R.id.app_goal_result_card_text_id);
            resultTextView.setTextSize(16.0f);
            resultTextView.setLineSpacing(1.5f, 1f);
            ViewGroup appArea = (ViewGroup) findViewById(R.id.appArea);
            //appArea.setPadding(16, 16, 16, 16);
            resultCard.addView(resultTextView);
            appArea.addView(resultCard);
        }else{
            resultTextView.setText(sq);
        }
        resultCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void reset() {
       if(resultCard != null){
           resultCard.setVisibility(View.GONE);
       }

    }
}
