package com.geek.geekmall.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by apple on 7/23/15.
 */
public class QRCodeBitmapView extends View {
    private Bitmap mBitmap;
    private Paint paint;
    private Rect dest;
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public QRCodeBitmapView(Context context, Bitmap bitmap) {
        super(context);
        mBitmap = bitmap;
        paint = new Paint();
        double aspectRatio = ((double)mBitmap.getWidth())/mBitmap.getHeight();
        dest = new Rect(0,0,this.getWidth(), (int)(this.getHeight()/aspectRatio));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setFilterBitmap(true);
        canvas.drawBitmap(mBitmap, null, dest, paint);
    }
    public Bitmap createBitmap(String str) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width*height];
            for(int y = 0; y<width; ++y){
                for(int x = 0; x<height; ++x){
                    if(matrix.get(x, y)){
                        pixels[y*width+x] = 0xff000000; // black pixel
                    } else {
                        pixels[y*width+x] = 0xffffffff; // white pixel
                    }
                }
            }
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
