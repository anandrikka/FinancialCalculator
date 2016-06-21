package com.wordpress.techanand.financialcalculator.app.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ericharlow.DragNDrop.DragListener;
import com.ericharlow.DragNDrop.DragNDropAdapter;
import com.ericharlow.DragNDrop.DragNDropListView;
import com.ericharlow.DragNDrop.DropListener;
import com.ericharlow.DragNDrop.RemoveListener;
import com.wordpress.techanand.financialcalculator.R;

import java.util.ArrayList;

import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;


public class EditActivity extends AppCompatActivity {

    DragNDropListView listView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> content = new ArrayList<String>(mListContent.length);
        for (int i=0; i < mListContent.length; i++) {
            content.add(mListContent[i]);
        }

        listView = (DragNDropListView) findViewById(R.id.edit_list);

        listView.setAdapter(new DragNDropAdapter(this, new int[]{R.layout.dragitem}, new int[]{R.id.TextView01}, content));//new DragNDropAdapter(this,content)

        if (listView instanceof DragNDropListView) {
            ((DragNDropListView) listView).setDropListener(mDropListener);
            ((DragNDropListView) listView).setRemoveListener(mRemoveListener);
            ((DragNDropListView) listView).setDragListener(mDragListener);
        }
    }

    private DropListener mDropListener =
            new DropListener() {
                public void onDrop(int from, int to) {
                    Adapter adapter = listView.getAdapter();
                    if (adapter instanceof DragNDropAdapter) {
                        ((DragNDropAdapter)adapter).onDrop(from, to);
                        listView.invalidateViews();
                    }
                }
            };

    private RemoveListener mRemoveListener =
            new RemoveListener() {
                public void onRemove(int which) {
                    Adapter adapter = listView.getAdapter();
                    if (adapter instanceof DragNDropAdapter) {
                        ((DragNDropAdapter)adapter).onRemove(which);
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

    private static String[] mListContent={"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7"};
}
