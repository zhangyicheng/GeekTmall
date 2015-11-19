package com.geek.geekmall.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.geek.geekmall.model.AreaData;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Created by apple on 6/2/15.
 */
public class FileUtils {
    private FileUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    public static AreaData readJson(Context context) {
        AssetManager am = context.getAssets();
        try {
            InputStream inputStream = am.open("area.json");
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        String json = new String(buffer,"utf-8");
            AreaData areaData = new Gson().fromJson(json, AreaData.class);
            inputStream.close();
//            inputStreamReader.close();
//            MyLog.debug(FileUtils.class,json);
            return areaData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getFileSize(File f) throws Exception
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            }
            else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }
    public static String FormetFileSize(long fileS)
    {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024)
        {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        }
        else
        {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    public static long getlist(File f)
    {// 递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }

}
