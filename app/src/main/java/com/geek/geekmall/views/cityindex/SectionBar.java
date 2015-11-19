package com.geek.geekmall.views.cityindex;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.geek.geekmall.R;
import com.geek.geekmall.category.adapter.BrandAdapter;

public class SectionBar extends View {

    private static final char[] WORDS = new char[]{
            '荐','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'
    };

    private int wordSize = 14;
    private int preHeight = wordSize + 2;

    private ListView mListView;
    private Indexer mSectionIndexter;
    // 垂直居中
    private int heightCenter;
    private Paint mPaint;
private  TouchBarListener listener;
    public SectionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TouchBarListener getListener() {
        return listener;
    }

    public void setListener(TouchBarListener listener) {
        this.listener = listener;
    }

    private void init() {
        wordSize = convertDpToPixel(wordSize, getContext());
        preHeight = convertDpToPixel(preHeight, getContext());
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.content_black));
        mPaint.setTextSize(preHeight);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
    }

    public void setListView(ListView listView) {
        mListView = listView;
        BrandAdapter adapter = (BrandAdapter) ((HeaderViewListAdapter)mListView.getAdapter()).getWrappedAdapter();
        if (adapter == null || !(adapter instanceof Indexer)) throw new RuntimeException("ListView must set Adapter or Adapter must implements Indexer interface");
        mSectionIndexter = (Indexer) adapter;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int startY = (int) event.getY() - heightCenter;
        int index = startY / preHeight;
        if (index >= WORDS.length) {
            index = WORDS.length - 1;
        } else if (index < 0) {
            index = 0;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            listener.onTouch(String.valueOf(WORDS[index]));
            int position = mSectionIndexter.getStartPositionOfSection(String.valueOf(WORDS[index]));
            if (position == -1) {
                return true;
            }
            mListView.setSelection(position);
        } else if(event.getAction() == MotionEvent.ACTION_UP){
            listener.onRelease();
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightCenter = getMeasuredHeight()/2 - preHeight*WORDS.length/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < WORDS.length; i++) {
            canvas.drawText(String.valueOf(WORDS[i]), getMeasuredWidth()/2, preHeight
                    + (i * preHeight) + heightCenter, mPaint);
        }
        super.onDraw(canvas);
    }

    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int)(dp * (metrics.densityDpi / 160f) + 0.5);
        return px;
    }
    public interface TouchBarListener{
        void onTouch(String str);
        void onRelease();
    }
}
