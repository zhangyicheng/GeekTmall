package com.geek.geekmall.category.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Brand;
import com.geek.geekmall.views.cityindex.Indexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class BrandAdapter extends BaseAdapter implements Indexer {
    private final LayoutInflater mInflate;
    private List<Brand> mBrands;
    private Context mContext;
    private HashMap<String, Integer> indexMap = new HashMap<String, Integer>();


    public BrandAdapter(Context context) {
        mContext = context;
        mInflate = LayoutInflater.from(mContext);

    }

    public void setmBrands(List<Brand> mBrands) {
        this.mBrands = mBrands;
        // 列表特征和分组首项进行关联
        if (mBrands != null){
            for (int i = 0; i < mBrands.size(); i++) {
                Brand brand = mBrands.get(i);
                String section = brand.getFirstWord();
                if (!indexMap.containsKey(section)) {
                    indexMap.put(section, i);
                }
            }
        }

    }

    public int getCount() {
        if (mBrands == null || mBrands.size() == 0) {
            return 0;
        }
        return mBrands.size();
    }

    public Object getItem(int position) {
        if (mBrands == null || mBrands.size() == 0) {
            return null;
        }
        return mBrands.get(position);
    }

    public long getItemId(int position) {
        return mBrands.get(position).getId().hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflate.inflate(R.layout.brand_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvSection = (TextView) convertView.findViewById(R.id.tvSection);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Brand brand = mBrands.get(position);
        holder.tvName.setText(brand.getBrandName());
        if (TextUtils.isEmpty(brand.getFirstWord())) {
            return convertView;
        }
        char idFirstChar = brand.getFirstWord().toUpperCase().charAt(0);
        if (position == 0) {
            setIndex(holder.tvSection, String.valueOf(idFirstChar));
        } else {
            if (!TextUtils.isEmpty(mBrands.get(position - 1).getFirstWord().toUpperCase())){
                char preFirstChar = mBrands.get(position - 1).getFirstWord().toUpperCase().charAt(0);
                if (idFirstChar != preFirstChar) { // diff group
                    setIndex(holder.tvSection, String.valueOf(idFirstChar));
                } else { // same group
                    holder.tvSection.setVisibility(View.GONE);
                }
            }

        }
        return convertView;
    }

    private void setIndex(TextView section, String str) {
        section.setVisibility(View.VISIBLE);
        if ("推".equals(str)) section.setText("热门品牌");
        else section.setText(str);
    }

    @Override
    public int getStartPositionOfSection(String section) {
        if (indexMap.containsKey(section))
            return indexMap.get(section);
        return 0;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvSection;
    }

}
