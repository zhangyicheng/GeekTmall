package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Income;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by apple on 4/28/15.
 */
public class TimeLineAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Context mContext;
    private List<Income> mInComes;
    private LayoutInflater mInflater;

    public void setmInComes(List<Income> mInComes) {
        this.mInComes = mInComes;
    }

    public TimeLineAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        if (mInComes == null) {
            return 0;
        }
        return mInComes.size();
    }

    @Override
    public Object getItem(int i) {
        if (mInComes == null) {
            return null;
        }
        return mInComes.get(i);
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

            convertView = mInflater.inflate(R.layout.time_lime_item, null);
            viewHolder.incomeDetail = (TextView) convertView.findViewById(R.id.income_detail);
            viewHolder.incomeMoney = (TextView) convertView.findViewById(R.id.income_money);
            viewHolder.outcomeDetail = (TextView) convertView.findViewById(R.id.outcome_detail);
            viewHolder.outcomeMoney = (TextView) convertView.findViewById(R.id.outcome_money);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.incomeDetail.setText("销售      " + mInComes.get(position).getAllTruePrice());
        if (!TextUtils.isEmpty(mInComes.get(position).getTrueCutPrice())) {

            viewHolder.incomeMoney.setText("提成      " + mInComes.get(position).getTrueCutPrice() + "元");
        }

        viewHolder.outcomeDetail.setText("销售      " + mInComes.get(position).getAllFalsePrice());
        if (!TextUtils.isEmpty(mInComes.get(position).getFalseCutPrice())) {
            viewHolder.outcomeMoney.setText("提成      " + mInComes.get(position).getFalseCutPrice() + "元");
        }


        return convertView;
    }

    /**
     * Get a View that displays the header data at the specified position in the
     * set. You can either create a View manually or inflate it from an XML layout
     * file.
     *
     * @param position    The position of the item within the adapter's data set of the item whose
     *                    header view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view is
     *                    non-null and of an appropriate type before using. If it is not possible to
     *                    convert this view to display the correct data, this method can create a new
     *                    view.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.income_time, parent, false);

            holder.time = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.time.setText(mInComes.get(position).getCreateTime());
        return convertView;
    }

    /**
     * Get the header id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose header id we
     *                 want.
     * @return The id of the header at the specified position.
     */
    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView incomeDetail;
        TextView incomeMoney;
        TextView outcomeDetail;
        TextView outcomeMoney;
    }

    static class HeaderViewHolder {
        TextView time;
    }
}
