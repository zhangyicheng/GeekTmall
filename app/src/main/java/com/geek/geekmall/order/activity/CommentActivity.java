package com.geek.geekmall.order.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Order;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.OrderCommentData;
import com.geek.geekmall.order.adapter.OrderCommentAdapter;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.views.ToastView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 6/23/15.
 */
public class CommentActivity extends BaseActivity {
    private User user;
    private String userId;
    private String token;
    private Order mOrder;
    private OrderCommentAdapter mAdapter;
    private ListView mListView;
    private Button mCommit;
    private TextView mStoreNameView;
    private TextView mProductNumView;
    private ImageView mStoreView;
    private TextView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);
        mOrder = (Order) getIntent().getSerializableExtra("order");
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        init();
        mAdapter = new OrderCommentAdapter(this);
        mAdapter.setOrderProducts(mOrder.getGoodsList());
        mListView.setAdapter(mAdapter);
    }

    private void init() {
        mBackView = (TextView) findViewById(R.id.back);
        mStoreNameView = (TextView) findViewById(R.id.store_name);
        mProductNumView = (TextView) findViewById(R.id.number);
        mStoreView = (ImageView) findViewById(R.id.cover);
        mStoreNameView.setText(mOrder.getStoreName());
        mProductNumView.setText("共" + mOrder.getGoodsNum() + "件");

//        ImageLoader.getInstance(this).getPicasso().load(mOrder.get)
//                .placeholder(R.drawable.my_avator)
//                .transform(ImageLoader.getInstance(this).new RoundedCornersTransformation(120))
//                .into(mAvator);
        mListView = (ListView) findViewById(R.id.products);
        mCommit = (Button) findViewById(R.id.commit);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitComment();
            }
        });
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void commitComment() {
//        mAdapter.notifyDataSetChanged();
        Gson gson = new Gson();
        List<OrderCommentData> data = new ArrayList<>();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            OrderCommentData commentData = new OrderCommentData();
            commentData.setContent(mAdapter.getItem(i).getContent());
            commentData.setGoodsId(mAdapter.getItem(i).getGoodsId());
            commentData.setUserId(userId);
            commentData.setGoodsOrderId(mAdapter.getItem(i).getGoodsOrderId());
            data.add(commentData);
        }
        String json = gson.toJson(data);
        MyLog.debug(CommentActivity.class, json);
        APIControl.getInstance().addComment(this, json, new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                if (commonData.getStatus() == 200) {
                    finish();
                } else {
                    new ToastView(CommentActivity.this,commonData.getErrorMsg()).show();
                }
            }
        }, errorListener(""));
    }
}
