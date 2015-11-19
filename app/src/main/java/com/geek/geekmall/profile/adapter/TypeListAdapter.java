package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.ReFundType;

import java.util.List;

/**
 * Created by apple on 3/19/15.
 */
public class TypeListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReFundType> mReFundTypes;

    public TypeListAdapter(Context context) {
        mContext = context;
    }

    public void setReFundType(List<ReFundType> mReFundTypes) {
        this.mReFundTypes = mReFundTypes;
    }

    public List<ReFundType> getReFundType() {
        return mReFundTypes;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if (mReFundTypes != null) {
            return mReFundTypes.size();
        }
        return 0;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public ReFundType getItem(int position) {
        if (mReFundTypes != null && !mReFundTypes.isEmpty()) {
            return mReFundTypes.get(position);
        }
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.name.setTextSize(15);
            viewHolder.name.setTextColor(mContext.getResources().getColor(R.color.content_black));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mReFundTypes.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }
}
