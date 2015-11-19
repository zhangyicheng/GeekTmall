package com.geek.geekmall.category.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.Brand;
import com.geek.geekmall.utils.ImageLoader;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class HotBrandAdapter extends BaseAdapter {
    private final LayoutInflater mInflate;
    private List<Brand> mBrands;
    private Context mContext;

    public HotBrandAdapter(Context context, List<Brand> brands) {
        mBrands = brands;
        mContext = context;
        mInflate = LayoutInflater.from(mContext);

    }

    public int getCount() {
        return mBrands.size();
    }

    public Object getItem(int position) {
        return mBrands.get(position);
    }

    public long getItemId(int position) {
        return mBrands.get(position).getId().hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflate.inflate(R.layout.hot_brand_list_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.brand_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Brand brand = mBrands.get(position);
        if (!TextUtils.isEmpty(brand.getImgUrl())) {
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(URLs.IMAGE_URL+brand.getImgUrl()).into(holder.icon);
        }
        return convertView;
    }


    static class ViewHolder {
        ImageView icon;
    }
}
