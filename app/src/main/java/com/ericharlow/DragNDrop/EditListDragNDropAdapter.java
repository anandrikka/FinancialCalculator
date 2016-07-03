package com.ericharlow.DragNDrop;

/**
 * Created by Anand Rikka on 6/25/2016.
 */
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wordpress.techanand.financialcalculator.db.model.CalculatorListContent;

public final class EditListDragNDropAdapter extends BaseAdapter implements RemoveListener, DropListener{

    private int[] mIds;
    private int[] mLayouts;
    private LayoutInflater mInflater;
    private ArrayList<CalculatorListContent.CalculatorItem> mContent;

    public EditListDragNDropAdapter(Context context, ArrayList<CalculatorListContent.CalculatorItem> content) {
        init(context,new int[]{android.R.layout.simple_list_item_1},new int[]{android.R.id.text1}, content);
    }

    public EditListDragNDropAdapter(Context context, int[] itemLayouts, int[] itemIDs, ArrayList<CalculatorListContent.CalculatorItem> content) {
        init(context,itemLayouts,itemIDs, content);
    }

    private void init(Context context, int[] layouts, int[] ids, ArrayList<CalculatorListContent.CalculatorItem> content) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);
        mIds = ids;
        mLayouts = layouts;
        mContent = content;
    }

    /**
     * The number of items in the list
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        return mContent.size();
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficient to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public CalculatorListContent.CalculatorItem getItem(int position) {
        return mContent.get(position);
    }

    /**
     * Use the array index as a unique id.
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, View,
     *      ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(mLayouts[0], null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(mIds[0]);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.text.setText(((CalculatorListContent.CalculatorItem)mContent.get(position)).getName());

        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }

    public void onRemove(int which) {
        if (which < 0 || which > mContent.size()) return;
        mContent.remove(which);
    }

    public void onDrop(int from, int to) {
        CalculatorListContent.CalculatorItem temp = mContent.get(from);
        mContent.remove(from);
        mContent.add(to,temp);
    }
}
