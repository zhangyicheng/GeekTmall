package com.geek.geekmall.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.home.activity.SettlementActivity;
import com.geek.geekmall.model.GoodsInfoMap;
import com.geek.geekmall.model.SettlementShopCart;
import com.geek.geekmall.utils.ImageLoader;

import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class SettlementAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<SettlementShopCart> mCarts;
    private YunFeiListener listener;
    private HashMap<Integer, Integer> checkMap;

    public SettlementAdapter(Context context) {
        checkMap = new HashMap<>();
        mContext = context;
    }

    public void setmCarts(List<SettlementShopCart> mCarts) {
        this.mCarts = mCarts;
    }

    public List<SettlementShopCart> getmCarts() {
        return mCarts;
    }

    public void setListener(YunFeiListener listener) {
        this.listener = listener;
    }

    public HashMap<Integer, Integer> getCheckMap() {
        return checkMap;
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        if (mCarts == null) {
            return 0;
        }
        return mCarts.size();
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
        if (mCarts == null) {
            return 0;
        }
        if (mCarts.get(groupPosition).getGoodsInfoMap() == null) {
            return 0;
        }
        return mCarts.get(groupPosition).getGoodsInfoMap().size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        if (mCarts == null || mCarts.size() == 0) {
            return null;
        }
        return mCarts.get(groupPosition);
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
    public GoodsInfoMap getChild(int groupPosition, int childPosition) {
        return mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition);
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
        groupHolder.name.setText(mCarts.get(groupPosition).getStoreName());
        groupHolder.number.setText("共" + mCarts.get(groupPosition).getTotalNum() + "件");
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
            itemHolder.yunfei = (TextView) convertView.findViewById(R.id.youfei);
            itemHolder.total = (TextView) convertView.findViewById(R.id.total);
            itemHolder.teteCoinLayout = (RelativeLayout) convertView.findViewById(R.id.virture_coin);
            itemHolder.isCheckBox = (CheckBox) convertView.findViewById(R.id.isCheck);
            itemHolder.reMainCoin = (TextView) convertView.findViewById(R.id.remain_coin);
            itemHolder.isActivity = (TextView) convertView.findViewById(R.id.isActivity);
            itemHolder.mTotalNumber = (TextView) convertView.findViewById(R.id.total_number);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.name.setText(mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getGoodsName());
        itemHolder.price.setText("价格：￥" + mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getMemberPrice());
        itemHolder.number.setText("数量："+mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getGoodsNumber());
        if (mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getIsFirstFree() == 1) {
            itemHolder.isActivity.setVisibility(View.VISIBLE);

            if (mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getCanUseCoin().equals("0")||mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getCanUseCoin().equals("0.0")){
                itemHolder.teteCoinLayout.setVisibility(View.GONE);
            } else {
                itemHolder.teteCoinLayout.setVisibility(View.VISIBLE);
                itemHolder.reMainCoin.setText(mContext.getResources().getString(R.string.tetecoin,
                        mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getCanUseCoin()));
            }
        } else {
            itemHolder.isActivity.setVisibility(View.GONE);
            itemHolder.teteCoinLayout.setVisibility(View.GONE);
        }
        if ( mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getCoinSelect()==1){
            itemHolder.isCheckBox.setChecked(true);
        }
//        else {
//            itemHolder.isCheckBox.setChecked(false);
//        }
        itemHolder.isCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).setCoinSelect(1);
                } else {
                    mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).setCoinSelect(0);
                }
                ((SettlementActivity)mContext).calculate();

            }
        });
        if (!TextUtils.isEmpty(mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getImageUrl())) {
            String url = mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getImageUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + mCarts.get(groupPosition).getGoodsInfoMap().get(childPosition).getImageUrl();
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url).into(itemHolder.cover);
        }
        if (isLastChild) {
            itemHolder.mTotalNumber.setText(mContext.getResources().getString(R.string.total_number, mCarts.get(groupPosition).getTotalNum()));
            itemHolder.yunfeiLayout.setVisibility(View.VISIBLE);
            itemHolder.totalLayout.setVisibility(View.VISIBLE);
            if (mCarts.get(groupPosition).getDistributionWay() != null && mCarts.get(groupPosition).getDistributionWay().size() > 0) {
                final TextView view = itemHolder.yunfei;
                itemHolder.yunfeiLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((SettlementActivity) mContext).setFreight(view, mCarts.get(groupPosition).getStoreId(), mCarts.get(groupPosition).getDistributionWay());
                    }
                });
                itemHolder.yunfei.setText(mCarts.get(groupPosition).getDistributionWay().get(0).getPostage() + "元");
            } else {
                itemHolder.yunfei.setText("包邮");
            }
            itemHolder.total.setText("￥" + mCarts.get(groupPosition).getTotalPrice());


        } else {
            itemHolder.yunfeiLayout.setVisibility(View.GONE);
            itemHolder.totalLayout.setVisibility(View.GONE);
        }

        return convertView;
    }

    public interface YunFeiListener {
        void onYunFei(TextView view);
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
        return false;
    }

    class GroupHolder {
        public TextView name;
        public TextView number;

    }

    class ItemHolder {
        private TextView mTotalNumber;
        private TextView isActivity;
        public TextView reMainCoin;
        public CheckBox isCheckBox;
        public TextView name;
        public TextView price;
        public TextView spec;
        public TextView number;
        public TextView yunfei;
        public TextView total;
        public ImageView cover;
        public RelativeLayout yunfeiLayout;
        private RelativeLayout totalLayout;
        private RelativeLayout teteCoinLayout;
    }
}
