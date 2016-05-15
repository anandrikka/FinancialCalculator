package com.wordpress.techanand.financialcalculator.db.tables;

/**
 * Created by Anand Rikka on 5/11/2016.
 */
public class CalculatorTable {

    public static final String NAME = "Calculators";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_UNIQUE_ID = "UNIQUE_ID";
    public static final String COLUMN_IS_VISIBLE = "IS_VISIBLE";
    public static final String COLUMN_ORDER = "ORDER_BY";

    public static final String CREATE_TABLE =
            "CREATE TABLE "+NAME+" (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME+ " TEXT, " + COLUMN_IS_VISIBLE + " TEXT, " +
                    COLUMN_UNIQUE_ID + " TEXT, " + COLUMN_ORDER + " INTEGER"+")";

    public static final String BULK_INSERT = "Insert or Replace into " +
            NAME +
            " (" +
            COLUMN_NAME +
            ", " +
            COLUMN_UNIQUE_ID +
            ", " +
            COLUMN_IS_VISIBLE +
            ", " +
            COLUMN_ORDER +
            ") values(?,?,?,?)";

}
