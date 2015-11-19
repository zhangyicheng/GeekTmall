package com.geek.geekmall.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.Order;
import com.geek.geekmall.bean.OrderProduct;
import com.geek.geekmall.order.activity.CommentActivity;
import com.geek.geekmall.order.activity.LogisticsActivity;
import com.geek.geekmall.order.activity.OrderDetailActivity;
import com.geek.geekmall.order.activity.OrderListActivty;
import com.geek.geekmall.order.activity.OrderpayActivity;
import com.geek.geekmall.profile.activity.ApplyActivity;
import com.geek.geekmall.utils.ImageLoader;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class OrderAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Order> mOrders;
private int order;
    public void setmOrders(List<Order> mOrders) {
        this.mOrders = mOrders;
    }

    public OrderAdapter(Context context) {
        mContext = context;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        if (mOrders == null) {
            return 0;
        }
        return mOrders.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (mOrders == null) {
            return 0;
        }
        if (mOrders.get(groupPosition).getGoodsList() == null) {
            return 0;
        }
        return mOrders.get(groupPosition).getGoodsList().size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Order getGroup(int groupPosition) {
        if (mOrders == null || mOrders.size() == 0) {
            return null;
        }
        return mOrders.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public OrderProduct getChild(int groupPosition, int childPosition) {
        return mOrders.get(groupPosition).getGoodsList().get(childPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = (View) LayoutInflater.from(mContext).inflate(
                    R.layout.settlement_outter, null);
            groupHolder = new GroupHolder();
            groupHolder.name = (TextView) convertView.findViewById(R.id.store_name);
            groupHolder.number = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.name.setText(mOrders.get(groupPosition).getStoreName());
        if (order == 8){
            if (mOrders.get(groupPosition).getRefundStatus() == 1) {
                groupHolder.number.setText("等待卖家待确认");
            } else if (mOrders.get(groupPosition).getRefundStatus() == 2) {
                groupHolder.number.setText("卖家驳回");
            }else if (mOrders.get(groupPosition).getRefundStatus() == 3) {
                groupHolder.number.setText("卖家同意");
            }else if (mOrders.get(groupPosition).getRefundStatus() == 4) {
                groupHolder.number.setText("买家发货");
            }else if (mOrders.get(groupPosition).getRefundStatus() == 5) {
                groupHolder.number.setText("退款成功");
            }else if (mOrders.get(groupPosition).getRefundStatus() == 6) {
                groupHolder.number.setText("退款关闭");
            }else if (mOrders.get(groupPosition).getRefundStatus() == 7) {
                groupHolder.number.setText("退款失败");
            }else if (mOrders.get(groupPosition).getRefundStatus() == 8) {
                groupHolder.number.setText("换货");
            }
        } else {
            if (mOrders.get(groupPosition).getPayStatus() == 1) {
                groupHolder.number.setText("待支付");
            } else if (mOrders.get(groupPosition).getPayStatus() == 2) {
                groupHolder.number.setText("待发货");
            } else if (mOrders.get(groupPosition).getPayStatus() == 3) {
                groupHolder.number.setText("待收货");
            } else if (mOrders.get(groupPosition).getPayStatus() == 4) {
                groupHolder.number.setText("已完成");
            } else if (mOrders.get(groupPosition).getPayStatus() == 5) {
                groupHolder.number.setText("已关闭");
            } else if (mOrders.get(groupPosition).getPayStatus() == 6) {
                groupHolder.number.setText("已取消");
            } else if (mOrders.get(groupPosition).getPayStatus() == 7) {
                groupHolder.number.setText("交易成功");
            }
        }

        return convertView;
    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.settlement_item, null);
            itemHolder = new ItemHolder();
            itemHolder.name = (TextView) convertView.findViewById(R.id.goods_title);
            itemHolder.price = (TextView) convertView.findViewById(R.id.goods_price);
            itemHolder.spec = (TextView) convertView.findViewById(R.id.text_size);
            itemHolder.number = (TextView) convertView.findViewById(R.id.goods_number);
            itemHolder.cover = (ImageView) convertView.findViewById(R.id.goods_pic);
            itemHolder.yunfeiLayout = (RelativeLayout) convertView.findViewById(R.id.yunefi_layout);
            itemHolder.totalLayout = (RelativeLayout) convertView.findViewById(R.id.total_layout);
            itemHolder.buttonLayout = (LinearLayout) convertView.findViewById(R.id.button_layout);
            itemHolder.yunfei = (TextView) convertView.findViewById(R.id.youfei);
            itemHolder.total = (TextView) convertView.findViewById(R.id.total);
            itemHolder.isActivity = (TextView) convertView.findViewById(R.id.isActivity);
            itemHolder.button1 = (Button) convertView.findViewById(R.id.cancel_order);
            itemHolder.button2 = (Button) convertView.findViewById(R.id.pay_money);
            itemHolder.mReFund = (Button) convertView.findViewById(R.id.refund);
            itemHolder.totalNumber = (TextView) convertView.findViewById(R.id.total_number);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.name.setText(mOrders.get(groupPosition).getGoodsList().get(childPosition).getGoodsName());
        itemHolder.price.setText("价格：￥" + mOrders.get(groupPosition).getGoodsList().get(childPosition).getTotalPrice());
        itemHolder.number.setText("数量：" + mOrders.get(groupPosition).getGoodsList().get(childPosition).getGoodsNumber());
        String url = mOrders.get(groupPosition).getGoodsList().get(childPosition).getImgUrl();
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url).into(itemHolder.cover);
        }
        if (isLastChild) {
            itemHolder.yunfeiLayout.setVisibility(View.VISIBLE);
            itemHolder.totalLayout.setVisibility(View.VISIBLE);
            itemHolder.buttonLayout.setVisibility(View.VISIBLE);
            itemHolder.totalNumber.setText(mContext.getResources().getString(R.string.total_number,mOrders.get(groupPosition).getGoodsNum()));
            itemHolder.yunfei.setText("￥" + mOrders.get(groupPosition).getPostage());
            itemHolder.total.setText("￥" + mOrders.get(groupPosition).getTotalPrice());
            itemHolder.button1.setOnClickListener(new OrderClickListenr(groupPosition, childPosition));
            itemHolder.button2.setOnClickListener(new OrderClickListenr(groupPosition, childPosition));
            if (order == 8){
                itemHolder.buttonLayout.setVisibility(View.GONE);
            } else {
                if (mOrders.get(groupPosition).getPayStatus() == 1) {
                    itemHolder.button1.setText("取消订单");
                    itemHolder.button2.setText("付款");
                    itemHolder.buttonLayout.setVisibility(View.VISIBLE);
                } else if (mOrders.get(groupPosition).getPayStatus() == 2) {
                    itemHolder.buttonLayout.setVisibility(View.GONE);
                } else if (mOrders.get(groupPosition).getPayStatus() == 3) {
                    itemHolder.buttonLayout.setVisibility(View.VISIBLE);
                    itemHolder.button1.setText("查看物流");
                    itemHolder.button2.setText("确认收货");
                } else if (mOrders.get(groupPosition).getPayStatus() == 4) {
                    itemHolder.buttonLayout.setVisibility(View.VISIBLE);
                    itemHolder.button1.setText("删除订单");
//                itemHolder.button2.setText("已完成");
                    itemHolder.button1.setVisibility(View.VISIBLE);
                    itemHolder.button2.setVisibility(View.GONE);
                } else if (mOrders.get(groupPosition).getPayStatus() == 5) {
                    itemHolder.button1.setVisibility(View.VISIBLE);
                    itemHolder.button2.setVisibility(View.GONE);
                    itemHolder.button1.setText("删除订单");
//                itemHolder.button2.setText("已关闭");
                    itemHolder.buttonLayout.setVisibility(View.VISIBLE);
                } else if (mOrders.get(groupPosition).getPayStatus() == 6) {
                    itemHolder.button1.setText("删除订单");
                    itemHolder.button1.setVisibility(View.VISIBLE);
                    itemHolder.button2.setVisibility(View.GONE);
                    itemHolder.buttonLayout.setVisibility(View.VISIBLE);
                } else if (mOrders.get(groupPosition).getPayStatus() == 7) {
                    itemHolder.button1.setText("删除订单");
                    itemHolder.button2.setText("评价订单");
                }
            }

        } else {
            itemHolder.yunfeiLayout.setVisibility(View.GONE);
            itemHolder.totalLayout.setVisibility(View.GONE);
        }
//        itemHolder.mReFund.setVisibility(View.VISIBLE);
//        itemHolder.mReFund.setOnClickListener(new OrderClickListenr(groupPosition,childPosition));
        return convertView;
    }

    class OrderClickListenr implements View.OnClickListener {
        private int position;
        private int childPosition;

        public OrderClickListenr(int pos, int childPosition) {
            position = pos;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.refund:
                    Intent intent1 = new Intent(mContext, ApplyActivity.class);
                    intent1.putExtra("goodsOrderId", mOrders.get(position).getGoodsList().get(childPosition).getGoodsOrderId());
                    intent1.putExtra("order", mOrders.get(position));
                    intent1.putExtra("totalPrice", mOrders.get(position).getGoodsList().get(childPosition).getTotalPrice());
                    mContext.startActivity(intent1);
                    break;
                case R.id.cancel_order:
                    if (mOrders.get(position).getPayStatus() == 1) {
                        ((OrderListActivty) mContext).cancelOrder(mOrders.get(position).getId());
                    } else if (mOrders.get(position).getPayStatus() == 2) {

                    } else if (mOrders.get(position).getPayStatus() == 3) {
                        Intent intent = new Intent(mContext, OrderDetailActivity.class);
                        intent.putExtra("order", mOrders.get(this.position));
                        mContext.startActivity(intent);
                    } else if (mOrders.get(position).getPayStatus() == 4) {
                        ((OrderListActivty) mContext).deleteOrder(mOrders.get(position).getId());
                    } else if (mOrders.get(position).getPayStatus() == 5) {
                        ((OrderListActivty) mContext).deleteOrder(mOrders.get(position).getId());
                    }else if (mOrders.get(position).getPayStatus() == 6) {
                        ((OrderListActivty) mContext).deleteOrder(mOrders.get(position).getId());
                    }
                    else if (mOrders.get(position).getPayStatus() == 7) {
//                        Intent intent = new Intent(mContext, CommentActivity.class);
//                        intent.putExtra("order", mOrders.get(position));
//                        mContext.startActivity(intent);
                        ((OrderListActivty) mContext).deleteOrder(mOrders.get(position).getId());
                    }
                    break;
                case R.id.pay_money:
                    if (mOrders.get(position).getPayStatus() == 1) {
                        Intent intent = new Intent(mContext, OrderpayActivity.class);
                        intent.putExtra("order", mOrders.get(this.position));
                        mContext.startActivity(intent);
//                        ((OrderListActivty) mContext).payOrder(mOrders.get(this.position).getId(), mOrders.get(position).getPayType());
                    } else if (mOrders.get(position).getPayStatus() == 2) {

                    } else if (mOrders.get(position).getPayStatus() == 3) {
                        ((OrderListActivty) mContext).sureRecive(mOrders.get(position).getId());
                    } else if (mOrders.get(position).getPayStatus() == 4) {

                    } else if (mOrders.get(position).getPayStatus() == 5) {

                    } else if (mOrders.get(position).getPayStatus() == 7) {
                        Intent intent = new Intent(mContext, CommentActivity.class);
                        intent.putExtra("order", mOrders.get(position));
                        mContext.startActivity(intent);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void doAction(int position) {

    }

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        private TextView name;
        private TextView number;
    }

    class ItemHolder {
        private Button mReFund;
        private TextView isActivity;

        private TextView totalNumber;
        private TextView name;
        private TextView price;
        private TextView spec;
        private TextView number;
        private TextView yunfei;
        private TextView total;
        private ImageView cover;
        private RelativeLayout yunfeiLayout;
        private RelativeLayout totalLayout;
        private LinearLayout buttonLayout;
        private Button button1;
        private Button button2;
    }
}
