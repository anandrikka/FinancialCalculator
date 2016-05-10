package com.wordpress.techanand.financialcalculator.db.models;

/**
 * Created by Anand Rikka on 5/11/2016.
 */

public class CalculatorModel {

    private int id;
    private String name;

    public CalculatorModel(int id, String name) {
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
