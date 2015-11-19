package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.FootPrintProduct;
import com.geek.geekmall.profile.activity.FootPrintActivity;
import com.geek.geekmall.utils.ImageLoader;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class FootPrintAdapter extends BaseAdapter {
    private Context mContext;
    private List<FootPrintProduct> mProducts;
    private PullToRefreshGridView mGridView;

    public FootPrintAdapter(Context context, PullToRefreshGridView gridView) {
        mContext = context;
        mGridView = gridView;
    }

    public void setmProducts(List<FootPrintProduct> mProducts) {
        this.mProducts = mProducts;
    }

    public List<FootPrintProduct> getmProducts() {
        return mProducts;
    }

    @Override
    public int getCount() {
        if (mProducts == null) {
            return 0;
        }
        return mProducts.size();
    }

    @Override
    public FootPrintProduct getItem(int i) {
        if (mProducts == null) {
            return null;
        }
        return mProducts.get(i);
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
            convertView = mInflater.inflate(R.layout.favor_product_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView
                    .findViewById(R.id.price);
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);
            convertView.setTag(viewHolder);
            viewHolder.tag = (ImageView) convertView.findViewById(R.id.select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mProducts.get(position).getDisplayName());
        viewHolder.price.setText("￥" + mProducts.get(position).getMemberPrice());
        String url = mProducts.get(position).getImgUrl();
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http://")){
                url = URLs.IMAGE_URL+url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .placeholder(R.drawable.product_list_default)
                    .into(viewHolder.cover);

//            ImageLoader.getInstance(mContext).getBitmapUtils()
//                    .configDefaultLoadingImage(R.drawable.product_list_default)
//                    .configDefaultLoadFailedImage(R.drawable.product_list_default)
//                    .display(viewHolder.cover, url);

//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.product_list_default)
//                    .showImageOnFail(R.drawable.product_list_default)
//                    .cacheInMemory(true)
//                    .cacheOnDisk(true)
//                    .build();
//            ImageLoader.getInstance(mContext).getUniversalImageLoader().displayImage(url, viewHolder.cover, options);
        }

        if (mGridView.getRefreshableView().getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
            viewHolder.tag.setVisibility(View.VISIBLE);
            if (mGridView.getRefreshableView().isItemChecked(position)) {
                viewHolder.tag.setBackgroundResource(R.drawable.select_product);
            } else {
                viewHolder.tag.setBackgroundDrawable(null);
            }
        } else {
            viewHolder.tag.setVisibility(View.GONE);
        }
        ((FootPrintActivity) mContext).updateButton();
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView cover;
        ImageView tag;
        TextView price;
    }
}
