package com.geek.geekmall.order.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.OrderProduct;
import com.geek.geekmall.utils.ImageLoader;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class OrderCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderProduct> orderProducts;

    public OrderCommentAdapter(Context context) {
        mContext = context;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    @Override
    public int getCount() {
        if (orderProducts == null) {
            return 0;
        }
        return orderProducts.size();
    }

    @Override
    public OrderProduct getItem(int i) {
        if (orderProducts == null || orderProducts.isEmpty()) {
            return null;
        }
        return orderProducts.get(i);
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
            convertView = mInflater.inflate(R.layout.product_comment_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.goods_title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.goods_price);
            viewHolder.spec = (TextView) convertView.findViewById(R.id.text_size);
            viewHolder.number = (TextView) convertView.findViewById(R.id.goods_number);
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.goods_pic);
            viewHolder.content = (EditText) convertView.findViewById(R.id.comment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(orderProducts.get(position).getGoodsName());
        viewHolder.price.setText("价格：￥" + orderProducts.get(position).getTotalPrice());
        viewHolder.number.setText("数量：" + orderProducts.get(position).getGoodsNumber());

        String url = orderProducts.get(position).getImgUrl();
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url).into(viewHolder.cover);
        }
        viewHolder.content.addTextChangedListener(new ContentWatcher(viewHolder.content, position));

        return convertView;
    }

    class ContentWatcher implements TextWatcher {
        private EditText mEdit;
        private int position;

        ContentWatcher(EditText edit, int position) {
            mEdit = edit;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            orderProducts.get(position).setContent(mEdit.getText().toString());
        }
    }

    private static class ViewHolder {
        private TextView name;
        private TextView price;
        private TextView spec;
        private TextView number;
        private TextView total;
        private ImageView cover;
        private EditText content;
    }
}
