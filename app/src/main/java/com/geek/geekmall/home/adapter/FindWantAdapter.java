package com.geek.geekmall.home.adapter;

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
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.views.CountAdjustView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.MemoryPolicy;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class FindWantAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProducts;

    public FindWantAdapter(Context context) {
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
    public View getView(int postion, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.dayrecommend_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView
                    .findViewById(R.id.price);
            viewHolder.oldPrice = (TextView) convertView
                    .findViewById(R.id.oldPrice);
            viewHolder.desc = (TextView) convertView
                    .findViewById(R.id.desc);
            viewHolder.number = (TextView) convertView
                    .findViewById(R.id.number);

            viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);
            viewHolder.sale = (ImageView) convertView.findViewById(R.id.sale);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.name.setText(mProducts.get(postion).getDisplayName());
        viewHolder.price.setText(mProducts.get(postion).getMemberPrice());
        viewHolder.oldPrice.setText(mProducts.get(postion).getMarketPrice());

        if (mProducts.get(postion).getIsCheck() == 1) {
            viewHolder.number.setText(mProducts.get(postion).getFavourNumber() + "人收藏");
            viewHolder.sale.setVisibility(View.VISIBLE);
            viewHolder.sale.setImageResource(R.drawable.sale_find);
        } else {
            viewHolder.number.setText(mProducts.get(postion).getFindWantNumber() + "人想要");
            viewHolder.sale.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mProducts.get(postion).getImgUrl())) {
            String url = mProducts.get(postion).getImgUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + mProducts.get(postion).getImgUrl();
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.product_home_default)
                    .into(viewHolder.cover);
//            ImageLoader.getInstance(mContext).getBitmapUtils()
//                    .configDefaultLoadingImage(R.drawable.product_list_default)
//                    .configDefaultLoadFailedImage(R.drawable.product_list_default)
//                    .display(viewHolder.cover, url);

//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.product_list_default)
//                    .showImageOnFail(R.drawable.product_list_default)
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
        TextView price;
        ImageView sale;
        TextView desc;
        TextView number;
        TextView oldPrice;
        CountAdjustView adjustView;
    }
}
