package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.geek.geekmall.R;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbob on 14-9-28.
 */
public class PhotoUpLoadAdapter extends BaseAdapter {

    private List<String> paths;
    private Context mContext;

    public PhotoUpLoadAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (paths == null || paths.isEmpty()) {
            return 1;
        }
        if (paths.size() == 4) {
            return 4;
        }
        return paths.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (paths == null || paths.isEmpty()) {
            return null;
        }
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_photo_upload, null);
            viewHolder = new ViewHolder();
            viewHolder.updateImage = (ImageView) convertView.findViewById(R.id.path);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (paths != null && !paths.isEmpty() && position < paths.size()) {
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(new File(paths.get(position)))
                    .resize(DensityUtils.dp2px(mContext, 75), DensityUtils.dp2px(mContext, 95))
                    .centerCrop()
                    .into(viewHolder.updateImage);
        } else {
            ImageLoader.getInstance(mContext).getPicasso().load(R.drawable.upload_add_photo).into(viewHolder.updateImage);
        }

        return convertView;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void addPath(String path) {
        if (paths == null) {
            paths = new ArrayList<String>();
        }
        if (paths.size() < 4) {
            this.paths.add(path);
        }

        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView updateImage;
    }
}
