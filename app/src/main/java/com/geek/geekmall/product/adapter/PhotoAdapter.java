package com.geek.geekmall.product.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.DescriptionImage;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.ListViewUtil;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.ScreenUtils;
import com.geek.geekmall.views.RecyclerImageView;
import com.geek.geekmall.views.ResizableImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private List<DescriptionImage> mPhotos;

    public PhotoAdapter(Context context) {
        mContext = context;
    }

    private ListView mListView;

    public void setmPhotos(List<DescriptionImage> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public List<DescriptionImage> getmPhotos() {
        return mPhotos;
    }

    public ListView getmListView() {
        return mListView;
    }

    public void setmListView(ListView mListView) {
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        if (mPhotos == null) {
            return 0;
        }
        return mPhotos.size();
    }

    @Override
    public DescriptionImage getItem(int i) {
        if (mPhotos == null) {
            return null;
        }
        return mPhotos.get(i);
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
            convertView = mInflater.inflate(R.layout.photo_item, null);

            viewHolder.photo = (RecyclerImageView) convertView.findViewById(R.id.photo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        int maxHeight = DensityUtils.dp2px(mContext, 200);
//        int height = (int) ((float) view.getWidth()/drawable.getMinimumWidth() * drawable.getMinimumHeight());
//        if (height > maxHeight) height = maxHeight;
//        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
        String url = mPhotos.get(position).getImgUrl();
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + url;
            }

            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .tag(mContext)
                    .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .error(R.drawable.product_detail_default)
                    .into(viewHolder);
//            ImageLoader.getInstance(mContext).getBitmapUtils()
//                    .configDefaultLoadingImage(R.drawable.product_detail_default)
//                    .configDefaultLoadFailedImage(R.drawable.product_detail_default)
//                    .display(viewHolder.photo, url);
//显示图片的配置
//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.product_detail_default)
//                    .showImageOnFail(R.drawable.product_detail_default)
//                    .cacheInMemory(false)
//                    .cacheOnDisk(true)
//                    .build();
//            ImageLoader.getInstance(mContext).getUniversalImageLoader().displayImage(url, viewHolder.photo, options);
//            new BitmapUtils(mContext,GeekApplication.PATH,200).display(viewHolder.photo,url);
//            if (viewHolder.photo.getBackground()!= null)
//            MyLog.debug(PhotoAdapter.class, viewHolder.photo.getImageHeight() + "---------" + position);
        }
        return convertView;
    }

    class MySizeChangeListener implements ResizableImageView.SizeChangeListener {
        private ResizableImageView imageView;

        public MySizeChangeListener(ResizableImageView view) {
            imageView = view;
        }

        @Override
        public void onChange(int size) {

            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size));
            MyLog.debug(PhotoAdapter.class, imageView.getHeight() + "++++++++");
        }
    }

    @Override
    public void notifyDataSetChanged() {
        new ListViewUtil().setListViewHeightBasedOnChildren(mListView);
        MyLog.debug(PhotoAdapter.class, "notifyDataSetChanged");
        super.notifyDataSetChanged();
    }

    private class ViewHolder implements Target {
        RecyclerImageView photo;

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int height = (int) (((float) ScreenUtils.getScreenWidth(mContext) / (float) bitmap.getWidth()) * bitmap.getHeight());
            photo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            photo.setImageBitmap(bitmap);
//            MyLog.debug(PhotoAdapter.class,height+"---------"+bitmap.getHeight());
        }


        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }
}
