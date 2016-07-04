package com.wordpress.techanand.financialcalculator.app.activities;

/**
 * Created by Anand Rikka on 6/12/2016
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.wordpress.techanand.financialcalculator.R;

import com.wordpress.techanand.financialcalculator.app.fragments.GoalFragment;

public class GoalActivity extends AppCompatActivity {

    private GoalFragment goalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goalFragment = (GoalFragment) getSupportFragmentManager().findFragmentById(R.id.goal_form);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
