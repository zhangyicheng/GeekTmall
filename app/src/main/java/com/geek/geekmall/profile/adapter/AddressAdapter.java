package com.geek.geekmall.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Address;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class AddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<Address> mAddresses;
private LoadListener listener;
    public AddressAdapter(Context context) {
        mContext = context;
    }

    public void setmAddresses(List<Address> mAddresses) {
        this.mAddresses = mAddresses;
    }

    public List<Address> getmAddresses() {
        return mAddresses;
    }

    public void setListener(LoadListener listener) {
        this.listener = listener;
    }

    public LoadListener getListener() {
        return listener;
    }

    @Override
    public int getCount() {
        if (mAddresses == null) {
            return 0;
        }
        return mAddresses.size();
    }

    @Override
    public Address getItem(int i) {
        if (mAddresses == null) {
            return null;
        }
        return mAddresses.get(i);
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
            convertView = mInflater.inflate(R.layout.address_item, null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.textview_name);
            viewHolder.phone = (TextView) convertView
                    .findViewById(R.id.textview_phone);
            viewHolder.edit = (TextView) convertView.findViewById(R.id.textview_address_edit);
            viewHolder.delete = (TextView) convertView
                    .findViewById(R.id.textview_address_delete);
            viewHolder.area = (TextView) convertView
                    .findViewById(R.id.textview_address_Area);

            viewHolder.icon = (ImageView) convertView.findViewById(R.id.imageview_select_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mAddresses.get(postion).getContactName());
        viewHolder.phone.setText(mAddresses.get(postion).getContactPhone());
        viewHolder.area.setText(mAddresses.get(postion).getProvinceName()+mAddresses.get(postion).getCityName()+mAddresses.get(postion).getZoneName()+mAddresses.get(postion).getAddress());
        if (mAddresses.get(postion).getIsDefault() == 1) {
            viewHolder.icon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.GONE);
        }
        viewHolder.edit.setOnClickListener(new ActionListener(postion));
        viewHolder.delete.setOnClickListener(new ActionListener(postion));
        return convertView;
    }

    class ActionListener implements View.OnClickListener {

        int position;

        public ActionListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.textview_address_edit) {
                if(listener != null){
                    listener.onEdit(mAddresses.get(position));
                }
            } else {
                if(listener != null){
                    listener.onDelete(mAddresses.get(position));
                }
            }

        }
    }

    public interface LoadListener {
        void onEdit(Address address);
        void onDelete(Address address);
    }

    private static class ViewHolder {
        TextView name;
        TextView phone;
        TextView delete;
        TextView edit;
        ImageView icon;
        TextView area;

    }
}
