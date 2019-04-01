package com.ww7h.ww.common.bases.view.packer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.ww7h.ww.common.R;

import java.lang.reflect.Field;

/**
 * Created by: Android Studio.
 * Project Nam: Android-common
 * PackageName: com.ww7h.ww.common.bases.view.packer
 * DateTime: 2019/3/28 16:53
 *
 * info: 自定义TextNumberPicker 新增了
 * lineColor： 用来设置分割线颜色
 * textColor： 用来设置文字颜色
 * textSize：  用来设置字号大小
 * descendantFocusability：用来设置是否允许编辑
 *
 * @author ww
 */
public class TextNumberPicker extends NumberPicker {

    private int mTextColor;
    private float mTextSize;

    public TextNumberPicker(Context context) {
        super(context);
    }

    public TextNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextNumberPicker, defStyleAttr, 0);
        int lineColor = a.getColor(R.styleable.TextNumberPicker_lineColor, -1);
        mTextColor = a.getColor(R.styleable.TextNumberPicker_textColor, Color.BLACK);
        mTextSize = a.getDimension(R.styleable.TextNumberPicker_textSize, 15);
        setDescendantFocusability(a.getInteger(R.styleable.TextNumberPicker_descendantFocusability, NumberPicker.FOCUS_BLOCK_DESCENDANTS));
        if (lineColor != -1) {
            setNumberPickerDividerColor(lineColor);
        }

    }

    public TextNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    private void updateView(View view) {
        if (view instanceof EditText) {
            //设置文字的颜色和大小
            ((EditText) view).setTextColor(mTextColor);
            ((EditText) view).setTextSize(mTextSize);
        }
    }

    /**
     * 设置分割线颜色
     * @param color 需要设置的颜色
     */
    private void setNumberPickerDividerColor(int color) {
        Field[] pickerFields = this.getClass().getDeclaredFields();
        for (Field pf : pickerFields) {
            if ("mSelectionDivider".equals(pf.getName())) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(this, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
