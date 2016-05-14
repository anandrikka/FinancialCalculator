package com.wordpress.techanand.financialcalculator.ui.listview.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.AppConstants;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_main_list_1, null);
            TextView textView = (TextView) convertView.findViewById(R.id.gridText);
            textView.setText(listViewItem.getName());
            ImageView image = (ImageView) convertView.findViewById(R.id.gridImage);
            String packageName = getContext().getPackageName();
            Resources resources = getContext().getResources();
            int resId =resources.getIdentifier(listViewItem.getUniqueId(), "drawable", packageName);
            image.setImageResource(resId);
            viewHolder = new TextViewHolder(textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TextViewHolder) convertView.getTag();
        }
        viewHolder.getText().setText(listViewItem.getName());
        return convertView;
    }

}
