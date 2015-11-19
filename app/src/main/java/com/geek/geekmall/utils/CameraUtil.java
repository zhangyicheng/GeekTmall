package com.geek.geekmall.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.text.format.DateFormat;
import android.util.Log;

import com.sina.weibo.sdk.utils.MD5;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.getExternalStorageState;

public final class CameraUtil {

    private static final String TAG = "CameraUtil";

    private static String filePath = null;

    private CameraUtil() {
        // can't be instantiated
    }

    public static boolean takePhoto(final Activity activity, final String dir, final String filename, final int cmd) {
        filePath = dir + filename;

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File cameraDir = new File(dir);
        if (!cameraDir.exists()) {
            return false;
        }

        final File file = new File(filePath);
        final Uri outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        try {
            activity.startActivityForResult(intent, cmd);

        } catch (final ActivityNotFoundException e) {
            return false;
        }
        return true;
    }

    public static String getResultPhotoPath(Context context, final Intent intent, final String dir) {
        if (filePath != null && new File(filePath).exists()) {
            return filePath;
        }

        return resolvePhotoFromIntent(context, intent, dir);
    }

    public static String resolvePhotoFromIntent(final Context ctx, final Intent data, final String dir) {
        if (ctx == null || data == null || dir == null) {
            Log.e(TAG, "resolvePhotoFromIntent fail, invalid argument");
            return null;
        }

        String filePath = null;

        final Uri uri = Uri.parse(data.toURI());
        Cursor cu = ctx.getContentResolver().query(uri, null, null, null, null);
        if (cu != null && cu.getCount() > 0) {
            try {
                cu.moveToFirst();
                final int pathIndex = cu.getColumnIndex(MediaColumns.DATA);
                Log.e(TAG, "orition: " + cu.getString(cu.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION)));
                filePath = cu.getString(pathIndex);
                Log.d(TAG, "photo from resolver, path:" + filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (data.getData() != null) {
            filePath = data.getData().getPath();
            if (!(new File(filePath)).exists()) {
                filePath = null;
            }
            Log.d(TAG, "photo file from data, path:" + filePath);

        } else if (data.getAction() != null && data.getAction().equals("inline-data")) {

            try {
                final String fileName = MD5.hexdigest(DateFormat.format("yyyy-MM-dd-HH-mm-ss", System.currentTimeMillis()).toString().getBytes()) + ".jpg";
                filePath = dir + fileName;

                final Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                final File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }

                BufferedOutputStream out;
                out = new BufferedOutputStream(new FileOutputStream(file));
                final int cQuality = 100;
                bitmap.compress(Bitmap.CompressFormat.PNG, cQuality, out);
                out.close();
                Log.d(TAG, "photo image from data, path:" + filePath);

            } catch (final Exception e) {
                e.printStackTrace();
            }

        } else {
            if (cu != null) {
                cu.close();
                cu = null;
            }
            Log.e(TAG, "resolve photo from intent failed");
            return null;
        }
        if (cu != null) {
            cu.close();
            cu = null;
        }
        return filePath;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void startPhotoZoom(Activity activity, Uri uri, Uri photoUri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, 3);
    }

    /**
     * 拍照获取图片
     */
    public static Uri takePhoto(Activity activity, Uri photoUri) {
        //执行拍照前，应该先判断SD卡是否存在
        String SDState = getExternalStorageState();
        if (SDState.equals(MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            if (photoUri == null) {
                ContentValues values = new ContentValues();
                photoUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            /**-----------------*/
            activity.startActivityForResult(intent, 2);
        }
        return photoUri;
    }

}
