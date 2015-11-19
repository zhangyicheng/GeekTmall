package com.geek.geekmall.category.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.Category;
import com.geek.geekmall.bean.Category;
import com.geek.geekmall.category.activity.SearchGoodsResultActivity;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.views.GridViewForScrollView;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<Category> mCategorys;

    public CategoryAdapter(Context context,List<Category> categories) {
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
            convertView = mInflater.inflate(R.layout.search_category_item, null);

            viewHolder.gridView = (GridViewForScrollView) convertView
                    .findViewById(R.id.grid_view);
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mCategorys.get(position).getDisplayName());
        viewHolder.gridView.setAdapter(new SecCategoryAdapter(mContext,mCategorys.get(position).getChildren()));
        if (!TextUtils.isEmpty(mCategorys.get(position).getImgUrl())){
            String url = mCategorys.get(position).getImgUrl();
            if (!url.startsWith("http")){
                url = URLs.IMAGE_URL+url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url).into(viewHolder.cover);
        }
        final int parentposition= position;
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startResult(mCategorys.get(parentposition).getChildren().get(position).getId(),
                        mCategorys.get(parentposition).getChildren().get(position).getDisplayName());
            }
        });
        return convertView;
    }
    private void startResult(String keyword,String name) {
        Intent intent = new Intent(mContext, SearchGoodsResultActivity.class);
        intent.putExtra("categoryId", keyword);
        intent.putExtra("title", name);

        mContext.startActivity(intent);
    }
    private static class ViewHolder {
        GridViewForScrollView gridView;
        ImageView cover;
        TextView name;
    }
}
