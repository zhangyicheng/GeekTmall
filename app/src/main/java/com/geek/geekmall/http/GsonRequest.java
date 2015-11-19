package com.geek.geekmall.http;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.utils.MD5;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SDCardUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 5/27/15.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final Map mMapParam;
    private String url;

    /**
     * Make a GET request and return a parsed object from JSON. Assumes
     * {@link Method#GET}.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.url = url;
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.mMapParam = new HashMap<>();
        setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1));
        MyLog.debug(clazz, url);
    }

    /**
     * Like the other, but allows you to specify which {@link Method} you want.
     *
     * @param method
     * @param url
     * @param clazz
     * @param headers
     * @param listener
     * @param errorListener
     */
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, Map params, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.url = url;
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.mMapParam = params;
        setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1));
        MyLog.debug(clazz, url);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMapParam;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            String filename = MD5.hexdigest(url);
//            File file = new File(GeekApplication.PATH,filename);
//            if (!file.exists()){
//                file.createNewFile();
//            }
//            FileOutputStream out = new FileOutputStream(file, false);
//            if (SDCardUtils.getSDCardAllSize() > 100) {
//                out.write(json.getBytes());
//            }
            MyLog.debug(this.clazz, json);
            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}