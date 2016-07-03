package com.wordpress.techanand.financialcalculator.app.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ericharlow.DragNDrop.DragListener;
import com.ericharlow.DragNDrop.DragNDropListView;
import com.ericharlow.DragNDrop.DropListener;
import com.ericharlow.DragNDrop.EditListDragNDropAdapter;
import com.ericharlow.DragNDrop.RemoveListener;
import com.google.gson.Gson;
import com.wordpress.techanand.financialcalculator.R;
import com.wordpress.techanand.financialcalculator.db.model.CalculatorListContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;


public class EditActivity extends AppCompatActivity {

    DragNDropListView listView;
    ArrayList<CalculatorListContent.CalculatorItem> content;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Gson gson = new Gson();
        final List<CalculatorListContent.CalculatorItem> calculators = CalculatorListContent.getCalculatorsList(getResources());
        String defaultCalcOrder = gson.toJson(calculators);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String calcListOrder = sharedPreferences.getString("main_list", defaultCalcOrder);
        CalculatorListContent.CalculatorItem[] calcArray = gson.fromJson(calcListOrder, CalculatorListContent.CalculatorItem[].class);
        List<CalculatorListContent.CalculatorItem> list = Arrays.asList(calcArray);

        content =  new ArrayList<>(list);

        listView = (DragNDropListView) findViewById(R.id.edit_list);

        listView.setAdapter(new EditListDragNDropAdapter(this, new int[]{R.layout.dragitem}, new int[]{R.id.TextView01}, content));//new DragNDropAdapter(this,content)

        if (listView instanceof DragNDropListView) {
            ((DragNDropListView) listView).setDropListener(mDropListener);
            ((DragNDropListView) listView).setRemoveListener(mRemoveListener);
            ((DragNDropListView) listView).setDragListener(mDragListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString("main_list", gson.toJson(content)).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private DropListener mDropListener =
            new DropListener() {
                public void onDrop(int from, int to) {
                    Adapter adapter = listView.getAdapter();
                    if (adapter instanceof EditListDragNDropAdapter) {
                        ((EditListDragNDropAdapter)adapter).onDrop(from, to);
                        listView.invalidateViews();
                    }
                }
            };

    private RemoveListener mRemoveListener =
            new RemoveListener() {
                public void onRemove(int which) {
                    Adapter adapter = listView.getAdapter();
                    if (adapter instanceof EditListDragNDropAdapter) {
                        ((EditListDragNDropAdapter)adapter).onRemove(which);
                        listView.invalidateViews();
                    }
                }
            };

    private DragListener mDragListener =
            new DragListener() {

                int backgroundColor = 0xe0103010;
                int defaultBackgroundColor;

                public void onDrag(int x, int y, ListView listView) {
                    // TODO Auto-generated method stub
                }

                public void onStartDrag(View itemView) {
                    itemView.setVisibility(View.INVISIBLE);
                    defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
                    itemView.setBackgroundColor(backgroundColor);
                    ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
                    if (iv != null) iv.setVisibility(View.INVISIBLE);
                }

                public void onStopDrag(View itemView) {
                    itemView.setVisibility(View.VISIBLE);
                    itemView.setBackgroundColor(defaultBackgroundColor);
                    ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
                    if (iv != null) iv.setVisibility(View.VISIBLE);
                }

            };
}
