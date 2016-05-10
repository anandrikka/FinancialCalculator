package com.wordpress.techanand.financialcalculator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wordpress.techanand.financialcalculator.db.tables.CalculatorTable;

/**
 * Created by Anand Rikka on 5/11/2016.
 */


public class AppOpenHelper extends SQLiteOpenHelper{

    public final static String DATABASE_NAME = "calc.db";
    public final static int DATABASE_VERSION = 1;

    public AppOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CalculatorTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CalculatorTable.NAME);
    }
}
