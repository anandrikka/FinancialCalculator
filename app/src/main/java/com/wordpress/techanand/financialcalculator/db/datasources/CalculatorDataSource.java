package com.wordpress.techanand.financialcalculator.db.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.wordpress.techanand.financialcalculator.db.model.Calculator;
import com.wordpress.techanand.financialcalculator.db.tables.CalculatorTable;

import java.util.ArrayList;

/**
 * Created by Anand Rikka on 5/14/2016.
 */

public class CalculatorDataSource extends GenericDataSource{

    public CalculatorDataSource(Context context) {
       super(context);
    }

    @Override
    public boolean insert(Object row) {
        Calculator calculator = (Calculator) row;
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalculatorTable.COLUMN_NAME, calculator.getName());
        contentValues.put(CalculatorTable.COLUMN_IS_VISIBLE, calculator.getIsVisible());
        contentValues.put(CalculatorTable.COLUMN_UNIQUE_ID, calculator.getImageName());
        contentValues.put(CalculatorTable.COLUMN_ORDER, calculator.getOrderBy());
        getWritableDB().insert(CalculatorTable.NAME, null, contentValues);
        return true;
    }

    @Override
    public ArrayList getAllRows() {
        ArrayList<Calculator> list = new ArrayList<Calculator>();
        Cursor cursor =  getReadableDB().rawQuery( "select * from "+CalculatorTable.NAME, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Calculator calculator = new Calculator();
            calculator.setId(cursor.getLong(cursor.getColumnIndex(CalculatorTable.COLUMN_ID)));
            calculator.setName(cursor.getString(cursor.getColumnIndex(CalculatorTable.COLUMN_NAME)));
            calculator.setImageName(cursor.getString(cursor.getColumnIndex(CalculatorTable.COLUMN_UNIQUE_ID)));
            calculator.setIsVisible(cursor.getInt(cursor.getColumnIndex(CalculatorTable.COLUMN_IS_VISIBLE)));
            calculator.setOrderBy(cursor.getInt(cursor.getColumnIndex(CalculatorTable.COLUMN_ORDER)));
            list.add(calculator);
            cursor.moveToNext();
        }
        return list;
    }

    @Override
    public ArrayList getRowsById(long id) {
        ArrayList<Calculator> list = new ArrayList<Calculator>();
        Cursor cursor = getRowsByIdCursor(CalculatorTable.NAME, CalculatorTable.COLUMN_ID, id);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Calculator calculator = new Calculator();
            calculator.setId(cursor.getLong(cursor.getColumnIndex(CalculatorTable.COLUMN_ID)));
            calculator.setName(cursor.getString(cursor.getColumnIndex(CalculatorTable.COLUMN_NAME)));
            calculator.setImageName(cursor.getString(cursor.getColumnIndex(CalculatorTable.COLUMN_UNIQUE_ID)));
            calculator.setIsVisible(cursor.getInt(cursor.getColumnIndex(CalculatorTable.COLUMN_IS_VISIBLE)));
            calculator.setOrderBy(cursor.getInt(cursor.getColumnIndex(CalculatorTable.COLUMN_ORDER)));
            list.add(calculator);
            cursor.moveToNext();
        }
        return list;
    }

    @Override
    public boolean update(Object row) {
        Calculator calculator = (Calculator)row;
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalculatorTable.COLUMN_NAME, calculator.getName());
        contentValues.put(CalculatorTable.COLUMN_UNIQUE_ID, calculator.getImageName());
        contentValues.put(CalculatorTable.COLUMN_IS_VISIBLE, calculator.getIsVisible());
        contentValues.put(CalculatorTable.COLUMN_ORDER, calculator.getOrderBy());
        getWritableDB().update(CalculatorTable.NAME, contentValues, "ID = ? ", new String[] { Long.toString(calculator.getId()) } );
        return true;
    }
}
