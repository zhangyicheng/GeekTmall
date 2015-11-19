package com.geek.geekmall.category.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.views.RecyclerImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.MemoryPolicy;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class SearchProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProducts;

    public SearchProductAdapter(Context context) {
        mContext = context;
    }

    public void setmProducts(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    public List<Product> getmProducts() {
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
    public Product getItem(int i) {
        if (mProducts == null || mProducts.size() == 0) {
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
            convertView = mInflater.inflate(R.layout.search_product_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView
                    .findViewById(R.id.price);
            viewHolder.like = (TextView) convertView
                    .findViewById(R.id.like_num);
            viewHolder.oldPrice = (TextView) convertView
                    .findViewById(R.id.oldPrice);
            viewHolder.isFisrt = (TextView) convertView.findViewById(R.id.isFirst);
            viewHolder.cover = (RecyclerImageView) convertView.findViewById(R.id.cover);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mProducts.get(position).getIsFirstFree()==1){
            viewHolder.isFisrt.setVisibility(View.VISIBLE);
        } else {
            viewHolder.isFisrt.setVisibility(View.GONE);
        }
        viewHolder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.oldPrice.setText("￥" + mProducts.get(position).getMarketPrice());
        viewHolder.name.setText(mProducts.get(position).getDisplayName());
        viewHolder.price.setText("￥" + mProducts.get(position).getMemberPrice());
        viewHolder.like.setText(mProducts.get(position).getFavourCount() + "");
        if (!TextUtils.isEmpty(mProducts.get(position).getImgUrl())) {
            String url = mProducts.get(position).getImgUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .resize(DensityUtils.dp2px(mContext, 160), DensityUtils.dp2px(mContext, 160))
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
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
        if (mProducts.get(position).getIsFavour() == 1) {
            viewHolder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon_pressed, 0, 0, 0);
        } else {
            viewHolder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_gray, 0, 0, 0);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        RecyclerImageView cover;
        TextView price;
        TextView like;
        TextView isFisrt;
        TextView oldPrice;
    }
}
