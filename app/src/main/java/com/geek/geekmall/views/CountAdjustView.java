package com.geek.geekmall.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 4/24/15.
 */
public class CountAdjustView extends FrameLayout implements View.OnClickListener {
    private Context mContext;
    private TextView mNumView;
    private ImageButton mLeftButton;
    private ImageButton mRightButton;
private ModifyListener listener;
    public CountAdjustView(Context context) {
        this(context, null);
    }

    public CountAdjustView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountAdjustView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        mContext = context;
    }

    public ModifyListener getListener() {
        return listener;
    }

    public void setListener(ModifyListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.count_adjust_left:
                if (Integer.valueOf(this.mNumView.getText().toString()) > 1) {
                    this.mNumView.setText((Integer.valueOf(this.mNumView.getText().toString()) - 1) + "");

                    if (listener != null){
                        listener.onModify(this.mNumView.getText().toString());
                    }

                }

                break;
            case R.id.count_adjust_right:
                this.mNumView.setText((Integer.valueOf(this.mNumView.getText().toString()) + 1) + "");
                if (Integer.valueOf(this.mNumView.getText().toString())>20){
                    this.mNumView.setText(20+"");
                    new ToastView(mContext,"最多购买20件").show();
                } else {
                    if (listener != null){
                        listener.onModify(this.mNumView.getText().toString());
                    }
                }

                break;

            default:
                break;
        }
    }

    protected void init(Context context) {
        inflate(getContext(), R.layout.count_adjust_layout, this);
        mLeftButton = (ImageButton) findViewById(R.id.count_adjust_left);
        mRightButton = (ImageButton) findViewById(R.id.count_adjust_right);
        mNumView = ((TextView) findViewById(R.id.count_adjust_count));
        mLeftButton.setOnClickListener(this);
        mRightButton.setOnClickListener(this);
        mLeftButton.setSelected(true);
        mRightButton.setSelected(true);
        mNumView.setEnabled(false);
//        this.a.setText(String.valueOf(this.c));
//        c();
    }
    public void setNumber(int number){
        mNumView.setText(number+"");
    }
    public String getNumber(){
        return mNumView.getText().toString();
    }
    public interface ModifyListener{
        void onModify(String number);
    }
}
