package com.geek.geekmall.category.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Keyword;
import com.geek.geekmall.category.adapter.StringAdapter;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.WordsData;
import com.geek.geekmall.utils.AppUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 5/18/15.
 */
public class SearchDetailActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mHistoryLayout;
    private GridView mHistoryGridView;
    private GridView mHotGridView;
    private ListView mSuggestView;
    private StringAdapter mHistoryAdapter;
    private StringAdapter mSuggestAdapter;
    private StringAdapter mHotAdapter;
    private List<Keyword> mKeywords;
    private List<Keyword> mHistorys;

    private ImageView mProgress;
    private AnimationDrawable mAnimation;
    private EditText mInputEdit;
    private TextView mSearchBtn;
    private TextView mBackView;
private TextView mDeleteView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getHotWords();
        getHistory();
    }

    private void init() {
        mDeleteView = (TextView) findViewById(R.id.delete_words);
        mBackView = (TextView) findViewById(R.id.back);
        mSearchBtn = (TextView) findViewById(R.id.search);
        mSearchBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mDeleteView.setOnClickListener(this);
        mInputEdit = (EditText) findViewById(R.id.search_edit);
        mInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mSuggestView.setVisibility(View.VISIBLE);
                } else {
                    mSuggestView.setVisibility(View.GONE);
                }
            }
        });
        mInputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    startResult(mInputEdit.getText().toString());
                }
                return false;
            }
        });
        mSuggestView = (ListView) findViewById(R.id.suggest_list);
        mSuggestView.setVisibility(View.GONE);
        mProgress = (ImageView) findViewById(R.id.progress);
        mAnimation = (AnimationDrawable) mProgress.getDrawable();
        mHistoryLayout = (RelativeLayout) findViewById(R.id.history_search);
        mHistoryGridView = (GridView) findViewById(R.id.history_list);
        mHotGridView = (GridView) findViewById(R.id.hot_list);
        mHistoryAdapter = new StringAdapter(this);
        mHotAdapter = new StringAdapter(this);
        mSuggestAdapter = new StringAdapter(this);
        mKeywords = new ArrayList<>();
        mHistoryAdapter.setmKeyword(mKeywords);
        mHotAdapter.setmKeyword(mKeywords);
        mSuggestAdapter.setmKeyword(mKeywords);

        mHistoryAdapter.setmKeyword(mKeywords);
        mHistoryGridView.setAdapter(mHistoryAdapter);
        mHotGridView.setAdapter(mHotAdapter);
        mSuggestView.setAdapter(mSuggestAdapter);
        mHotGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startResult(mHotAdapter.getItem(position).getWords());
            }
        });
        mHistoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startResult(mHistoryAdapter.getItem(position).getWords());
            }
        });
    }

    private void getHistory() {
        try {
            mHistorys = AppUtils.getDbUtils(this).findAll(Selector.from(Keyword.class).limit(10).orderBy("times,time", true));
            if (mHistorys!= null && !mHistorys.isEmpty()){
                mHistoryAdapter.setmKeyword(mHistorys);
                mHistoryAdapter.notifyDataSetChanged();
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
private void deleteHistory(){
    try {
        AppUtils.getDbUtils(this).deleteAll(Keyword.class);
    } catch (DbException e) {
        e.printStackTrace();
    }
}
    private void getHotWords() {
        mAnimation.start();
        APIControl.getInstance().searchKeywords(this, SIZE, new DataResponseListener<WordsData>() {
            @Override
            public void onResponse(WordsData wordsData) {
                mAnimation.stop();
                mProgress.setVisibility(View.GONE);
                if (wordsData.getStatus() == 200) {
                    mHotAdapter.setmKeyword(wordsData.getData().getDataList());
                    mHotAdapter.notifyDataSetChanged();
                }
            }
        }, errorListener(URLs.KEYWORD_URL));
    }

    /**
     * 插入数据库，如果有相同搜索记录 增加搜索次数
     *
     * @param keyword
     */
    private void startResult(String keyword) {
        if (!TextUtils.isEmpty(keyword)){
            Keyword word = new Keyword(keyword, System.currentTimeMillis());
            try {
                Keyword first = AppUtils.getDbUtils(this).findFirst(Selector.from(Keyword.class).where("words", "=", keyword));
                if (first != null) {
                    first.setTimes(first.getTimes() + 1);
                    AppUtils.getDbUtils(this).update(first, "words");
                } else {
                    AppUtils.getDbUtils(this).save(word);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(SearchDetailActivity.this, SearchGoodsResultActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("title", keyword);
        startActivity(intent);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                startResult(mInputEdit.getText().toString());
                break;
            case R.id.back:
                finish();
                break;
            case R.id.delete_words:
                deleteHistory();
                mHistorys = null;
                mHistoryAdapter.setmKeyword(mHistorys);
                mHistoryAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
