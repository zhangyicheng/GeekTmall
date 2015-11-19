package com.geek.geekmall.views;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.Area;
import com.geek.geekmall.profile.adapter.AreaAdapter;

import java.util.List;


/**
 * 
 * @author angelo
 *
 */
public class ListDialog {
    private Dialog mDialog;
    private TextView mTitle;
    private ListView mListView;
    private AreaAdapter mAdapter;
    public ListDialog(Context context, String title, List<Area> regionses, AdapterView.OnItemClickListener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_dialog, null);
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mListView = (ListView) view.findViewById(R.id.dialog_list);
        mAdapter = new AreaAdapter(context);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(listener);

    }
    public AreaAdapter getAdapter() {
        return mAdapter;
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}