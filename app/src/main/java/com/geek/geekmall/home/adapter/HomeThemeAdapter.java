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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.MemoryPolicy;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class HomeThemeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Theme> mThemes;

    public HomeThemeAdapter(Context context) {
        mContext = context;
    }

    public List<Theme> getmThemes() {
        return mThemes;
    }

    public void setmThemes(List<Theme> mThemes) {
        this.mThemes = mThemes;
    }

    @Override
    public int getCount() {
        if (mThemes == null) {
            return 0;
        }
        return mThemes.size();
    }

    @Override
    public Theme getItem(int i) {
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
    public View getView(int postion, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.home_theme_item, null);

            viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(mThemes.get(postion).getImgUrl())){
            String url = mThemes.get(postion).getImgUrl();
            if (!url.startsWith("http://")){
                url = URLs.IMAGE_URL+mThemes.get(postion).getImgUrl();
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .resize(ScreenUtils.getScreenWidth(mContext), DensityUtils.dp2px(mContext, 155))
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.product_home_default)
                    .into(viewHolder.cover);
//            ImageLoader.getInstance(mContext).getBitmapUtils()
//                    .configDefaultLoadingImage(R.drawable.product_home_default)
//                    .configDefaultLoadFailedImage(R.drawable.product_home_default)
//                    .display(viewHolder.cover, url);

//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.product_home_default)
//                    .showImageOnFail(R.drawable.product_home_default)
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
