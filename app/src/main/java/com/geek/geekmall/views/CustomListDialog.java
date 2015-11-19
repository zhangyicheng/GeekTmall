package com.geek.geekmall.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.utils.ScreenUtils;


/**
 * @author angelo
 */
public class CustomListDialog extends Dialog {
    private TextView mTitle;
    private ListView mListView;
    private BaseAdapter mAdapter;
    private ClickListener listener;
private String title;
    public CustomListDialog(Context context, BaseAdapter adapter) {
        super(context, R.style.dialog);
        mAdapter = adapter;
    }

    public ClickListener getListener() {
        return listener;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public BaseAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_dialog);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.7f);
        lp.height = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.85f);

        getWindow().setAttributes(lp);
        mTitle = (TextView) findViewById(R.id.title);
        mListView = (ListView) findViewById(R.id.dialog_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClick(mAdapter.getItem(position));
            }
        });
        mTitle.setText(title);
    }

    public interface ClickListener {
        void onItemClick(Object obj);
    }
    public void setTitle(String title){
        this.title = title;
    }
}