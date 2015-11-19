package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Area;

import java.util.List;

public class AreaAdapter extends BaseAdapter {
    private Context mContext;
    private List<Area> mAreas;

    public AreaAdapter(Context context) {
        mContext = context;
    }
    public AreaAdapter(Context context,List<Area> area) {
        mContext = context;
        mAreas = area;
    }

    public List<Area> getmAreas() {
        return mAreas;
    }

    public void setmAreas(List<Area> mAreas) {
        this.mAreas = mAreas;
    }

    @Override
    public int getCount() {
        if (mAreas != null) {
            return mAreas.size();
        }
        return 0;
    }

    @Override
    public Area getItem(int position) {
        if (mAreas != null && !mAreas.isEmpty()) {
            return mAreas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_dialog, null);
            viewHolder.name = (TextView) convertView
                    .findViewById(R.id.name);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mAreas.get(position).getDisplayName());
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
