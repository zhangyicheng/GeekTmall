package com.geek.geekmall.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Category;
import com.geek.geekmall.bean.Category;
import com.geek.geekmall.utils.ImageLoader;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class SecCategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<Category> mCategorys;

    public SecCategoryAdapter(Context context,List<Category> categories) {
        mContext = context;
        mCategorys = categories;
    }

    public void setmCategorys(List<Category> mCategorys) {
        this.mCategorys = mCategorys;
    }

    public List<Category> getmCategorys() {
        return mCategorys;
    }

    @Override
    public int getCount() {
        if (mCategorys == null) {
            return 0;
        }
        return mCategorys.size();
    }

    @Override
    public Object getItem(int i) {
        if (mCategorys == null) {
            return null;
        }
        return mCategorys.get(i);
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
            convertView = mInflater.inflate(R.layout.category_words_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mCategorys.get(position).getDisplayName());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
    }
}
