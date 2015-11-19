package com.geek.geekmall.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.Theme;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.ScreenUtils;
import com.geek.geekmall.views.CountAdjustView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.MemoryPolicy;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class StoreNewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Theme> mThemes;

    public StoreNewAdapter(Context context) {
        mContext = context;
    }

    public void setThemes(List<Theme> mThemes) {
        this.mThemes = mThemes;
    }

    public List<Theme> getThemes() {
        return mThemes;
    }

    @Override
    public int getCount() {
        if (mThemes == null) {
            return 0;
        }
        return mThemes.size();
    }

    @Override
    public Object getItem(int i) {
        if (mThemes == null) {
            return null;
        }
        return mThemes.get(i);
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
            convertView = mInflater.inflate(R.layout.store_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.saleOff = (TextView) convertView
                    .findViewById(R.id.sale_off);
            viewHolder.time = (TextView) convertView
                    .findViewById(R.id.remain_time);
            viewHolder.likes = (TextView) convertView
                    .findViewById(R.id.likes);

            viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mThemes.get(position).getTitle());
//        viewHolder.price.setText("200￥");
//        viewHolder.oldPrice.setText("300￥");
        if (!TextUtils.isEmpty(mThemes.get(position).getImgUrl())){
            String url = mThemes.get(position).getImgUrl();
            if (!url.startsWith("http://")){
                url = URLs.IMAGE_URL+mThemes.get(position).getImgUrl();
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .fit()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.home_banner_default).into(viewHolder.cover);
//            ImageLoader.getInstance(mContext).getBitmapUtils()
//                    .configDefaultLoadingImage(R.drawable.home_banner_default)
//                    .configDefaultLoadFailedImage(R.drawable.home_banner_default)
//                    .display(viewHolder.cover, url);


//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.home_banner_default)
//                    .showImageOnFail(R.drawable.home_banner_default)
//                    .cacheInMemory(false)
//                    .cacheOnDisk(true)
//                    .build();
//            ImageLoader.getInstance(mContext).getUniversalImageLoader().displayImage(url, viewHolder.cover, options);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView cover;
        TextView saleOff;

        TextView time;
        TextView likes;
    }
}
