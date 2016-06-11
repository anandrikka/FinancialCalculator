package com.wordpress.techanand.financialcalculator.db.model;

/**
 * Created by Anand Rikka on 5/15/2016.
 */
public class Calculator {

    private long id;
    private String name;
    private String uniqueId;
    private int isVisible;
    private int orderBy;

    public final static String UNIQUE_FD_ID = "fixed_deposit";
    public final static String UNIQUE_RD_ID = "recurring_deposit";
    public final static String UNIQUE_STOCK_ID = "stock";
    public final static String UNIQUE_MUTUAL_FUND_ID = "mutual_fund";
    public final static String UNIQUE_TAX_ID = "tax";
    public final static String UNIQUE_LOAN_ID = "loan";
    public final static String UNIQUE_RETIREMENT_ID = "retirement";
    public final static String UNIQUE_GOAL_ID = "goal";


    public Calculator(){

    }

    public Calculator(long id, String name, String uniqueId, int isVisible, int orderBy) {
        this.id = id;
        this.name = name;
        this.uniqueId = uniqueId;
        this.isVisible = isVisible;
        this.orderBy = orderBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "Position of "+name+": "+orderBy;
    }
}
