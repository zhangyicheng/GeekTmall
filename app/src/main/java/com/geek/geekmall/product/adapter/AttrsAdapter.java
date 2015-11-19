package com.geek.geekmall.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.GoodsAttr;
import com.geek.geekmall.bean.GoodsAttr;
import com.geek.geekmall.views.GridViewForScrollView;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class AttrsAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodsAttr> mAttrs;
    public AttrsAdapter(Context context) {
        mContext = context;
    }

    public void setmAttrs(List<GoodsAttr> mAttrs) {
        this.mAttrs = mAttrs;
    }

    public List<GoodsAttr> getmAttrs() {
        return mAttrs;
    }

    @Override
    public int getCount() {
        if (mAttrs == null) {
            return 0;
        }
        return mAttrs.size();
    }

    @Override
    public GoodsAttr getItem(int i) {
        if (mAttrs == null) {
            return null;
        }
        return mAttrs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.attr_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.value = (TextView) convertView.findViewById(R.id.value);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mAttrs.get(position).getAttributeName()+"：");
        viewHolder.value.setText(mAttrs.get(position).getAttributeValue());

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView value;

    }
}
