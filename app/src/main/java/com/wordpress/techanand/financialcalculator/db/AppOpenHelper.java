package com.wordpress.techanand.financialcalculator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wordpress.techanand.financialcalculator.app.AppMain;
import com.wordpress.techanand.financialcalculator.db.model.Calculator;
import com.wordpress.techanand.financialcalculator.db.tables.CalculatorTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand Rikka on 5/11/2016.
 */


public class AppOpenHelper extends SQLiteOpenHelper{

    public final static String DATABASE_NAME = "financial_calc.db";
    public final static int DATABASE_VERSION = 1;

    public AppOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Calculator Table
        db.execSQL(CalculatorTable.CREATE_TABLE);
        List<ContentValues> calculators = loadInitialDataForCalculators();
        for (ContentValues values: calculators) {
            db.insert(CalculatorTable.NAME, null, values);
        }
        Log.d(AppMain.LOG, "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CalculatorTable.NAME);
    }

    private List loadInitialDataForCalculators() {
        List<Calculator> initialLoadData = new ArrayList();
        List<ContentValues> content = new ArrayList();
        initialLoadData.add(new Calculator(0, "Stock Calculator", Calculator.UNIQUE_STOCK_ID, AppMain.TRUE, 1));
        initialLoadData.add(new Calculator(0, "Mutual Fund", Calculator.UNIQUE_MUTUAL_FUND_ID, AppMain.TRUE, 2));
        initialLoadData.add(new Calculator(0, "Fixed Deposit", Calculator.UNIQUE_FD_ID, AppMain.TRUE, 3));
        initialLoadData.add(new Calculator(0, "Recurring Deposit", Calculator.UNIQUE_RD_ID, AppMain.TRUE, 4));
        initialLoadData.add(new Calculator(0, "Inflation Calculator", Calculator.UNIQUE_INFLATION_ID, AppMain.TRUE, 5));
        //initialLoadData.add(new Calculator(0, "Tax Calculator", Calculator.UNIQUE_TAX_ID, AppMain.TRUE, 5));
        initialLoadData.add(new Calculator(0, "Loan Calculator", Calculator.UNIQUE_LOAN_ID, AppMain.TRUE, 6));
        initialLoadData.add(new Calculator(0, "Retirement Calculator", Calculator.UNIQUE_RETIREMENT_ID, AppMain.TRUE, 7));
        initialLoadData.add(new Calculator(0, "Goal Calculator", Calculator.UNIQUE_GOAL_ID, AppMain.TRUE, 8));
        //initialLoadData.add(new Calculator(0, "Budget Calculator", Calculator.UNIQUE_BUDGET_ID, AppMain.TRUE, 8));
        for (Calculator calculator:initialLoadData) {
            ContentValues values = new ContentValues();
            values.put(CalculatorTable.COLUMN_NAME, calculator.getName());
            values.put(CalculatorTable.COLUMN_IS_VISIBLE, calculator.getIsVisible());
            values.put(CalculatorTable.COLUMN_UNIQUE_ID, calculator.getUniqueId());
            values.put(CalculatorTable.COLUMN_ORDER, calculator.getOrderBy());
            content.add(values);
        }
        return content;
    }

}
