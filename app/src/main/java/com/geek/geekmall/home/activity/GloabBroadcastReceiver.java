package com.geek.geekmall.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by apple on 4/27/15.
 */
public class GloabBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String str = intent.getAction();
        if (!"com.meilishuo.app.action.refresh.shoppingcart".equals(str)){

        }
    }
}
