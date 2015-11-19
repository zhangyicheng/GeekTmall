package com.geek.geekmall.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.LogisticsData;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class LogisticsAdapter extends BaseAdapter {
    private Context mContext;
    private List<LogisticsData> logisticsDatas;

    public LogisticsAdapter(Context context) {
        mContext = context;
    }

    public List<LogisticsData> getLogisticsDatas() {
        return logisticsDatas;
    }

    public void setLogisticsDatas(List<LogisticsData> logisticsDatas) {
        this.logisticsDatas = logisticsDatas;
    }

    @Override
    public int getCount() {
        if (logisticsDatas == null) {
            return 0;
        }
        return logisticsDatas.size();
    }

    @Override
    public LogisticsData getItem(int i) {
        if (logisticsDatas == null || logisticsDatas.isEmpty()) {
            return null;
        }
        return logisticsDatas.get(i);
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
            convertView = mInflater.inflate(R.layout.logistics_item, null);
            viewHolder.context = (TextView) convertView.findViewById(R.id.context);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.context.setText(logisticsDatas.get(position).getContext());
        viewHolder.time.setText(logisticsDatas.get(position).getFtime());

        if (position == 0){
            viewHolder.image.setBackgroundResource(R.drawable.logistics_current_icon);
            viewHolder.context.setTextColor(mContext.getResources().getColor(R.color.pink));
            viewHolder.time.setTextColor(mContext.getResources().getColor(R.color.pink));
        } else {
            viewHolder.image.setBackgroundResource(R.drawable.logistics_icon);
            viewHolder.context.setTextColor(mContext.getResources().getColor(R.color.content_black));
            viewHolder.time.setTextColor(mContext.getResources().getColor(R.color.content_black));
        }

        return convertView;
    }



    private static class ViewHolder {
        private TextView context;
        private TextView time;
        private ImageView image;
    }
}
