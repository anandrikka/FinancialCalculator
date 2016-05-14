package com.wordpress.techanand.financialcalculator.app.models;

import com.wordpress.techanand.financialcalculator.db.tables.CalculatorTable;

import java.util.HashMap;

/**
 * Created by Anand Rikka on 5/11/2016.
 */

public class MainCalcListItem {

    private String name;
    private int type;
    private String id;

    public final static int FIXED_DEPOSIT = 1;
    public final static String UNIQUE_FD_ID = "_" + FIXED_DEPOSIT;
    public final static int RECURRING_DEPOSIT = 2;
    public final static String UNIQUE_RD_ID = "_" + RECURRING_DEPOSIT;
    public final static int PUBLIC_PROVIDENT_FUND = 3;
    public final static String UNIQUE_PPF_ID = "_" + PUBLIC_PROVIDENT_FUND;
    public final static int STOCK = 4;
    public final static String UNIQUE_STOCK_ID = "_" + STOCK;
    public final static int TAX = 5;
    public final static String UNIQUE_TAX_ID = "_" + TAX;
    public final static int LOAN = 6;
    public final static String UNIQUE_LOAN_ID = "_" + LOAN;
    public final static int SPLIT_CALC = 7;
    public final static String UNIQUE_SPLIT_ID = "_" + SPLIT_CALC;
    public final static int MISCELLANEOUS = 8;
    public final static String UNIQUE_MISCELLANEOUS_ID = "_" + MISCELLANEOUS;
    public final static int CALCULATOR = 9;
    public final static String UNIQUE_CALCULATOR_ID = "_" + CALCULATOR;
    public final static int MUTUAL_FUND = 10;
    public final static String UNIQUE_MUTUAL_FUND_ID = "_" + MUTUAL_FUND;

    public MainCalcListItem(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public int getType() { return type; }

    public final static HashMap<String, Integer> map = new HashMap<>();

    static {
        map.put(UNIQUE_FD_ID, FIXED_DEPOSIT);
        map.put(UNIQUE_RD_ID, RECURRING_DEPOSIT);
        map.put(UNIQUE_PPF_ID, PUBLIC_PROVIDENT_FUND);
        map.put(UNIQUE_STOCK_ID, STOCK);
        map.put(UNIQUE_TAX_ID, TAX);
        map.put(UNIQUE_LOAN_ID, LOAN);
        map.put(UNIQUE_SPLIT_ID, SPLIT_CALC);
        map.put(UNIQUE_CALCULATOR_ID, CALCULATOR);
        map.put(UNIQUE_MISCELLANEOUS_ID, MISCELLANEOUS);
        map.put(UNIQUE_MUTUAL_FUND_ID, MUTUAL_FUND);
    }

}
