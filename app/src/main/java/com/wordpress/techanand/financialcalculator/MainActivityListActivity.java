package com.wordpress.techanand.financialcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.wordpress.techanand.financialcalculator.app.activities.FixedDepositActivity;
import com.wordpress.techanand.financialcalculator.app.activities.GoalActivity;
import com.wordpress.techanand.financialcalculator.app.activities.InflationActivity;
import com.wordpress.techanand.financialcalculator.app.activities.LoanActivity;
import com.wordpress.techanand.financialcalculator.app.activities.MutualFundActivity;
import com.wordpress.techanand.financialcalculator.app.activities.RecurringDepositActivity;
import com.wordpress.techanand.financialcalculator.app.activities.RetirementActivity;
import com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity;
import com.wordpress.techanand.financialcalculator.app.fragments.FixedDepositFragment;
import com.wordpress.techanand.financialcalculator.db.model.CalculatorListContent;
import com.wordpress.techanand.financialcalculator.db.model.CalculatorListModel;

import java.util.List;

public class MainActivityListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mainactivity_list);
        assert recyclerView != null;
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(CalculatorListContent.getCalculatorsList(getResources())));
        RecyclerView.LayoutManager lLayout;
        if (findViewById(R.id.mainactivity_detail_container) != null) {
            mTwoPane = true;
            lLayout = new LinearLayoutManager(MainActivityListActivity.this);
            recyclerView.setLayoutManager(lLayout);
        }else{
            lLayout = new GridLayoutManager(MainActivityListActivity.this, 4);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(lLayout);
        }
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<CalculatorListContent.CalculatorItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<CalculatorListContent.CalculatorItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mainactivity_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            String packageName = getApplicationContext().getPackageName();
            Resources resources = getApplicationContext().getResources();
            int resId =resources.getIdentifier(holder.mItem.getImageName(), "drawable", packageName);
            holder.mIdView.setImageResource(resId);
            holder.mContentView.setText(mValues.get(position).getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        switch (holder.mItem.getId()){
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_FD:
                                arguments.putString(FixedDepositFragment.class.getName(), Integer.toString(holder.mItem.getId()));
                                FixedDepositFragment fragment = new FixedDepositFragment();
                                //fragment.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.mainactivity_detail_container, fragment, FixedDepositFragment.class.getName())
                                        .commit();
                                break;
                            /*case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_RD:
                                launchItem = new Intent(MainActivityListActivity.this, RecurringDepositActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_STOCK:
                                launchItem = new Intent(MainActivityListActivity.this, StockPriceActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_MUTUAL_FUND:
                                launchItem = new Intent(MainActivityListActivity.this, MutualFundActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_EMI:
                                launchItem = new Intent(MainActivityListActivity.this, LoanActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_RETIREMENT:
                                launchItem = new Intent(MainActivityListActivity.this, RetirementActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_GOAL:
                                launchItem = new Intent(MainActivityListActivity.this, GoalActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_INFLATION:
                                launchItem = new Intent(MainActivityListActivity.this, InflationActivity.class);
                                break;*/
                            default:
                                Toast.makeText(MainActivityListActivity.this, holder.mItem.getName()+" Not Defined !", Toast.LENGTH_SHORT).show();
                        }
                        arguments.putString(MainActivityDetailFragment.ARG_ITEM_ID, Integer.toString(holder.mItem.getId()));
                        MainActivityDetailFragment fragment = new MainActivityDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainactivity_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        //intent.putExtra(MainActivityDetailFragment.ARG_ITEM_ID, Integer.toString(holder.mItem.getId()));
                        Intent launchItem = null;
                        switch (holder.mItem.getId()){
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_FD:
                                launchItem = new Intent(MainActivityListActivity.this, FixedDepositActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_RD:
                                launchItem = new Intent(MainActivityListActivity.this, RecurringDepositActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_STOCK:
                                launchItem = new Intent(MainActivityListActivity.this, StockPriceActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_MUTUAL_FUND:
                                launchItem = new Intent(MainActivityListActivity.this, MutualFundActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_EMI:
                                launchItem = new Intent(MainActivityListActivity.this, LoanActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_RETIREMENT:
                                launchItem = new Intent(MainActivityListActivity.this, RetirementActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_GOAL:
                                launchItem = new Intent(MainActivityListActivity.this, GoalActivity.class);
                                break;
                            case CalculatorListContent.CalculatorItem.UniqueId.UNIQUE_INFLATION:
                                launchItem = new Intent(MainActivityListActivity.this, InflationActivity.class);
                                break;
                            default:
                                Toast.makeText(MainActivityListActivity.this, holder.mItem.getName()+" Not Defined !", Toast.LENGTH_SHORT).show();
                        }
                        if(launchItem != null){
                            context.startActivity(launchItem);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mIdView;
            public final TextView mContentView;
            public CalculatorListContent.CalculatorItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (ImageView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
