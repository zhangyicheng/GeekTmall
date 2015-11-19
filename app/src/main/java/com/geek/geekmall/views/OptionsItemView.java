package com.geek.geekmall.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.utils.GeekUtils;

/**
 * Created by apple on 4/22/15.
 */
public class OptionsItemView extends RelativeLayout {
    private ImageView mIconImage;
    private ImageView mIconArrow;
    private ImageView mIconDot;
    private TextView mContent;
    private TextView mTitle;
    private TextView mNum;
    private int titleColor;
    private int contentColor;
    private int numColor;
    private int numBg;
    private int image;
    private String title;
    private String content;
    private String num;
    private Context mContext;

    public OptionsItemView(Context context) {
        this(context, null);
    }

    public OptionsItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        int i1 = GeekUtils.spTopx(getContext(), 5);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_options_item, null);
        addView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.mIconImage = ((ImageView) view.findViewById(R.id.iv_ico));
        this.mTitle = ((TextView) view.findViewById(R.id.tv_title));
        this.mNum = ((TextView) view.findViewById(R.id.tv_num));
        this.mContent = ((TextView) view.findViewById(R.id.tv_content));
        this.mIconArrow = ((ImageView) view.findViewById(R.id.iv_arrow));
        this.mIconDot = ((ImageView) view.findViewById(R.id.iv_dot));
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.OptionsItemView);
        this.titleColor = typedArray.getColor(R.styleable.OptionsItemView_optionTitleColor, getResources().getColor(R.color.main_text_color_grey));
        this.contentColor = typedArray.getColor(R.styleable.OptionsItemView_optionContentColor, getResources().getColor(R.color.main_pink));
        this.numColor = typedArray.getColor(R.styleable.OptionsItemView_optionNumColor, getResources().getColor(R.color.white));
        this.numBg = typedArray.getResourceId(R.styleable.OptionsItemView_optionNumBg, R.drawable.shoplist_out_num);
        this.image = typedArray.getResourceId(R.styleable.OptionsItemView_optionImage, 0);
        this.title = typedArray.getString(R.styleable.OptionsItemView_optionTitle);
        this.content = typedArray.getString(R.styleable.OptionsItemView_optionContent);
        this.num = typedArray.getString(R.styleable.OptionsItemView_optionNum);
        int arrowRes = typedArray.getResourceId(R.styleable.OptionsItemView_optionArrow, 0);
        int size = typedArray.getInt(R.styleable.OptionsItemView_optionTitleSize, 0);
        this.mTitle.setTextColor(this.titleColor);
        this.mContent.setTextColor(this.contentColor);
        this.mNum.setTextColor(this.numColor);
//        this.mNum.setBackgroundResource(this.numBg);
        this.mNum.setPadding(i1 * 2, i1, i1, i1 * 2);
        if (!TextUtils.isEmpty(this.num)) {
            this.mNum.setVisibility(VISIBLE);
            this.mNum.setText(this.num);
        }
        this.mTitle.setText(this.title);
        this.mContent.setText(this.content);
        if (this.image > 0) {
            this.mIconImage.setVisibility(VISIBLE);
            this.mIconImage.setImageResource(this.image);
        }
        if (arrowRes > 0)
            this.mIconArrow.setImageResource(arrowRes);
        if (size > 0)
            this.mTitle.setTextSize(size);
        typedArray.recycle();
    }

    public void showDot() {
        this.mIconDot.setVisibility(VISIBLE);
    }

    public void hideDot() {
        this.mIconDot.setVisibility(GONE);
    }

    public void setContent(int res) {
        this.content = getContext().getResources().getString(res);
        this.mContent.setText(this.content);
    }

    public void setContent(String str) {
        this.content = str;
        this.mContent.setText(content);
    }

    public void setContentColor(int res) {
        this.mContent.setTextColor(res);
    }

    public void setContentUrl(String str) {
        if (!TextUtils.isEmpty(str))
            this.mContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }

    public void setIconResource(int res) {
        if (res > 0) {
            this.mIconImage.setImageResource(res);
            this.mIconImage.setVisibility(VISIBLE);
            return;
        }
        this.mIconImage.setVisibility(GONE);
    }

    public void setNum(String str) {
        this.num = str;
        if (!TextUtils.isEmpty(str)) {
            this.mNum.setVisibility(VISIBLE);
            this.mNum.setText(str);
            return;
        }
        this.mNum.setVisibility(GONE);
    }

    public void setTitle(String str) {
        this.title = str;
        this.mTitle.setText(title);
    }
}
