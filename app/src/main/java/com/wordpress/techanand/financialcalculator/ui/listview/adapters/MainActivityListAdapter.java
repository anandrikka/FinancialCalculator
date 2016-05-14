package com.wordpress.techanand.financialcalculator.ui.listview.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.models.MainCalcListItem;
import com.wordpress.techanand.financialcalculator.db.model.Calculator;
import com.wordpress.techanand.financialcalculator.ui.listview.holders.TextViewHolder;

import java.util.List;
import java.util.Random;

/**
 * Created by Anand Rikka on 5/12/2016.
 */

public class MainActivityListAdapter extends ArrayAdapter {

    private List<Calculator> objects;

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    public MainActivityListAdapter(Context context, int resource, List<Calculator> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextViewHolder viewHolder = null;
        Calculator listViewItem = objects.get(position);
        if (convertView == null) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_main_list_1, null);
            TextView textView = (TextView) convertView.findViewById(R.id.gridText);
            //TextView bigLetter = (TextView) convertView.findViewById(R.id.content_main_list_circle);
            viewHolder = new TextViewHolder(textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TextViewHolder) convertView.getTag();
        }
        viewHolder.getText().setText(listViewItem.getName());

        return convertView;
    }

}
