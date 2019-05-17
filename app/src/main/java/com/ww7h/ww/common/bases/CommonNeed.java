package com.ww7h.ww.common.bases;

/**
 * ================================================
 * 描述：
 * 来源：     Android Studio.
 * 项目名：   Android-common
 * 包名：     com.ww7h.ww.common.bases
 * 创建时间：  2019/5/17 10:26
 *
 * @author ww  Github地址：https://github.com/ww7hcom
 * ================================================
 */
public interface CommonNeed {

    interface ClassCommon<T> {
        String getTag();
        T getInstance();
    }

}
