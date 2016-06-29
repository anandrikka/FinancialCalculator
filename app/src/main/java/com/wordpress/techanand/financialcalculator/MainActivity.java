package com.wordpress.techanand.financialcalculator;

/**
 * Created by Anand Rikka on 05/11/2016
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wordpress.techanand.financialcalculator.app.activities.AboutActivity;
import com.wordpress.techanand.financialcalculator.app.activities.AppPreferencesActivity;
import com.wordpress.techanand.financialcalculator.app.activities.EditActivity;
import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;
import com.wordpress.techanand.financialcalculator.app.activities.GoalActivity;
import com.wordpress.techanand.financialcalculator.app.activities.InflationActivity;
import com.wordpress.techanand.financialcalculator.app.activities.LoanActivity;
import com.wordpress.techanand.financialcalculator.app.activities.MutualFundActivity;
import com.wordpress.techanand.financialcalculator.app.activities.RecurringDepositActivity;
import com.wordpress.techanand.financialcalculator.app.activities.RetirementActivity;
import com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity;
import com.wordpress.techanand.financialcalculator.db.model.CalculatorListModel;
import com.wordpress.techanand.financialcalculator.ui.listview.holders.TextViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private List<CalculatorListModel> list;
    GridView calcList;

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
        reloadList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
    }

    public void reloadList(){
        Gson gson = new Gson();
        final List<CalculatorListModel> calculators = CalculatorListModel.getCalculatorsList(getResources());
        String defaultCalcOrder = gson.toJson(calculators);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String calcListOrder = sharedPreferences.getString("main_list", defaultCalcOrder);
        CalculatorListModel[] calcArray = gson.fromJson(calcListOrder, CalculatorListModel[].class);
        List<CalculatorListModel> arrayAsList = Arrays.asList(calcArray);
        list = new ArrayList<>(arrayAsList);
        calcList = (GridView) findViewById(R.id.gridViewList);
        CalculatorsListAdapter arrayAdapter = new CalculatorsListAdapter(this, R.id.gridText, list);
        calcList.setAdapter(arrayAdapter);
        calcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalculatorListModel item = list.get(position);
                Intent launchItem = null;
                switch (item.getId()){
                    case CalculatorListModel.UniqueId.UNIQUE_FD:
                        launchItem = new Intent(MainActivity.this, FixedDepositActivity.class);
                        break;
                    case CalculatorListModel.UniqueId.UNIQUE_RD:
                        launchItem = new Intent(MainActivity.this, RecurringDepositActivity.class);
                        break;
                    case CalculatorListModel.UniqueId.UNIQUE_STOCK:
                        launchItem = new Intent(MainActivity.this, StockPriceActivity.class);
                        break;
                    case CalculatorListModel.UniqueId.UNIQUE_MUTUAL_FUND:
                        launchItem = new Intent(MainActivity.this, MutualFundActivity.class);
                        break;
                    case CalculatorListModel.UniqueId.UNIQUE_EMI:
                        launchItem = new Intent(MainActivity.this, LoanActivity.class);
                        break;
                    case CalculatorListModel.UniqueId.UNIQUE_RETIREMENT:
                        launchItem = new Intent(MainActivity.this, RetirementActivity.class);
                        break;
                    case CalculatorListModel.UniqueId.UNIQUE_GOAL:
                        launchItem = new Intent(MainActivity.this, GoalActivity.class);
                        break;
                    case CalculatorListModel.UniqueId.UNIQUE_INFLATION:
                        launchItem = new Intent(MainActivity.this, InflationActivity.class);
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
            startActivity(new Intent(this, EditActivity.class));
        } else if (id == R.id.settings) {
            startActivity(new Intent(this, AppPreferencesActivity.class));
        } else if (id == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text that will be shared.</p>"));
            startActivity(Intent.createChooser(sharingIntent,"Share using"));
        } else if (id == R.id.nav_send) {
            String packageName = this.getPackageName();
            packageName = "com.facebook.katana";
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            /*To count with Play market backstack, After pressing back button, to taken back to our application, we need to add following flags to intent.*/
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class CalculatorsListAdapter extends ArrayAdapter {

        private List<CalculatorListModel> objects;

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        public CalculatorsListAdapter(Context context, int resource, List<CalculatorListModel> objects) {
            super(context, resource, objects);
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextViewHolder viewHolder = null;
            CalculatorListModel listViewItem = objects.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_calculator_list, null);
                TextView textView = (TextView) convertView.findViewById(R.id.gridText);
                textView.setText(listViewItem.getName());
                ImageView image = (ImageView) convertView.findViewById(R.id.gridImage);
                String packageName = getContext().getPackageName();
                Resources resources = getContext().getResources();
                int resId =resources.getIdentifier(listViewItem.getImageName(), "drawable", packageName);
                image.setImageResource(resId);
                viewHolder = new TextViewHolder(textView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (TextViewHolder) convertView.getTag();
            }
            viewHolder.getText().setText(listViewItem.getName());
            return convertView;
        }

    }
}
