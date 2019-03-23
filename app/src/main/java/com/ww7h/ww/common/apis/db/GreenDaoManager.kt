package com.ww7h.ww.common.apis.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.greenrobot.greendao.AbstractDaoMaster
import org.greenrobot.greendao.AbstractDaoSession
import org.greenrobot.greendao.database.DatabaseOpenHelper

import java.lang.reflect.Field
import java.util.ArrayList

class GreenDaoManager private constructor() {

    private var daoMaster: AbstractDaoMaster? = null
    private var daoSession: AbstractDaoSession? = null
    private var helper: DatabaseOpenHelper? = null
    private var openCount = 0

    val dbPath: String
        get() = helper!!.writableDatabase.path

    internal object GreenDaoManagerInstance {
        val INSTANCE = GreenDaoManager()
    }

    fun initGreenDao(helper: DatabaseOpenHelper, daoMasterClass: Class<*>) {
        this.helper = helper
        try {
            this.daoMaster = daoMasterClass.newInstance() as AbstractDaoMaster
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }

    }


    /**
     * 获取数据库操作对象
     * @param needWrite 是否需要写入操作
     * @return 数据库操作对象
     */
    private fun getDB(needWrite: Boolean): SQLiteDatabase {
        openCount++
        return if (needWrite) {
            helper!!.writableDatabase
        } else {
            helper!!.readableDatabase
        }
    }

    /**
     * 关闭数据库
     * @param db 数据库对象
     */
    private fun closeDB(db: SQLiteDatabase) {
        openCount--
        if (openCount == 0) {
            db.close()
        }
    }

    /**
     * 关闭数据库
     */
    private fun closeDB() {
        openCount--
        if (openCount == 0 && daoSession != null) {
            daoSession!!.database.close()
        }
    }

    private fun getDaoSession(): AbstractDaoSession? {
        val db = helper!!.writableDatabase
        daoSession = daoMaster!!.newSession()
        return daoSession
    }


    fun <T> insertOrReplace(entity: T) {
        writeDB(0, entity, null, null, null)
    }

    fun <T> insertOrReplaceList(entityList: List<T>) {
        for (t in entityList) {
            insertOrReplace(t)
        }
    }

    fun <T> insertOrReplaceArray(entityArray: Array<T>) {
        for (t in entityArray) {
            insertOrReplace(t)
        }
    }

    fun executeSql(sql: String) {
        writeDB<Any>(1, null, null, null, sql)
    }

    fun executeSqlList(sqlList: List<String>) {
        writeDB<Any>(2, null, sqlList, null, null)
    }

    fun executeSqlArray(sqlArray: Array<String>) {
        writeDB<Any>(2, null, null, sqlArray, null)
    }

    @Synchronized
    private fun <T> writeDB(type: Int, entity: T?, sqlList: List<String>?, sqls: Array<String>?, sql: String?) {
        openCount++
        if (type == 0) {
            getDaoSession()!!.insertOrReplace(entity!!)
        } else if (type == 1) {
            getDaoSession()!!.database.execSQL(sql)
        } else if (type == 2) {
            executeSqlList(sqlList, sqls)
        }
        closeDB()
    }

    fun <T> queryOne(clazz: Class<T>, sql: String, callBack: GreenDaoCallBack.QueryCallBack<T>) {
        val tList = queryList(clazz, sql)
        if (tList.size > 0) {
            callBack.querySuccess(tList[0])
        } else {
            callBack.queryFail("没有查询到数据")
        }
    }

    fun <T> queryList(clazz: Class<T>, sql: String, callBack: GreenDaoCallBack.QueryCallBack<List<T>>) {
        callBack.querySuccess(queryList(clazz, sql))
    }

    private fun <T> queryList(clazz: Class<T>, sql: String): List<T> {
        val db = getDB(false)
        val tList = ArrayList<T>()
        val cursor: Cursor
        try {
            cursor = db.rawQuery(sql, null)
        } catch (e: Exception) {
            db.close()
            return tList
        }

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val t: T
            try {
                t = clazz.newInstance()
            } catch (e1: InstantiationException) {
                e1.printStackTrace()
                cursor.moveToNext()
                continue
            } catch (e1: IllegalAccessException) {
                e1.printStackTrace()
                continue
            }

            val names = cursor.columnNames
            for (name in names) {
                setFieldValue(t, name, cursor, clazz)
            }
            tList.add(t)
            cursor.moveToNext()
        }
        cursor.close()
        closeDB(db)
        return tList
    }

    private fun <T> setFieldValue(t: T, fieldName: String, cursor: Cursor, clazz: Class<T>) {
        var fieldName = fieldName
        val index = cursor.getColumnIndex(fieldName)
        var field: Field? = null
        try {
            fieldName = if (fieldName == "_id") "id" else fieldName
            try {
                field = clazz.getDeclaredField(fieldName)
            } catch (e: Exception) {
                val fields = clazz.declaredFields
                for (f in fields) {
                    if (f.name.toLowerCase() == fieldName.toLowerCase()) {
                        field = f
                        break
                    }
                }
                if (field == null) {
                    return
                }
            }

            field!!.isAccessible = true
            val type = field.type
            when (type.name) {
                "long" -> field.set(t, cursor.getLong(index))
                "java.lang.String" -> field.set(t, cursor.getString(index))
                "java.lang.Long" -> field.set(t, cursor.getLong(index))
                "int" -> field.set(t, cursor.getInt(index))
                "java.lang.Integer" -> field.set(t, cursor.getInt(index))
                "double" -> field.set(t, cursor.getDouble(index))
                "java.lang.Double" -> field.set(t, cursor.getDouble(index))
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }


    private fun executeSqlList(sqlList: List<String>?, sqls: Array<String>?) {
        if ((sqlList == null || sqlList.isEmpty()) && (sqls == null || sqls.size == 0)) {
            return
        }
        getDaoSession()!!.runInTx {
            if (sqlList != null) {
                for (i in sqlList.indices) {
                    getDaoSession()!!.database.execSQL(sqlList[i])
                }
            }
            if (sqls != null) {
                for (sql in sqls) {
                    getDaoSession()!!.database.execSQL(sql)
                }
            }
        }
    }

    /**
     * 判断数据库中某张表是否存在
     */
    fun sqlTableIsExist(tableName: String): Boolean {
        val count = queryCount("select count(*) as c from Sqlite_master  where type ='table' and name ='$tableName'")
        return count > 0
    }

    private fun queryCount(sql: String): Long {
        val db = getDB(false)
        val cursor = db.rawQuery(sql, null)
        var count: Long = 0
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val names = cursor.columnNames
            count = cursor.getLong(cursor.getColumnIndex(names[0]))
            cursor.moveToNext()
        }
        cursor.close()
        closeDB(db)
        return count
    }

    companion object {

        val instance: GreenDaoManager
            get() = GreenDaoManagerInstance.INSTANCE
    }

}
