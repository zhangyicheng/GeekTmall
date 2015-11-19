package com.geek.geekmall.product.adapter;

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
import com.geek.geekmall.bean.Comment;
import com.geek.geekmall.bean.Comment;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.TimeUtils;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> mCommets;
    public CommentAdapter(Context context) {
        mContext = context;
    }

    public void setmCommets(List<Comment> mCommets) {
        this.mCommets = mCommets;
    }

    public List<Comment> getmCommets() {
        return mCommets;
    }

    @Override
    public int getCount() {
        if (mCommets == null) {
            return 0;
        }
        return mCommets.size();
    }

    @Override
    public Comment getItem(int i) {
        if (mCommets == null) {
            return null;
        }
        return mCommets.get(i);
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
            convertView = mInflater.inflate(R.layout.comment_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.other = (TextView) convertView.findViewById(R.id.other);
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.avator);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mCommets.get(position).getNickname());
        viewHolder.content.setText(mCommets.get(position).getContent());
        viewHolder.time.setText(TimeUtils.getTime(mCommets.get(position).getCreateTime()));
        viewHolder.other.setText(mCommets.get(position).getGoodsSpec());

        if (!TextUtils.isEmpty(mCommets.get(position).getImgUrl())){
            String url = mCommets.get(position).getImgUrl();
            if (!url.startsWith("http://")){
                url = URLs.IMAGE_URL+mCommets.get(position).getImgUrl();
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url).resize(DensityUtils.dp2px(mContext,50),DensityUtils.dp2px(mContext,50))
                    .transform(ImageLoader.getInstance(mContext).new RoundedCornersTransformation(120))
                    .into(viewHolder.cover);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView time;
        TextView other;
        ImageView cover;
        TextView content;

    }
}
