package com.hhbgk.wristband.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhbgk.wristband.R;

public class Toolbar extends FrameLayout{
    private ImageButton mNavButtonView;

    private TextView mTitle;

    private TextView mOperation;
    private ImageView mOperationBackground;

    public void setNavigationOnClickListener(OnClickListener l) {
        if (mNavButtonView != null && l != null){
            mNavButtonView.setOnClickListener(l);
        }
    }

    public void setOperationOnClickListener(OnClickListener l) {
        if (mOperation != null && l != null){
            mOperation.setOnClickListener(l);
        }
    }

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true);

        mNavButtonView = (ImageButton) findViewById(R.id.navigation);
        mTitle = (TextView) findViewById(R.id.title);
        mOperation = (TextView) findViewById(R.id.operation);
        mOperationBackground = (ImageView) findViewById(R.id.operation_bg);
    }

    public void setNavigationIcon(@DrawableRes int resId){
        setNavigationIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setNavigationIcon(@Nullable Drawable icon) {
        if (icon != null){
            mNavButtonView.setImageDrawable(icon);
        }
    }

    public void setOperationText(int resId){
        setOperationText(getContext().getString(resId));
    }

    public void setOperationText(String text){
        if (mOperationBackground.getVisibility() == VISIBLE){
            mOperationBackground.setVisibility(GONE);
        }
        if (mOperation.getVisibility() == GONE){
            mOperation.setVisibility(VISIBLE);
        }
        mOperation.setText(text);
    }

    public void setOperationIcon(int resId) {
        setOperationIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setOperationIcon(@Nullable Drawable icon){
        if (icon != null){
            if (mOperationBackground.getVisibility() == GONE){
                mOperationBackground.setVisibility(VISIBLE);
            }
            if (mOperation.getVisibility() == VISIBLE){
                mOperation.setVisibility(GONE);
            }
            mOperationBackground.setImageDrawable(icon);
        }
    }

    public void setTitle(int title) {
        setTitle(getContext().getString(title));
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTopBarVisible(boolean flag) {
        setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
    }

    public void setLeftVisible(boolean flag) {
        mNavButtonView.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
    }

    public void setCenterVisible(boolean flag) {
        mTitle.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
    }

    public void setRightVisible(boolean flag) {
        mOperation.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
    }
}
