package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 5/11/2016.
 */

public class MainCalcListItem {

    private String name;
    private int type;

    public final static int FIXED_DEPOSIT = 1;
    public final static int RECURRING_DEPOSIT = 2;
    public final static int PUBLIC_PROVIDENT_FUND = 3;
    public final static int STOCK = 4;
    public final static int TAX = 5;
    public final static int LOAN = 6;
    public final static int SPLIT_CALC = 7;
    public final static int MISCELLANEOUS = 8;
    public final static int CALCULATOR = 9;
    public final static int MUTUAL_FUND = 10;

    public MainCalcListItem(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public int getType() { return type; }
}
