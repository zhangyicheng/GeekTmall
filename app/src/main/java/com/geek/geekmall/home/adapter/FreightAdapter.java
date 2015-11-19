package com.geek.geekmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.model.DistributionWay;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class FreightAdapter extends BaseAdapter {
    private Context mContext;
    private List<DistributionWay> mFreightWay;
    private ListView mListView;

    public FreightAdapter(Context context, ListView listView) {
        mContext = context;
        mListView = listView;
    }

    public void setmFreightWay(List<DistributionWay> mFreightWay) {
        this.mFreightWay = mFreightWay;
    }

    public List<DistributionWay> getmFreightWay() {
        return mFreightWay;
    }

    @Override
    public int getCount() {
        if (mFreightWay == null) {
            return 0;
        }
        return mFreightWay.size();
    }

    @Override
    public DistributionWay getItem(int i) {
        if (mFreightWay == null) {
            return null;
        }
        return mFreightWay.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.freight_item, null);

            viewHolder.price = (TextView) convertView
                    .findViewById(R.id.freight_price);
            viewHolder.select = (ImageView) convertView.findViewById(R.id.select);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.name.setText(mFreightWay.get(postion).getDisplayName());
        if (mFreightWay.get(postion).getType() == 1) {
            viewHolder.price.setText("快递 " + mFreightWay.get(postion).getPostage() + "元");
        } else if (mFreightWay.get(postion).getType() == 2) {
            viewHolder.price.setText("EMS " + mFreightWay.get(postion).getPostage() + "元");
        }
        if (mListView.isItemChecked(postion)) {
            viewHolder.select.setBackgroundResource(R.drawable.select_product);
        } else {
            viewHolder.select.setBackgroundResource(R.drawable.unselect_product);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView select;

        TextView price;
    }
}
