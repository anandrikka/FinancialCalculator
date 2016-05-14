package com.wordpress.techanand.financialcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.activities.fixeddeposits.FixedDepositActivity;
import com.wordpress.techanand.financialcalculator.activities.loans.LoanActivity;
import com.wordpress.techanand.financialcalculator.activities.miscellaneous.MiscellaneousActivity;
import com.wordpress.techanand.financialcalculator.activities.miscellaneous.PPFActivity;
import com.wordpress.techanand.financialcalculator.activities.miscellaneous.SplitActivity;
import com.wordpress.techanand.financialcalculator.activities.mutualfunds.MutualFundActivity;
import com.wordpress.techanand.financialcalculator.activities.recurringdeposit.RecurringDeposityActivity;
import com.wordpress.techanand.financialcalculator.activities.stock.StockPriceActivity;
import com.wordpress.techanand.financialcalculator.activities.tax.TaxActivity;
import com.wordpress.techanand.financialcalculator.app.AppConstants;
import com.wordpress.techanand.financialcalculator.app.models.MainCalcListItem;
import com.wordpress.techanand.financialcalculator.db.AppOpenHelper;
import com.wordpress.techanand.financialcalculator.db.datasources.CalculatorDataSource;
import com.wordpress.techanand.financialcalculator.db.model.Calculator;
import com.wordpress.techanand.financialcalculator.db.tables.CalculatorTable;
import com.wordpress.techanand.financialcalculator.ui.listview.adapters.MainActivityListAdapter;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        AppOpenHelper helper = new AppOpenHelper(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final List<Calculator> calculators = new CalculatorDataSource(this).getAllRows();
        calculators.addAll(calculators);
        calculators.addAll(calculators);

        GridView calcList = (GridView) findViewById(R.id.content_main_mainList);
        MainActivityListAdapter arrayAdapter = new MainActivityListAdapter(this, R.id.gridText, calculators);
        calcList.setAdapter(arrayAdapter);
        calcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Calculator item = calculators.get(position);
                Intent launchItem = null;
                switch (item.getImageName()){
                    case Calculator.UNIQUE_FD_ID:
                        launchItem = new Intent(MainActivity.this, FixedDepositActivity.class);
                        break;
                    case Calculator.UNIQUE_RD_ID:
                        launchItem = new Intent(MainActivity.this, RecurringDeposityActivity.class);
                        break;
                    case Calculator.UNIQUE_PPF_ID:
                        launchItem = new Intent(MainActivity.this, PPFActivity.class);
                        break;
                    case Calculator.UNIQUE_STOCK_ID:
                        launchItem = new Intent(MainActivity.this, StockPriceActivity.class);
                        break;
                    case Calculator.UNIQUE_MUTUAL_FUND_ID:
                        launchItem = new Intent(MainActivity.this, MutualFundActivity.class);
                        break;
                    case Calculator.UNIQUE_LOAN_ID:
                        launchItem = new Intent(MainActivity.this, LoanActivity.class);
                        break;
                    case Calculator.UNIQUE_TAX_ID:
                        launchItem = new Intent(MainActivity.this, TaxActivity.class);
                        break;
                    case Calculator.UNIQUE_ROI_ID:
                        //launchItem = new Intent(MainActivity.this, MiscellaneousActivity.class);
                        Toast.makeText(MainActivity.this, item.getName()+" Not Defined !", Toast.LENGTH_SHORT).show();
                        break;
                    case Calculator.UNIQUE_CI_ID:
                        //launchItem = new Intent(MainActivity.this, SplitActivity.class);
                        Toast.makeText(MainActivity.this, item.getName()+" Not Defined !", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, item.getName()+" Not Defined !", Toast.LENGTH_SHORT).show();
                }
                if(launchItem != null){
                    MainActivity.this.startActivity(launchItem);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit) {
        } else if (id == R.id.settings) {

        } else if (id == R.id.about) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
