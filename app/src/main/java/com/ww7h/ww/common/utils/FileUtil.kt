package com.ww7h.ww.common.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

object FileUtil {

    /**
     * 通过Uri获取文件在本地存储**的**真实路径
     */
    fun getRealPathFromURI(contentUri: Uri, contentResolver: ContentResolver): String? {
        val poj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor : Cursor? = contentResolver.query(contentUri, poj, null, null, null)
        return if (cursor != null && cursor.moveToNext()) {
            cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
        }else{
            cursor?.close()
            contentUri.path
        }
    }

    /**
     * 复制asset文件到指定目录
     * @param content  json文件内容
     * @param filePath  文件在Sdcard下的目录
     * @param fileName  文件名称
     */
    fun createTextFile(content: String, filePath: String, fileName: String): String {
        // 拼接文件完整路径
        var fullPath = Environment.getExternalStorageDirectory().absolutePath+filePath + File.separator + fileName + ".json"

        // 生成json格式文件
        try {
            // 保证创建一个新文件
            val file = File(fullPath)
            if (!file.parentFile.exists()) { // 如果父目录不存在，创建父目录
                file.parentFile.mkdirs()
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete()
            }
            file.createNewFile()
            // 将格式化后的字符串写入文件
            val write = OutputStreamWriter(FileOutputStream(file), "UTF-8")
            write.write(content)
            write.flush()
            write.close()
        } catch (e: Exception) {
            fullPath = ""
            e.printStackTrace()
        }
        return fullPath
    }

    /**
     * 复制asset文件到指定目录
     * @param oldPath  asset下的路径
     * @param newPath  SD卡下保存路径
     */
    fun copyAssets(context: Context, oldPath: String, newPath: String) {
        try {
            val fileNames = context.assets.list(oldPath)!!// 获取assets目录下的所有文件及目录名
            if (fileNames.isNotEmpty()) {// 如果是目录
                val file = File(newPath)
                file.mkdirs()
                for (fileName in fileNames) {
                    copyAssets(context, "$oldPath/$fileName", "$newPath/$fileName")
                }
            } else {// 如果是文件
                val inputStream = context.assets.open(oldPath)
                val fos = FileOutputStream(File(newPath))
                val buffer = ByteArray(1024)
                var byteCount: Int
                do {
                    byteCount = inputStream.read(buffer)
                    if (byteCount != -1) {
                        fos.write(buffer, 0, byteCount)// 将读取的输入流写入到输出流
                    } else {
                        break
                    }
                } while (true)
                fos.flush()
                inputStream.close()
                fos.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}