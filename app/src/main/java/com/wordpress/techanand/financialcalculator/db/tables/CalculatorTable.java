package com.wordpress.techanand.financialcalculator.db.tables;

/**
 * Created by Anand Rikka on 5/11/2016.
 */
public class CalculatorTable {

    public static final String NAME = "Calculator";

    public static final String COLUMN_ID = "catId";
    public static final String COLUMN_NAME = "catName";

    public static final String CREATE_TABLE =
            "CREATE "+NAME+" (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME+ " TEXT" + ")";
}
