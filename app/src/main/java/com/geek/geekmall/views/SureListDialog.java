package com.geek.geekmall.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 6/11/15.
 */
public class SureListDialog extends Dialog {
    private Button cancel;
    private Button okBtn;
    private TextView titleView;
    private ListView listView;
    private FreightListener listener;
    private BaseAdapter mAdapter;

    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public SureListDialog(Context context, FreightListener listener) {
        super(context, R.style.dialog);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_sure_dialog);
        okBtn = (Button) findViewById(R.id.ok_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        titleView = (TextView) findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.dialog_list);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setFreightPosition(checkedItem);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setItemChecked(position, true);
                checkedItem = position;
                mAdapter.notifyDataSetChanged();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
private int checkedItem;
    public ListView getListView() {
        return listView;
    }

    public void setCheckItem(int position, boolean checked) {
        listView.setItemChecked(position, checked);
    }

    public void setAdapter(int position, BaseAdapter adapter) {
        mAdapter = adapter;
        listView.setAdapter(adapter);
        listView.setItemChecked(position, true);

    }

    public BaseAdapter getmAdapter() {
        return mAdapter;
    }

    public void setTitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public interface FreightListener {
        void setFreightPosition(int position);
    }
}
