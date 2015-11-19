package com.geek.geekmall.product.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.GoodsSpec;
import com.geek.geekmall.bean.GoodsSpec;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.views.GridViewForScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class SpecAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodsSpec> mSpecs;
    private GridViewForScrollView mGridView;
    public SpecAdapter(Context context, GridViewForScrollView gridView) {
        mContext = context;
        mGridView = gridView;
    }

    public void setmSpecs(List<GoodsSpec> mSpecs) {
        this.mSpecs = mSpecs;
    }

    public List<GoodsSpec> getmSpecs() {
        return mSpecs;
    }

    @Override
    public int getCount() {
        if (mSpecs == null) {
            return 0;
        }
        return mSpecs.size();
    }

    @Override
    public GoodsSpec getItem(int i) {
        if (mSpecs == null) {
            return null;
        }
        return mSpecs.get(i);
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
            convertView = mInflater.inflate(R.layout.size_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mSpecs.get(position).getDisplayName());
        if (mGridView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
            if (mGridView.isItemChecked(position)){
                viewHolder.name.setBackgroundResource(R.drawable.btn_pink_normal);
                viewHolder.name.setTextColor(Color.WHITE);
            } else {
                viewHolder.name.setTextColor(mContext.getResources().getColor(R.color.pink));
                viewHolder.name.setBackgroundResource(R.drawable.btn_spec_normal);
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
    }
}
