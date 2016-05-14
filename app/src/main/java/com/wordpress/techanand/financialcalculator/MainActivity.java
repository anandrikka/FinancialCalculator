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
import android.widget.ListView;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.app.AppConstants;
import com.wordpress.techanand.financialcalculator.app.models.MainCalcListItem;
import com.wordpress.techanand.financialcalculator.ui.listview.adapters.MainActivityListAdapter;


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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final MainCalcListItem[] sample = createMainList();

        ListView calcList = (ListView) findViewById(R.id.content_main_mainList);
        MainActivityListAdapter arrayAdapter = new MainActivityListAdapter(this, R.id.content_main_list_text, sample);
        calcList.setAdapter(arrayAdapter);
        calcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainCalcListItem item = sample[position];
                Intent launchItem = null;
                switch (item.getType()){
                    case MainCalcListItem.FIXED_DEPOSIT:
                        launchItem = new Intent(MainActivity.this, FixedDepositActivity.class);
                        break;
                    case MainCalcListItem.RECURRING_DEPOSIT:
                        launchItem = new Intent(MainActivity.this, RecurringDeposityActivity.class);
                        break;
                    case MainCalcListItem.PUBLIC_PROVIDENT_FUND:
                        launchItem = new Intent(MainActivity.this, PPFActivity.class);
                        break;
                    case MainCalcListItem.STOCK:
                        launchItem = new Intent(MainActivity.this, StockPriceActivity.class);
                        break;
                    case MainCalcListItem.MUTUAL_FUND:
                        launchItem = new Intent(MainActivity.this, MutualFundActivity.class);
                        break;
                    case MainCalcListItem.LOAN:
                        launchItem = new Intent(MainActivity.this, LoanActivity.class);
                        break;
                    case MainCalcListItem.TAX:
                        launchItem = new Intent(MainActivity.this, TaxActivity.class);
                        break;
                    case MainCalcListItem.CALCULATOR:
                        launchItem = new Intent(MainActivity.this, CalculatorActivity.class);
                        break;
                    case MainCalcListItem.MISCELLANEOUS:
                        launchItem = new Intent(MainActivity.this, MiscellaneousActivity.class);
                        break;
                    case MainCalcListItem.SPLIT_CALC:
                        launchItem = new Intent(MainActivity.this, SplitActivity.class);
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

    //todo: How to make this list load only once at the time of app install ?
    public MainCalcListItem[] createMainList(){
        MainCalcListItem[] list = {
                new MainCalcListItem("Stock Price Calculator", MainCalcListItem.STOCK),
                new MainCalcListItem("Mutual Fund Calculator", MainCalcListItem.MUTUAL_FUND),
                new MainCalcListItem(getString(R.string.main_activity_list_fixedDeposit), MainCalcListItem.FIXED_DEPOSIT),
                new MainCalcListItem("Recurring Deposit Calculator", MainCalcListItem.RECURRING_DEPOSIT),
                new MainCalcListItem("Loan Calculator", MainCalcListItem.LOAN),
                new MainCalcListItem("Tax Calculator", MainCalcListItem.TAX),
                new MainCalcListItem("Public Provident Fund", MainCalcListItem.PUBLIC_PROVIDENT_FUND),
                //new MainCalcListItem("Calculator", AppConstants.CALCULATOR),
                new MainCalcListItem("Split Calculator", MainCalcListItem.SPLIT_CALC),
                new MainCalcListItem("Miscellaneous", MainCalcListItem.MISCELLANEOUS)
        };
        return list;
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
