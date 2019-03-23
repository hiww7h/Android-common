package com.ww7h.ww.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ww on 2018/7/10.
 */
class DateUtil {
    companion object {
        fun getNowDateWithFormat(format: String): String {
            val simpleDateFormat = SimpleDateFormat(format)
            val date = Date()
            return simpleDateFormat.format(date)
        }

        fun getLastDayDate(dateStr: String): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            var beginDate: Date? = null
            try {
                beginDate = simpleDateFormat.parse(dateStr)
            } catch (e: ParseException) {
                beginDate = Date()
                e.printStackTrace()
            }

            val date = Calendar.getInstance()
            date.time = beginDate
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 1)
            return simpleDateFormat.format(date.time)
        }

        fun strDateToLong(dateStr: String): Long {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            var beginDate: Date? = null
            try {
                beginDate = simpleDateFormat.parse(dateStr)
            } catch (e: ParseException) {
                beginDate = Date()
                e.printStackTrace()
            }

            return beginDate!!.time
        }

        fun longToDateStr(datel: Long, format: String): String {
            val date = Date()
            date.time = datel
            val simpleDateFormat = SimpleDateFormat(format)
            return simpleDateFormat.format(date)
        }
    }
}