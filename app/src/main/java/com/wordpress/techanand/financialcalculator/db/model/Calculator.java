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

    public final static String UNIQUE_FD_ID = "FIXED_DEPOSIT";
    public final static String UNIQUE_RD_ID = "RECURRING_DEPOSIT";
    public final static String UNIQUE_PPF_ID = "PPF";
    public final static String UNIQUE_STOCK_ID = "STOCK";
    public final static String UNIQUE_MUTUAL_FUND_ID = "MUTUAL_FUND";
    public final static String UNIQUE_TAX_ID = "TAX";
    public final static String UNIQUE_LOAN_ID = "LOAN";
    public final static String UNIQUE_RETIREMENT_ID = "RETIREMENT";
    public final static String UNIQUE_ROI_ID = "ROI";
    public final static String UNIQUE_CI_ID = "CI";



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

    public String getImageName() {
        return uniqueId;
    }

    public void setImageName(String uniqueId) {
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
