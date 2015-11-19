package com.geek.geekmall.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.LogisticsCompany;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class LogisticsNameAdapter extends BaseAdapter {
    private Context mContext;
    private List<LogisticsCompany> mCompanyNames;

    public LogisticsNameAdapter(Context context) {
        mContext = context;
    }

    public List<LogisticsCompany> getmCompanyNames() {
        return mCompanyNames;
    }

    public void setmCompanyNames(List<LogisticsCompany> mCompanyNames) {
        this.mCompanyNames = mCompanyNames;
    }

    @Override
    public int getCount() {
        if (mCompanyNames == null) {
            return 0;
        }
        return mCompanyNames.size();
    }

    @Override
    public LogisticsCompany getItem(int i) {
        if (mCompanyNames == null || mCompanyNames.isEmpty()) {
            return null;
        }
        return mCompanyNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
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
        viewHolder.name.setText(mCompanyNames.get(position).getName());
        return convertView;

    }



    private static class ViewHolder {
        private TextView name;
    }
}
