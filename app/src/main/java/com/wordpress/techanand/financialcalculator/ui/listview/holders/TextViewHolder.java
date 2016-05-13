package com.wordpress.techanand.financialcalculator.ui.listview.holders;

import android.widget.TextView;

/**
 * Created by Anand Rikka on 5/12/2016.
 */

public class TextViewHolder {
    private TextView text;

    public TextViewHolder(TextView text) {
        this.text = text;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }
}
