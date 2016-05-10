package com.wordpress.techanand.financialcalculator.app.models;

/**
 * Created by Anand Rikka on 5/11/2016.
 */

public class CalculatorInfo {

    private int id;
    private String name;

    public CalculatorInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
