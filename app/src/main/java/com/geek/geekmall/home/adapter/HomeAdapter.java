package com.geek.geekmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.views.CountAdjustView;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProducts;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    public void setmProducts(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    public List<Product> getmProducts() {
        return mProducts;
    }

    @Override
    public int getCount() {
        if (mProducts == null) {
            return 0;
        }
        return mProducts.size();
    }

    @Override
    public Object getItem(int i) {
        if (mProducts == null) {
            return null;
        }
        return mProducts.get(i);
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
            convertView = mInflater.inflate(R.layout.dayrecommend_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView
                    .findViewById(R.id.price);
            viewHolder.oldPrice = (TextView) convertView
                    .findViewById(R.id.oldPrice);
            viewHolder.desc = (TextView) convertView
                    .findViewById(R.id.desc);
            viewHolder.number = (TextView) convertView
                    .findViewById(R.id.number);

            viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.name.setText("衣服");
//        viewHolder.price.setText("200￥");
//        viewHolder.oldPrice.setText("300￥");

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView cover;
        TextView price;

        TextView desc;
        TextView number;
        TextView oldPrice;
        CountAdjustView adjustView;
    }
}
