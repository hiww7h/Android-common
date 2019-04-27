package com.ww7h.ww.common.bases.view.radiobutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;


/**
 * ================================================
 * 描述：
 * 来源：     Android Studio.
 * 项目名：   PurchasingTreasure
 * 包名：     com.ww7h.purchasing.main.view
 * 创建时间：  2019/4/24 15:46
 *
 * @author ww  Github地址：https://github.com/ww7hcom
 * ================================================
 */
public class RemindRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private float remindWidth;
    private float remindHeight;
    private float remindTextSize;
    private int remindResourceId;
    private int remindTextColor;
    private int remindTextBgColor;
    private int remindNumber = 0;
    private float remindMarginRight;

    public RemindRadioButton(Context context) {
        super(context);
    }

    public RemindRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, com.ww7h.ww.common.R.styleable.RemindRadioButton, 0, 0);
        remindMarginRight = a.getDimension(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindMarginRight, 0);
        remindWidth = a.getDimension(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindTextWidth, 0);
        remindHeight = a.getDimension(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindTextHeight, 0);
        remindTextSize = a.getDimension(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindTextSize, 0);
        remindResourceId = a.getResourceId(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindTextBackground, 0);
        remindTextColor = a.getColor(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindTextColor, Color.WHITE);
        remindTextBgColor = a.getColor(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindTextBgColor, Color.RED);
        remindNumber = a.getInt(com.ww7h.ww.common.R.styleable.RemindRadioButton_remindNumber, 0);
    }

    public RemindRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (remindNumber > 0) {

            Paint paint = new Paint();
            if (remindResourceId == 0) {
                paint.setColor(remindTextBgColor);
            }

            Path path = new Path();

            float minX = getWidth() - remindWidth - remindHeight / 2 - remindMarginRight;
            float maxX = getWidth() - remindHeight / 2 - remindMarginRight;

            // 绘制左侧圆形
            path.addCircle(maxX,
                    remindHeight / 2, remindHeight / 2, Path.Direction.CW);

            path.close();

            canvas.drawPath(path, paint);
            int count = 0;
            // 当提醒消息数超过一位数时
            if (remindNumber > 9) {
                count = remindNumber > 99 ? 3 : 2;
                // 绘制右侧半圆
                path.addCircle(minX,
                        remindHeight / 2, remindHeight / 2, Path.Direction.CW);
                path.close();

                canvas.drawPath(path, paint);

                path = new Path();

                // 绘制提醒消息显示位置矩形
                path.moveTo(minX, remindHeight);
                path.lineTo(minX, 0);
                path.lineTo(maxX, 0);
                path.lineTo(maxX, remindHeight);
                path.close();

                canvas.drawPath(path, paint);
            } else {
                // 当消息数为一位数时
                count = 1;
                minX = getWidth() - remindHeight - remindMarginRight;
                maxX = getWidth() - remindMarginRight;
                path = new Path();

                // 绘制消息显示矩形
                path.moveTo(minX, remindHeight);
                path.lineTo(minX, 0);
                path.lineTo(maxX, 0);
                path.lineTo(maxX, remindHeight);
                path.close();

                paint.setColor(Color.TRANSPARENT);
                canvas.drawPath(path, paint);
            }

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(remindTextColor);
            paint.setTextSize(remindTextSize);

            // 数字字号大小约等于两倍的数字宽度，remindTextSize / 2 * count 当前数的实际文本宽度
            // (remindWidth - remindTextSize / 2 * count )/2 两侧多余的容器宽度
            canvas.drawTextOnPath(String.valueOf(remindNumber), path,
                    remindHeight + Math.abs((remindWidth - remindTextSize / 2 * count )/2),
                    remindTextSize, paint);
        }
    }

    public void setRemindNumber(int number) {
        this.remindNumber = number;
        invalidate();
    }

    public void setRemindMarginRight(float remindMarginRight) {
        this.remindMarginRight = remindMarginRight;
        invalidate();
    }

    public void setRemindWidth(float remindWidth) {
        this.remindWidth = remindWidth;
        invalidate();
    }

    public void setRemindHeight(float remindHeight) {
        this.remindHeight = remindHeight;
        invalidate();
    }
}
