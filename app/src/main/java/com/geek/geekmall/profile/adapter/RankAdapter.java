package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class RankAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> mUsers;

    public RankAdapter(Context context) {
        mContext = context;
    }

    public void setmUsers(List<User> mUsers) {
        this.mUsers = mUsers;
    }

    public List<User> getmUsers() {
        return mUsers;
    }

    @Override
    public int getCount() {
        if (mUsers == null) {
            return 0;
        }
        return mUsers.size();
    }

    @Override
    public Object getItem(int i) {
        if (mUsers == null) {
            return null;
        }
        return mUsers.get(i);
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
            convertView = mInflater.inflate(R.layout.item_sales_rank, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.income = (TextView) convertView
                    .findViewById(R.id.income);
            viewHolder.rank = (TextView) convertView
                    .findViewById(R.id.rank);
            viewHolder.sales = (TextView) convertView
                    .findViewById(R.id.sales);
            viewHolder.avator = (ImageView) convertView.findViewById(R.id.avator);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.rank.setText(position+1+"");
        viewHolder.income.setText(mUsers.get(position).getCutPrice()+"元");
        viewHolder.sales.setText(mUsers.get(position).getTotalPrice()+"元");
        String url = mUsers.get(position).getUserPhoto();

        if (!TextUtils.isEmpty(url)){
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .resize(DensityUtils.dp2px(mContext,40),DensityUtils.dp2px(mContext,40))
                    .transform(ImageLoader.getInstance(mContext).new RoundedCornersTransformation(120))
                    .into(viewHolder.avator);
        } else {
            viewHolder.avator.setImageResource(R.drawable.avatar_default);
        }
        viewHolder.name.setText(mUsers.get(position).getNickname());
//        viewHolder.price.setText("200￥");

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        ImageView avator;
        TextView rank;
        TextView income;
        TextView sales;

    }
}
