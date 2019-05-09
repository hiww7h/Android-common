package com.ww7h.ww.common.popupwindows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * ================================================
 * 描述：
 * 来源：     Android Studio.
 * 项目名：   Android-common
 * 包名：     com.ww7h.ww.common.popupwindows
 * 创建时间：  2019/5/9 20:03
 *
 * @author ww  Github地址：https://github.com/ww7hcom
 * ================================================
 */
public abstract class BasePopupWindow extends PopupWindow {

    private Context mContext;

    public BasePopupWindow(Context context) {
        mContext = context;
        onCreateView();
    }

    protected abstract int getResourceId();

    protected abstract void initView();

    protected abstract void initAction();

    private void onCreateView() {
        setContentView(getResourceView());

        initView();
        initAction();
    }

    protected View getResourceView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(getResourceId(), null);
    }

    protected <T extends View> T findViewById(int viewId) {

        return getContentView().findViewById(viewId);
    }


}
