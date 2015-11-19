package com.geek.geekmall.category.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Category;
import com.geek.geekmall.bean.Keyword;
import com.geek.geekmall.utils.MyLog;

import java.util.List;

/**
 * 搜索记录适配器
 * Created by apple on 4/28/15.
 */
public class StringAdapter extends BaseAdapter {
    private Context mContext;
    private List<Keyword> mKeywords;

    public StringAdapter(Context context) {
        mContext = context;
    }

    public void setmKeyword(List<Keyword> mKeywords) {
        this.mKeywords = mKeywords;
    }

    public List<Keyword> getmKeyword() {
        return mKeywords;
    }

    @Override
    public int getCount() {
        if (mKeywords == null ||mKeywords.isEmpty()) {
            return 0;
        }
        return mKeywords.size();
    }

    @Override
    public Keyword getItem(int i) {
        if (mKeywords == null || mKeywords.isEmpty()) {
            return null;
        }
        return mKeywords.get(i);
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
            convertView = mInflater.inflate(R.layout.keyword_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mKeywords.get(postion) != null){
            viewHolder.name.setText(mKeywords.get(postion).getWords());
        }
        MyLog.debug(StringAdapter.class,postion+"-----");
        return convertView;
    }

    private static class ViewHolder {
        TextView name;

    }
}
