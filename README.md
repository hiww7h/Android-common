# Android-common 公共库封装，包括一些基础功能

## 项目目录

1、apis 主要包括一些系统和第三方api的使用的一个封装. 
https://github.com/ww7hcom/Android-common/tree/master/app/src/main/java/com/ww7h/ww/common/apis
2、bases 主要是一些常用基类的封装  
https://github.com/ww7hcom/Android-common/tree/master/app/src/main/java/com/ww7h/ww/common/bases
3、listeners 主要是一些常用的事件监听  
https://github.com/ww7hcom/Android-common/tree/master/app/src/main/java/com/ww7h/ww/common/listeners
4、natives 主要是一些native方法的提供  
https://github.com/ww7hcom/Android-common/tree/master/app/src/main/java/com/ww7h/ww/common/natives
5、popupwindows 主要是一些常用样式的弹框的封装  
https://github.com/ww7hcom/Android-common/tree/master/app/src/main/java/com/ww7h/ww/common/popupwindows
6、utils 常用工具类的封装  
https://github.com/ww7hcom/Android-common/tree/master/app/src/main/java/com/ww7h/ww/common/utils

## 简单的使用

### apis的使用
#### camera中主要包含camera和camera2api的使用，使用方法一致
1、创建根据需要用到的api创建CameraNeed的实例  

    CameraNeed need = new Camera2Api();  
    CameraNeed need = new Camera1Api();  
    
2、初始化 

    need.init(context, surfaceView, callBack);
    
3、启动相机，暂停相机

    need.openCamera(index);
    need.closeCamera();

#### db目前主要用到的框架是greenDao
1、在application中初始化GreenDaoManager

    GreenDaoManager.instance.initGreenDao(helper, daoMasterClass);
    
2、插入记录，建议多条插入是转存为sql执行

    GreenDaoManager.instance.insertOrReplace(obj); // 单条插入
    GreenDaoManager.instance.insertOrReplaceList(list); // list插入
    GreenDaoManager.instance.insertOrReplaceArray(array); // 数组插入
    
3、执行sql

    GreenDaoManager.instance.executeSql(sql); // 执行单条sql
    GreenDaoManager.instance.executeSqlList(list); // 执行list
    GreenDaoManager.instance.executeSqlArray(array); // 执行数组
    
4、查询记录

    GreenDaoManager.instance.queryOne(clazz, sql, callBack); // 查询单条结果
    GreenDaoManager.instance.queryList(clazz, sql, callBack); // 查询多条结果，list返回
    
5、删除记录，标记删除建议使用insertOrReplace，多条删除，建议转存为sql执行

    GreenDaoManager.instance.deleteOne(obj); // 删除单条记录
    GreenDaoManager.instance.deleteList(list); // 删除list
    GreenDaoManager.instance.deleteArray(array); // 删除数组
    
6、回调

    调用上述方法部分需要实现GreenDaoCallBack回调
    

#### glides的使用
主要调用提供的方法完成图片的呈现即可

#### http的使用
##### okGo的使用
##### okHttp的使用
1、get请求，实现OkHttpCallBack完成请求成功回调

    HttpOkHttp.getInstance.requestGet( url, okHttpCallBack, clazz)
    
2、post请求，目前支持json数据请求，如需其他格式，可自行添加

    HttpOkHttp.getInstance.requestPost( url, json, okHttpCallBack, clazz)

### retrofit的使用
### media的使用

## bases常用基类的使用
### activity 的使用，主要是其中方法的用途
1、视图需引入toolbar

    <include layout="@layout/toolbar"/>
    
2、页面展示，首先检测当前页面是否有需要动态申请的权限，如果有

    protected String[] needPermission(){
        return new String[]{""}; // 此处返回需要申请的权限
    }
    
3、若用户拒绝权限申请,下面的方法会告知用户拒绝的权限有哪些，可以根据实际情况处理后面的操作

    protected void refusePermission(String[] permissions) {
    
    }











