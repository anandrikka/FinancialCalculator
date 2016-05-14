package com.wordpress.techanand.financialcalculator.db.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wordpress.techanand.financialcalculator.db.AppOpenHelper;

import java.util.ArrayList;

/**
 * Created by Anand Rikka on 5/14/2016.
 */
public abstract class GenericDataSource implements GenericDataSourceInterface{

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;

    public GenericDataSource(Context context) {
        this.sqLiteOpenHelper = new AppOpenHelper(context);
        this.writableDB = sqLiteOpenHelper.getWritableDatabase();
        this.readableDB = sqLiteOpenHelper.getReadableDatabase();
    }

    public SQLiteOpenHelper getSqLiteOpenHelper() {
        return sqLiteOpenHelper;
    }

    public SQLiteDatabase getWritableDB() {
        return writableDB;
    }

    public SQLiteDatabase getReadableDB() {
        return readableDB;
    }

    @Override
    public int numberOfRows(String table) {
        int numRows = (int) DatabaseUtils.queryNumEntries(readableDB, table);
        return numRows;
    }

    @Override
    public int deleteRow(String table, String idColName, long id) {
        return writableDB.delete(table, idColName+"= ? ", new String[] { Long.toString(id) });
    }

    @Override
    public Cursor getAllRowsCursor(String table) {
        return readableDB.rawQuery("select * from "+table, null);
    }

    @Override
    public Cursor getRowsByIdCursor(String table, String idColName, long id) {
        return readableDB.rawQuery("select * from "+table+" where "+idColName+"="+id, null);
    }

}
