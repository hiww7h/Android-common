package com.ww7h.ww.common.utils

import java.util.HashMap

object Constant {

    val permissionsTips: Map<String, String> = object : HashMap<String, String>() {
        init {
            put("ACCESS_WIFI_STATE", "未允许程序获取当前WiFi接入的状态以及WLAN热点的信息")
            put("ACCESS_NETWORK_STATE", "未允许程序获取网络信息状态，如当前的网络连接是否有效")
            put("ACCESS_SURFACE_FLINGER", "未允许屏幕截图")
            put("ACCESS_MOCK_LOCATION", "未允许程序获取模拟定位信息")
            put("ACCESS_FINE_LOCATION", "未允许程序通过GPS芯片接收卫星的定位信息")
            put("ACCESS_COARSE_LOCATION", "未允许程序通过WiFi或移动基站的方式获取用户错略的经纬度信息")
            put("BROADCAST_SMS", "未允许程序当收到短信时触发一个广播")
            put("CALL_PHONE", "未允许程序从非系统拨号器里拨打电话")
            put("CAMERA", "未允许程序访问摄像头进行拍照")
            put("CHANGE_CONFIGURATION", "未允许当前应用改变配置")
            put("CHANGE_NETWORK_STATE", "未允许程序改变网络状态")
            put("CHANGE_WIFI_STATE", "未允许程序改变WiFi状态")
            put("CLEAR_APP_CACHE", "未允许程序清除应用缓存")
            put("CLEAR_APP_USER_DATA", "未允许程序清除用户数据")
            put("CONTROL_LOCATION_UPDATES", "未允许程序获得移动网络定位信息改变")
            put("DELETE_CACHE_FILES", "未允许程序删除缓存文件")
            put("DELETE_PACKAGES", "未允许程序删除应用")
            put("DEVICE_POWER", "未允许程序访问底层电源管理")
            put("GET_PACKAGE_SIZE", "未允许程序获取应用的文件大小")
            put("GET_TASKS", "未允许程序获取任务信息")
            put("INSTALL_PACKAGES", "未允许程序安装应用")
            put("INTERNET", "未允许程序访问网络连接，可能产生GPRS流量")
            put("LOCATION_HARDWARE", "未允许一个应用程序中使用定位功能的硬件，不使用第三方应用")
        }

    }


}

