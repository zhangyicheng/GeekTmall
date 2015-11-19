package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.Constant;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.MyLog;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by apple on 4/28/15.
 */
public class CustomerAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> mUsers;

    public CustomerAdapter(Context context) {
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
            convertView = mInflater.inflate(R.layout.item_customer_rank, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.number = (TextView) convertView
                    .findViewById(R.id.number);
            viewHolder.rank = (TextView) convertView
                    .findViewById(R.id.rank);
            viewHolder.avator = (ImageView) convertView.findViewById(R.id.avator);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyLog.debug(CustomerAdapter.class, "----------" + position);
        viewHolder.rank.setText(mUsers.get(position).getOrder()+"");
        if (position==0 || position ==1||position ==2){
         viewHolder.rank.setBackgroundResource(R.drawable.btn_pink_normal);
        } else {
            viewHolder.rank.setBackgroundResource(R.drawable.btn_gray_normal);
        }
        viewHolder.number.setText(mUsers.get(position).getCustomerNumber() + "人");
        Pattern pattern = Pattern.compile(Constant.MOBILE_REG);
        if (pattern.matcher(mUsers.get(position).getNickname()).matches()){
            viewHolder.name.setText(mUsers.get(position).getNickname().substring(0,3)+"****"+mUsers.get(position).getNickname().substring(7));
        } else {
            viewHolder.name.setText(mUsers.get(position).getNickname());
        }
        String url = mUsers.get(position).getUserPhoto();
        if (!TextUtils.isEmpty(url)){
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .resize(DensityUtils.dp2px(mContext, 40), DensityUtils.dp2px(mContext, 40))
                    .placeholder(R.drawable.avatar_default)
                    .transform(ImageLoader.getInstance(mContext).new RoundedCornersTransformation(120))
                    .into(viewHolder.avator);

        } else {
            viewHolder.avator.setImageResource(R.drawable.avatar_default);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView avator;
        TextView rank;
        TextView number;

    }
}
