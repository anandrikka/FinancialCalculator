package com.wordpress.techanand.financialcalculator.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.app.activities.StockPriceActivity;
import com.wordpress.techanand.financialcalculator.app.models.StockCategory;

/**
 * A simple {@link Fragment} subclass.
 */
public class StocksBySharePrice extends Fragment {


    public StocksBySharePrice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stocks_by_share_price, container, false);
        final Spinner categorySpinner = (Spinner) view.findViewById(R.id.stock_category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_spinner_dropdown, StockPriceActivity.CATEGORIES);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), StockPriceActivity.CATEGORIES[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categorySpinner.setSelection(0);
            }
        });

        return view;
    }

}
