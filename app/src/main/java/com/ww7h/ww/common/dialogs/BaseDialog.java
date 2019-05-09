package com.ww7h.ww.common.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * ================================================
 * 描述：
 * 来源：     Android Studio.
 * 项目名：   Android-common
 * 包名：     com.ww7h.ww.common.dialogs
 * 创建时间：  2019/5/9 19:56
 *
 * @author ww  Github地址：https://github.com/ww7hcom
 * ================================================
 */
public abstract class BaseDialog extends AlertDialog {

    protected BaseDialog(Context context) {
        super(context);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected abstract int getResourceId();

    protected abstract void initView();

    protected abstract void initAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceView());
        initView();
        initAction();
    }

    protected View getResourceView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return inflater.inflate(getResourceId(), null);
    }

}
