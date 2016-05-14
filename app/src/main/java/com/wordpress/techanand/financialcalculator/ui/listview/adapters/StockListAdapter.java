package com.wordpress.techanand.financialcalculator.ui.listview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.models.StockCategory;

import java.util.List;

/**
 * Created by nandu on 5/14/2016.
 */
public class StockListAdapter extends ArrayAdapter {

    List<StockCategory> list;

    public StockListAdapter(Context context, int resource, List<StockCategory> objects) {
        super(context, resource, objects);
        this.list = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_stock_price_dropdown, null);
        StockCategory stockCategory = (StockCategory) list.get(position);
        TextView label = (TextView) convertView.findViewById(R.id.content_stock_price_category_dropdown);
        label.setText(stockCategory.getName());
        return convertView;
    }
}
