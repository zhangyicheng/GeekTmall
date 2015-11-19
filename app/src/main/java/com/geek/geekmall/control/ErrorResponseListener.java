package com.geek.geekmall.control;

import com.android.volley.Response;

/**
 * Created by apple on 8/26/15.
 */
public interface ErrorResponseListener<T> extends Response.ErrorListener {

    void onResponse(T t);
}
