package com.geek.geekmall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.geek.geekmall.bean.User;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Map;

/**
 * Created by apple on 6/2/15.
 */
public class SharedPreUtil {
    // 用户名key
    public final static String KEY_NAME = "username";

    public final static String KEY_LEVEL = "KEY_LEVEL";


    private static SharedPreUtil s_SharedPreUtil;

    private static User s_User = null;

    private SharedPreferences msp;

    // 初始化，一般在应用启动之后就要初始化
    public static synchronized void initSharedPreference(Context context) {
        if (s_SharedPreUtil == null) {
            s_SharedPreUtil = new SharedPreUtil(context);
        }
        if (s_User != null){

            Log.d("zhong",s_User.toString());
        }
    }

    /**
     * 获取唯一的instance
     *
     * @return
     */
    public static synchronized SharedPreUtil getInstance() {
        return s_SharedPreUtil;
    }

    public SharedPreUtil(Context context) {
        msp = context.getSharedPreferences("SharedPreUtil",
                Context.MODE_PRIVATE | Context.MODE_APPEND);
    }

    public SharedPreferences getSharedPref() {
        return msp;
    }


    public synchronized void putUser(User user) {

        SharedPreferences.Editor editor = msp.edit();

        String str = "";
        try {
            str = SerializableUtil.obj2Str(user);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        editor.putString(KEY_NAME, str);
        editor.apply();

        s_User = user;
    }

    public synchronized User getUser() {

        if (s_User == null) {
            s_User = new User();


            //获取序列化的数据
            String str = msp.getString(SharedPreUtil.KEY_NAME, "");

            try {
                Object obj = SerializableUtil.str2Obj(str);
                if (obj != null) {
                    s_User = (User) obj;
                    if (s_User.getToken() == null || s_User.getToken().isEmpty()){
                        s_User = null;
                    }
                } else {
                    s_User = null;
                }

            } catch (StreamCorruptedException e) {
                s_User = null;
                e.printStackTrace();
            } catch (IOException e) {
                s_User = null;
                e.printStackTrace();
            }

        }

        return s_User;
    }

    public synchronized void DeleteUser() {
        SharedPreferences.Editor editor = msp.edit();
        editor.putString(KEY_NAME, "");

        editor.apply();
        s_User = null;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void put(Context context, String key, Object object) {


        SharedPreferences.Editor editor = msp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(Context context, String key, Object defaultObject) {


        if (defaultObject instanceof String) {
            return msp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return msp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return msp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return msp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return msp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public void remove(Context context, String key) {

        SharedPreferences.Editor editor = msp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public void clear(Context context) {

        SharedPreferences.Editor editor = msp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public boolean contains(Context context, String key) {

        return  msp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public Map<String, ?> getAll(Context context) {

        return msp.getAll();
    }
}
