package com.ww7h.ww.common.bases.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.ww7h.ww.common.R
import com.ww7h.ww.common.utils.Constant
import kotlinx.android.synthetic.main.toolbar.*

import java.util.ArrayList

abstract class BaseActivity<T : BaseActivity<T>> : AppCompatActivity() {

    protected var activity: T? = null

    private var TAG: String? = null

    private val PERMISSION_REQUEST_CODE = 0x1001


    /**
     * 获取当前界面需要申请的权限，默认无权限，子类需重写该方法
     */
    protected open fun needPermission(): Array<String>? {
        return null
    }

    /**
     * 返回界面显示视图
     */
    protected abstract val contentView: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        activity = this as T

        TAG = activity?.javaClass?.name
        setSupportActionBar(toolbar)
        if (intent.getStringExtra("title") != null) {
            title = intent.getStringExtra("title")
        }

        getPermission()
    }

    fun showLeftWithIcon(iconId:Int) {
        toolbar?.setNavigationIcon(iconId)
        toolbar?.setNavigationOnClickListener { onLeftClick() }
    }

    override fun setTitle(title: CharSequence) {
        super.setTitle("")
        title_tv?.text = title
    }


    /**
     * 检查当前界面的权限是否全部申请通过
     * 若有未通过权限，发起申请
     */
    private fun getPermission() {

        if (needPermission() != null) {
            val permissions = checkPermissionAllGranted(needPermission()!!)

            if (permissions.isEmpty()) {
                init()
                return
            } else {
                /**
                 * 第 2 步: 请求权限
                 */
                // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
                ActivityCompat.requestPermissions(
                    this,
                    permissions, PERMISSION_REQUEST_CODE
                )
            }
        } else {
            init()
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     * 并返回所有未申请的权限
     */
    private fun checkPermissionAllGranted(permissions: Array<String>): Array<String> {
        val noPermissionArray = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                noPermissionArray.add(permission)
            }
        }
        return noPermissionArray.toTypedArray()
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            val noPermissions = checkPermissionAllGranted(
                permissions
            )

            if (noPermissions.isEmpty()) {
                // 如果所有的权限都授予了, 则执行备份代码
                initView()
                initAction()

            } else {
                // 如果有权限未被授予，执行该函数
                refusePermission(noPermissions)
            }
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    private fun openAppDetails(noPermissions: Array<String>) {
        val builder = AlertDialog.Builder(this)
        val message = StringBuilder()
        for (p in noPermissions) {
            if (Constant.permissionsTips.containsKey(p)) {
                message.append(Constant.permissionsTips[p]).append("\n")
            }
        }
        builder.setMessage(message)
        builder.setPositiveButton("去手动授权") { dialog, which ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:\$packageName")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(intent)
        }

        builder.setNegativeButton("取消", null)
        builder.show()
    }

    /**
     * 初始化
     */
    protected fun init() {
        initView()
        initAction()
    }

    /**
     * 初始化视图
     */
    protected abstract fun initView()

    /**
     * 初始化事件
     */
    protected abstract fun initAction()

    /**
     * 导航栏左侧按钮点击事件
     */
    protected open fun onLeftClick() {
        finish()
    }

    /**
     * 隐藏软键盘
     */
    protected open fun hideSoftInput() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 有权限被拒绝时调用，该方法默认调用
     * 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
     * 子类可重写该方法，根据拒绝权限的优先级判断下部操作
     */
    protected open fun refusePermission(permissions: Array<String>) {
        openAppDetails(permissions)
    }
}
