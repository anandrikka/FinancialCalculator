package com.wordpress.techanand.financialcalculator.db.model;

import android.content.res.Resources;

import com.wordpress.techanand.financialcalculator.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorListContent {

    public static final List<CalculatorItem> ITEMS = new ArrayList<CalculatorItem>();
    public static final Map<Integer, CalculatorItem> ITEM_MAP = new HashMap<Integer, CalculatorItem>();

    private static void addItem(CalculatorItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static final List<CalculatorItem> getCalculatorsList(Resources resources){
        if(ITEMS.size() > 0){
            return ITEMS;
        }
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_STOCK,
                resources.getString(R.string.main_activity_stock_calculator), "stock"));
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_MUTUAL_FUND,
                resources.getString(R.string.main_activity_mutual_fund_calculator), "mutual_fund"));
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_FD,
                resources.getString(R.string.main_activity_fixed_deposit_calculator), "fixed_deposit"));
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_RD,
                resources.getString(R.string.main_activity_recurring_deposit_calculator), "recurring_deposit"));
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_INFLATION,
                resources.getString(R.string.main_activity_inflation_calculator), "inflation"));
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_EMI,
                resources.getString(R.string.main_activity_emi_calculator), "loan"));
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_RETIREMENT,
                resources.getString(R.string.main_activity_retirement_calculator), "retirement"));
        addItem(new CalculatorItem(CalculatorItem.UniqueId.UNIQUE_GOAL,
                resources.getString(R.string.main_activity_goal_calculator), "goal"));
        return ITEMS;
    }

    public static class CalculatorItem {
        private int id;
        private String name;
        private String imageName;

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

        public CalculatorItem(int id, String name, String imageName) {
            this.id = id;
            this.name = name;
            this.imageName = imageName;
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
}
