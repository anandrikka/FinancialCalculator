package com.wordpress.techanand.financialcalculator.db.datasources;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Anand Rikka on 5/14/2016.
 */

public interface GenericDataSourceInterface {

    public boolean insert(Object row);
    public ArrayList getAllRows();
    public Cursor getAllRowsCursor(String table);
    public int numberOfRows(String table);
    public int deleteRow(String table, String idColName, long id);
    public ArrayList getRowsById(long id);
    public Cursor getRowsByIdCursor(String table, String idColName, long id);
    public boolean update(Object row);

}
