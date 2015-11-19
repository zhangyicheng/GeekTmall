package com.geek.geekmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.Cart;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.Store;
import com.geek.geekmall.home.activity.ShoppingCartActivity;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.views.CountAdjustView;
import com.geek.geekmall.views.ToastView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class CartAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Store> mStores;
    private SparseBooleanArray mGroupCheck;
    //    private SparseBooleanArray mChildCheck;
    private HashMap<Integer, SparseBooleanArray> mCheckedList;
    private boolean ignoreChange = false;
    private boolean childIgnoreChange = false;
    private CartListener mListener;

    public void setmStores(List<Store> mStores) {
        for (int i = 0; i < mStores.size(); i++) {
            mCheckedList.put(i, new SparseBooleanArray());
        }
        mGroupCheck = new SparseBooleanArray();
        this.mStores = mStores;
    }

    public void setmListener(CartListener mListener) {
        this.mListener = mListener;
    }

    public void setChildIgnoreChange(boolean childIgnoreChange) {
        this.childIgnoreChange = childIgnoreChange;
    }

    public void setmGroupCheck(SparseBooleanArray mGroupCheck) {
        this.mGroupCheck = mGroupCheck;
    }

    public List<Store> getmStores() {
        return mStores;
    }

    public CartAdapter(Context context) {
        mContext = context;
        mGroupCheck = new SparseBooleanArray();
        mCheckedList = new HashMap<>();
//        mChildCheck = new SparseBooleanArray();
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        if (mStores == null) {
            return 0;
        }
        return mStores.size();
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
        if (mStores == null) {
            return 0;
        }
        if (mStores.get(groupPosition).getGoodsInfoMap() == null) {
            return 0;
        }
        return mStores.get(groupPosition).getGoodsInfoMap().size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Store getGroup(int groupPosition) {
        return mStores.get(groupPosition);
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
    public Cart getChild(int groupPosition, int childPosition) {
        return mStores.get(groupPosition).getGoodsInfoMap().get(childPosition);
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = (View) LayoutInflater.from(mContext).inflate(
                    R.layout.shopping_cart_outter, null);
            groupHolder = new GroupHolder();
            groupHolder.name = (TextView) convertView.findViewById(R.id.store_name);
            groupHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_store);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.name.setText(mStores.get(groupPosition).getStoreName());
        groupHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                mGroupCheck.put(groupPosition, checked);
                childIgnoreChange = false;
                MyLog.debug(CartAdapter.class, "notifyDataSetChanged");
                notifyDataSetChanged();

            }
        });

        if (mGroupCheck.size() > 0) {
            groupHolder.checkBox.setChecked(mGroupCheck.get(groupPosition));
        } else {
            groupHolder.checkBox.setChecked(false);
        }
        if (getCheckedCount(mGroupCheck) == getGroupCount()) {
            ((ShoppingCartActivity) mContext).updateCheckAll(true);
        } else {
            ((ShoppingCartActivity) mContext).updateCheckAll(false);
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.shopping_cart_goods_item, null);
            itemHolder = new ItemHolder();
            itemHolder.name = (TextView) convertView.findViewById(R.id.goods_title);
            itemHolder.delete = (ImageView) convertView.findViewById(R.id.del_goods_btn);
            itemHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            itemHolder.cover = (ImageView) convertView.findViewById(R.id.goods_pic);
            itemHolder.spec = (TextView) convertView.findViewById(R.id.text_size);
            itemHolder.adjustView = (CountAdjustView) convertView.findViewById(R.id.count_ajust_view);
            itemHolder.price = (TextView) convertView.findViewById(R.id.goods_price);

            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShoppingCartActivity) mContext).deleteCarts(mStores.get(groupPosition).getGoodsInfoMap().get(childPosition));
            }
        });
        itemHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductInfoActivity.class);
                Product product = new Product();
                product.setId(getChild(groupPosition, childPosition).getGoodsId());
                intent.putExtra("product", product);
                mContext.startActivity(intent);
            }
        });
        itemHolder.adjustView.setListener(new CountAdjustView.ModifyListener() {
            @Override
            public void onModify(String number) {
                MyLog.debug(CartAdapter.class, "购物车数量--===" + number);

                    mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).setGoodsNumber(Integer.valueOf(number));
                    ((ShoppingCartActivity) mContext).modifyCarts(mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getShopCartId(), number);

            }
        });
        if (mGroupCheck.size() > 0 && !childIgnoreChange) {
            MyLog.debug(CartAdapter.class, "333 notifyDataSetChanged");
            itemHolder.checkBox.setChecked(mGroupCheck.get(groupPosition));
            mCheckedList.get(groupPosition).put(childPosition, mGroupCheck.get(groupPosition));
        } else if (mGroupCheck.size() == 0) {
            itemHolder.checkBox.setChecked(false);
        }
        if (getCheckedCount(mGroupCheck) == getGroupCount()) {
            if (mListener != null) {
                mListener.onCheck();
            }
            ((ShoppingCartActivity) mContext).updateCheckAll(true);
        } else {
            if (mListener != null) {
                mListener.onCheck();
            }
            ((ShoppingCartActivity) mContext).updateCheckAll(false);
        }
        itemHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                childIgnoreChange = true;
                mCheckedList.get(groupPosition).put(childPosition, checked);
                if (!checked) {
                    mGroupCheck.put(groupPosition, checked);
                    notifyDataSetChanged();
                    MyLog.debug(CartAdapter.class, "111 notifyDataSetChanged");
                }
                if (checked && getCheckedCount(mCheckedList.get(groupPosition)) == getChildrenCount(groupPosition)) {
                    mGroupCheck.put(groupPosition, checked);
                    notifyDataSetChanged();
                    MyLog.debug(CartAdapter.class, "222 notifyDataSetChanged");
                }
                if (mListener != null) {
                    mListener.onCheck();
                }
            }
        });
        itemHolder.spec.setText(mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getGoodsSpecInfo());

        itemHolder.adjustView.setNumber(mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getGoodsNumber());
        itemHolder.name.setText(mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getGoodsName());
        itemHolder.price.setText("￥" + mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getMemberPrice() + "");
        if (!TextUtils.isEmpty(mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getImageUrl())) {
            String url = mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getImageUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + mStores.get(groupPosition).getGoodsInfoMap().get(childPosition).getImageUrl();
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .placeholder(R.drawable.product_small_default).into(itemHolder.cover);
        }
        return convertView;
    }

    public List<Cart> getChecked() {
        List<Cart> carts = new ArrayList<>();
        for (int i = 0; i < mCheckedList.size(); i++) {
            for (int j = 0; j < mCheckedList.get(i).size(); j++) {
                if (mCheckedList.get(i).valueAt(j)) {
                    if (i<mStores.size()){
                        carts.add(mStores.get(i).getGoodsInfoMap().get(mCheckedList.get(i).keyAt(j)));
                    }

                }
            }
        }
        return carts;

    }

    private int getCheckedCount(SparseBooleanArray arr) {
        int count = 0;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.valueAt(i)) {
                count++;
            }
        }
        return count;
    }

    public interface CartListener {
        void onCheck();
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
        public CheckBox checkBox;
    }

    class ItemHolder {
        public TextView name;
        public TextView price;
        public TextView spec;
        public ImageView delete;
        public CheckBox checkBox;
        public ImageView cover;
        public CountAdjustView adjustView;

    }
}
