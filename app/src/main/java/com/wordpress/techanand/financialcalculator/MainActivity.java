package com.wordpress.techanand.financialcalculator;

import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                    case AppConstants.FIXED_DEPOSIT:
                        launchItem = new Intent(MainActivity.this, FixedDepositActivity.class);
                        break;
                    case AppConstants.RECURING_DEPOSIT:
                        launchItem = new Intent(MainActivity.this, RecurringDeposityActivity.class);
                        break;
                    case AppConstants.PUBLIC_PROVIDENT_FUND:
                        launchItem = new Intent(MainActivity.this, PPFActivity.class);
                        break;
                    case AppConstants.STOCK:
                        launchItem = new Intent(MainActivity.this, StockPriceActivity.class);
                        break;
                    case AppConstants.MUTUAL_FUND:
                        launchItem = new Intent(MainActivity.this, MutualFundActivity.class);
                        break;
                    case AppConstants.LOAN:
                        launchItem = new Intent(MainActivity.this, LoanActivity.class);
                        break;
                    case AppConstants.TAX:
                        launchItem = new Intent(MainActivity.this, TaxActivity.class);
                        break;
                    case AppConstants.CALCULATOR:
                        launchItem = new Intent(MainActivity.this, CalculatorActivity.class);
                        break;
                    case AppConstants.MISCELLANEOUS:
                        launchItem = new Intent(MainActivity.this, MiscellaneousActivity.class);
                        break;
                    case AppConstants.SPLIT_CALC:
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
                new MainCalcListItem("Stock Price Calculator", AppConstants.STOCK),
                new MainCalcListItem("Mutual Fund Calculator", AppConstants.MUTUAL_FUND),
                new MainCalcListItem(getString(R.string.main_activity_list_fixedDeposit), AppConstants.FIXED_DEPOSIT),
                new MainCalcListItem("Recurring Deposit Calculator", AppConstants.RECURING_DEPOSIT),
                new MainCalcListItem("Loan Calculator", AppConstants.LOAN),
                new MainCalcListItem("Tax Calculator", AppConstants.TAX),
                new MainCalcListItem("Public Provident Fund", AppConstants.PUBLIC_PROVIDENT_FUND),
                new MainCalcListItem("Calculator", AppConstants.CALCULATOR),
                new MainCalcListItem("Split Calculator", AppConstants.SPLIT_CALC),
                new MainCalcListItem("Miscellaneous", AppConstants.MISCELLANEOUS)
        };
        return list;
    }
}
