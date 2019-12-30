package com.lyichao.customewidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginButton extends RelativeLayout {

    static final int DEFAULT_COLOR = android.R.color.white;
    public static final int IN_FROM_RIGHT = 0;
    public static final int IN_FROM_LEFT = 1;

    private int mDefaultTextSize;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private String mLoadingText;
    private String mButtonText;
    private float mTextSize;
    private int mTextColor;
    private boolean mIsLoadingShowing;
    private Typeface mTypeface;
    private Animation inRight;
    private Animation inLeft;
    private int mCurrentInDirection;
    private boolean mTextSwitcherReady;

    public LoginButton(Context context) {
        super(context);
        init(context,null);
    }


    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        //设定默认textSize大小
        mDefaultTextSize = getResources().getDimensionPixelSize(R.dimen.text_default_size);
        //设定默认progress隐藏
        mIsLoadingShowing = false;
        //加载组合控件布局
        LayoutInflater.from(getContext()).inflate(R.layout.view_loading_button, this, true);

        //绑定mProgressBar和mTextView
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTextView = (TextView) findViewById(R.id.text);

        //因为通过xml赋值，所以需要定义attrs.xml组合控件属性集合
        if (attrs != null) {
            //这里说一下obtainStyledAttributes()方法中的俩个参数，第一个就是我们构造函数的attrs，第二个就是我们刚刚定义的属性集合的名字。
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.LoadingButton,0, 0);
            try {
                //赋值给未登陆时的登陆按钮文字大小
                float textSize = a.getDimensionPixelSize(R.styleable.LoadingButton_textSize, mDefaultTextSize);
                setTextSize(textSize);

                //赋值给未登陆时的登陆按钮文字
                String text = a.getString(R.styleable.LoadingButton_text);
                setText(text);

                //赋值给登陆中的登陆按钮文字
                mLoadingText = a.getString(R.styleable.LoadingButton_loadingText);

                if (mLoadingText == null) {
                    mLoadingText = getContext().getString(R.string.default_loading);
                }

                //赋值给progress颜色
                int progressColor = a.getColor(R.styleable.LoadingButton_progressColor, DEFAULT_COLOR);
                setProgressColor(progressColor);
                //赋值给登陆中的登陆按钮文字颜色
                int textColor = a.getColor(R.styleable.LoadingButton_textColor, DEFAULT_COLOR);

                setTextColor(textColor);

            } finally {
                a.recycle();
            }
        } else {
            //attrs为空时，设置默认属性
            int white = getResources().getColor(DEFAULT_COLOR);
            mLoadingText = getContext().getString(R.string.loading);
            setProgressColor(white);
            setTextColor(white);
            setTextSize(mDefaultTextSize);
            setText("登陆");
        }


    }


    public float getTextSize() {
        return mTextSize;
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public void setProgressColor(int colorRes) {
        mProgressBar.getIndeterminateDrawable()
                .mutate()
                .setColorFilter(colorRes, PorterDuff.Mode.SRC_ATOP);
    }

    public void setTypeface(@NonNull Typeface typeface) {
        this.mTypeface = typeface;
    }

    public void setAnimationInDirection(int inDirection) {
        mCurrentInDirection = inDirection;
    }

    public void setText(String text) {
        if (text != null) {
            mButtonText = text;
            mTextView.setText(text);
        }
    }

    public void setLoadingText(String text) {
        if (text != null) {
            mLoadingText = text;
        }
    }



    public void showLoading() {
        if (!mIsLoadingShowing) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setText(mLoadingText);
            mIsLoadingShowing = true;
            setEnabled(false);
        }
    }

    public void showButtonText() {
        if (mIsLoadingShowing) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mTextView.setText(mButtonText);
            mIsLoadingShowing = false;
            setEnabled(true);
        }
    }

    public boolean isLoadingShowing() {
        return mIsLoadingShowing;
    }



    private void setTextSize(float size) {
        mTextSize = size;
    }

    private void setTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextView.setTextColor(textColor);
    }
}
