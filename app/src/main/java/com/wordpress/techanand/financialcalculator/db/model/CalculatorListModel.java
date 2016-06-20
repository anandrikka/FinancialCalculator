package com.wordpress.techanand.financialcalculator.db.model;

import android.content.res.Resources;

import com.wordpress.techanand.financialcalculator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand Rikka on 6/19/2016.
 */
public class CalculatorListModel {

    private int id;
    private String name;
    private String imageName;

    public CalculatorListModel(int id, String name, String imageName) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public static final List<CalculatorListModel> getCalculatorsList(Resources resources){
        List<CalculatorListModel> list = new ArrayList<>();

        list.add(new CalculatorListModel(UniqueId.UNIQUE_STOCK, resources.getString(R.string.main_activity_stock_calculator), "stock"));
        list.add(new CalculatorListModel(UniqueId.UNIQUE_MUTUAL_FUND, resources.getString(R.string.main_activity_mutual_fund_calculator), "mutual_fund"));
        list.add(new CalculatorListModel(UniqueId.UNIQUE_FD, resources.getString(R.string.main_activity_fixed_deposit_calculator), "fixed_deposit"));
        list.add(new CalculatorListModel(UniqueId.UNIQUE_RD, resources.getString(R.string.main_activity_recurring_deposit_calculator), "recurring_deposit"));
        list.add(new CalculatorListModel(UniqueId.UNIQUE_INFLATION, resources.getString(R.string.main_activity_inflation_calculator), "inflation"));
        list.add(new CalculatorListModel(UniqueId.UNIQUE_EMI, resources.getString(R.string.main_activity_emi_calculator), "loan"));
        list.add(new CalculatorListModel(UniqueId.UNIQUE_RETIREMENT, resources.getString(R.string.main_activity_retirement_calculator), "retirement"));
        list.add(new CalculatorListModel(UniqueId.UNIQUE_GOAL, resources.getString(R.string.main_activity_goal_calculator), "goal"));

        return list;
    }

    public static class UniqueId{
        public static final int UNIQUE_STOCK = 1;
        public static final int UNIQUE_MUTUAL_FUND = 2;
        public static final int UNIQUE_FD = 3;
        public static final int UNIQUE_RD = 4;
        public static final int UNIQUE_INFLATION = 5;
        public static final int UNIQUE_EMI = 6;
        public static final int UNIQUE_RETIREMENT = 7;
        public static final int UNIQUE_GOAL = 8;
    }
}
