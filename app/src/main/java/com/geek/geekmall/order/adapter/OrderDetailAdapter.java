package com.geek.geekmall.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.Order;
import com.geek.geekmall.bean.OrderProduct;
import com.geek.geekmall.profile.activity.ApplyActivity;
import com.geek.geekmall.utils.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class OrderDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderProduct> orderProducts;
    private Order mOrder;
private boolean isApply;
    private boolean isPay;
    private String goodsOrderId;
    public OrderDetailAdapter(Context context) {
        mContext = context;
    }

    public String getGoodsOrderId() {
        return goodsOrderId;
    }

    public void setGoodsOrderId(String goodsOrderId) {
        this.goodsOrderId = goodsOrderId;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setIsPay(boolean isPay) {
        this.isPay = isPay;
    }

    public boolean isApply() {
        return isApply;
    }

    public void setIsApply(boolean isApply) {
        this.isApply = isApply;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public Order getmOrder() {
        return mOrder;
    }

    public void setmOrder(Order mOrder) {
        this.mOrder = mOrder;
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
            convertView = mInflater.inflate(R.layout.settlement_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.goods_title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.goods_price);
            viewHolder.spec = (TextView) convertView.findViewById(R.id.text_size);
            viewHolder.number = (TextView) convertView.findViewById(R.id.goods_number);
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.goods_pic);
            viewHolder.mReFund = (Button) convertView.findViewById(R.id.refund);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(orderProducts.get(position).getGoodsName());
        viewHolder.price.setText("价格：￥" + orderProducts.get(position).getTotalPrice());
        viewHolder.number.setText("数量：" + orderProducts.get(position).getGoodsNumber());
        String url = orderProducts.get(position).getImgUrl();
        if (!url.startsWith("http://")) {
            url = URLs.IMAGE_URL + orderProducts.get(position).getImgUrl();
        }
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url).into(viewHolder.cover);
        }
        if (mOrder.getPayStatus() == 2 || mOrder.getPayStatus() == 3 || mOrder.getPayStatus() == 4 || mOrder.getPayStatus() == 7) {
            viewHolder.mReFund.setVisibility(View.VISIBLE);

            if (orderProducts.get(position).getRefundStatus() == 0) {
                viewHolder.mReFund.setText("退款");
                viewHolder.mReFund.setEnabled(true);
                viewHolder.mReFund.setOnClickListener(new OrderClickListenr(position));
            } else if (orderProducts.get(position).getRefundStatus() == 1) {
                viewHolder.mReFund.setText("已申请");
                viewHolder.mReFund.setEnabled(false);
            } else if (orderProducts.get(position).getRefundStatus() == 2) {
                viewHolder.mReFund.setText("退款中");
                viewHolder.mReFund.setEnabled(false);
            } else if (orderProducts.get(position).getRefundStatus() == 3) {
                viewHolder.mReFund.setText("退款成功");
                viewHolder.mReFund.setEnabled(false);
            } else if (orderProducts.get(position).getRefundStatus() == 4){
                viewHolder.mReFund.setText("退款");
                viewHolder.mReFund.setEnabled(true);
                viewHolder.mReFund.setOnClickListener(new OrderClickListenr(position));
            }
            if (isApply && goodsOrderId.equals(orderProducts.get(position).getGoodsOrderId())){
                viewHolder.mReFund.setText("退款中");
                viewHolder.mReFund.setEnabled(false);
            }
        } else {
            viewHolder.mReFund.setVisibility(View.GONE);
        }

        return convertView;
    }

    class OrderClickListenr implements View.OnClickListener {
        private int position;

        public OrderClickListenr(int pos) {
            position = pos;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.refund:
                    Intent intent1 = new Intent(mContext, ApplyActivity.class);
                    intent1.putExtra("order", mOrder);
                    intent1.putExtra("goodsOrderId", orderProducts.get(position).getGoodsOrderId());
                    intent1.putExtra("totalPrice", orderProducts.get(position).getTotalPrice());
                    mContext.startActivity(intent1);
                    break;

                default:
                    break;
            }
        }
    }


    private static class ViewHolder {
        private TextView name;
        private TextView price;
        private TextView spec;
        private TextView number;
        private TextView total;
        private ImageView cover;
        private Button mReFund;
    }
}
