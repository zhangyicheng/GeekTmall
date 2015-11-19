package com.geek.geekmall.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.SecCategory;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class TypeProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<SecCategory> mSecCategorys;

    public TypeProductAdapter(Context context) {
        mContext = context;
    }

    public void setmSecCategorys(List<SecCategory> mSecCategorys) {
        this.mSecCategorys = mSecCategorys;
    }

    public List<SecCategory> getmSecCategorys() {
        return mSecCategorys;
    }

    @Override
    public int getCount() {
        if (mSecCategorys == null) {
            return 0;
        }
        return mSecCategorys.size();
    }

    @Override
    public Object getItem(int i) {
        if (mSecCategorys == null) {
            return null;
        }
        return mSecCategorys.get(i);
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
            convertView = mInflater.inflate(R.layout.type_category_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.image = (ImageView) convertView
                    .findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mSecCategorys.get(postion).getName());


        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView image;

    }
}
