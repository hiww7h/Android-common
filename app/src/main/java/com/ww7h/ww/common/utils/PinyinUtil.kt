package com.ww7h.ww.common.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination


/**
 * Created by ww on 2018/7/12.
 */
class PinyinUtil {

    /**
     * 将文字转为汉语拼音
     * @param chineselanguage 要转成拼音的中文
     */
    fun toHanyuPinyin(ChineseLanguage: String): String {
        val clChars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
        var hanYuPinYin = ""
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE// 输出拼音全部小写
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE// 不带声调
        defaultFormat.vCharType = HanyuPinyinVCharType.WITH_V
        try {
            for (i in clChars.indices) {
                if (clChars[i].toString().matches("[\u4e00-\u9fa5]+".toRegex())) {// 如果字符是中文,则将中文转为汉语拼音
                    hanYuPinYin += PinyinHelper.toHanyuPinyinStringArray(clChars[i], defaultFormat)[0]
                } else {// 如果字符不是中文,则不转换
                    hanYuPinYin += clChars[i]
                }
            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            println("字符不能转成汉语拼音")
        }

        return hanYuPinYin
    }


    fun getFirstLettersUp(ChineseLanguage: String): String {
        return getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE)
    }

    fun getFirstLettersLo(ChineseLanguage: String): String {
        return getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE)
    }

    private fun getFirstLetters(ChineseLanguage: String, caseType: HanyuPinyinCaseType): String {
        val clChars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
        var hanYuPinYin = ""
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = caseType// 输出拼音全部大写
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE// 不带声调
        try {
            for (i in clChars.indices) {
                val str = clChars[i].toString()
                when {
                    str.matches("[\u4e00-\u9fa5]+".toRegex()) -> // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                        hanYuPinYin += PinyinHelper.toHanyuPinyinStringArray(clChars[i], defaultFormat)[0].substring(0, 1)
                    str.matches("[0-9]+".toRegex()) -> // 如果字符是数字,取数字
                        hanYuPinYin += clChars[i]
                    str.matches("[a-zA-Z]+".toRegex()) -> // 如果字符是字母,取字母
                        hanYuPinYin += clChars[i]
                    else -> // 否则不转换
                        hanYuPinYin += clChars[i]//如果是标点符号的话，带着
                }
            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            println("字符不能转成汉语拼音")
        }

        return hanYuPinYin
    }

    fun getPinyinString(ChineseLanguage: String): String {
        val clChars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
        var hanYuPinYin = ""
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE// 输出拼音全部大写
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE// 不带声调
        try {
            for (i in clChars.indices) {
                val str = clChars[i].toString()
                when {
                    str.matches("[\u4e00-\u9fa5]+".toRegex()) -> // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                        hanYuPinYin += PinyinHelper.toHanyuPinyinStringArray(
                            clChars[i], defaultFormat)[0]
                    str.matches("[0-9]+".toRegex()) -> // 如果字符是数字,取数字
                        hanYuPinYin += clChars[i]
                    str.matches("[a-zA-Z]+".toRegex()) -> // 如果字符是字母,取字母

                        hanYuPinYin += clChars[i]
                    else -> {// 否则不转换
                    }
                }
            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            println("字符不能转成汉语拼音")
        }

        return hanYuPinYin
    }

    /**
     * 取第一个汉字的第一个字符
     * @Title: getFirstLetter
     * @Description: TODO
     * @return String
     * @throws
     */
    fun getFirstLetter(ChineseLanguage: String): String {
        val clChars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
        var hanYuPinYin = ""
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.UPPERCASE// 输出拼音全部大写
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE// 不带声调
        try {
            val str = clChars[0].toString()
            when {
                str.matches("[\u4e00-\u9fa5]+".toRegex()) -> // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanYuPinYin = PinyinHelper.toHanyuPinyinStringArray(
                        clChars[0], defaultFormat)[0].substring(0, 1)
                str.matches("[0-9]+".toRegex()) -> // 如果字符是数字,取数字
                    hanYuPinYin += clChars[0]
                str.matches("[a-zA-Z]+".toRegex()) -> // 如果字符是字母,取字母

                    hanYuPinYin += clChars[0]
                else -> {// 否则不转换

                }
            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            println("字符不能转成汉语拼音")
        }
        return hanYuPinYin
    }

}