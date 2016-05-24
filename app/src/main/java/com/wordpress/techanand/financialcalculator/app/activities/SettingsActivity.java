package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.MainActivity;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.db.model.Calculator;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final List<String> settings = new ArrayList<>();
        settings.add("NSE");
        settings.add("BSE");

        ListView settingsList = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.settings_list, R.id.settings_item, settings);
        settingsList.setAdapter(arrayAdapter);

        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = settings.get(position);
                Intent launchItem = null;
                switch (item) {
                    case "NSE":
                        AppMain.toast(SettingsActivity.this, item, Toast.LENGTH_LONG);
                        break;
                    case "BSE":
                        AppMain.toast(SettingsActivity.this, item, Toast.LENGTH_LONG);
                        break;
                    default:
                        AppMain.toast(SettingsActivity.this, "Not Applicable", Toast.LENGTH_LONG);
                }
            }
        });

    }
}
