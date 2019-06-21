package com.ww7h.ww.common.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * ================================================
 * 描述：
 * 来源：     Android Studio.
 * 项目名：   Android-common
 * 包名：     com.ww7h.ww.common.utils
 * 创建时间：  2019/4/11 20:37
 *
 * @author ww  Github地址：https://github.com/ww7hcom
 * ================================================
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 短时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     **/
    @SuppressLint("ShowToast")
    public static void showShortToast(String msg) {
        if (toast == null) {
            toast = makeText(ApplicationContextUtil.Companion.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        //1、setGravity方法必须放到这里，否则会出现toast始终按照第一次显示的位置进行显示（比如第一次是在底部显示，那么即使设置setGravity在中间，也不管用）
        //2、虽然默认是在底部显示，但是，因为这个工具类实现了中间显示，所以需要还原，还原方式如下：
        toast.show();
    }

    /**
     * 短时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     **/
    @SuppressLint("ShowToast")
    public static void showShortToastCenter(String msg){
        if (toast == null) {
            toast = makeText(ApplicationContextUtil.Companion.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 短时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     **/
    @SuppressLint("ShowToast")
    public static void showShortToastTop(String msg){
        if (toast == null) {
            toast = makeText(ApplicationContextUtil.Companion.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     **/
    @SuppressLint("ShowToast")
    public static void showLongToast(String msg) {
        if (toast == null) {
            toast = makeText(ApplicationContextUtil.Companion.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     **/
    @SuppressLint("ShowToast")
    public static void showLongToastCenter(String msg){
        if (toast == null) {
            toast = makeText(ApplicationContextUtil.Companion.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     **/
    @SuppressLint("ShowToast")
    public static void showLongToastTop(String msg){
        if (toast == null) {
            toast = makeText(ApplicationContextUtil.Companion.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

}
