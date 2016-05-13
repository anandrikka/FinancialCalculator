package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 5/11/2016.
 */

public class MainCalcListItem {

    private String name;
    private int type;

    public MainCalcListItem(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public int getType() { return type; }
}
