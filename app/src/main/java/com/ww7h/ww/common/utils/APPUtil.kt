package com.ww7h.ww.common.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateEncodingException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

object APPUtil {

    fun getAppList(context: Context): List<PackageInfo> {
        val pm = context.packageManager
        return pm.getInstalledPackages(PackageManager.GET_SIGNATURES)
    }

    //这个是获取SHA1的方法
    fun getCertificateSHA1Fingerprint(packageInfo: PackageInfo): String? {

        //签名信息
        val signatures = packageInfo.signatures
        val cert = signatures[0].toByteArray()
        //将签名转换为字节数组流
        val input = ByteArrayInputStream(cert)
        //证书工厂类，这个类实现了出厂合格证算法的功能
        var cf: CertificateFactory? = null
        try {
            cf = CertificateFactory.getInstance("X509")
        } catch (e: CertificateException) {
            e.printStackTrace()
        }

        //X509证书。X.509是一种很通用的证书格式
        var c: X509Certificate? = null
        try {
            c = cf!!.generateCertificate(input) as X509Certificate
        } catch (e: CertificateException) {
            e.printStackTrace()
        }

        var hexString: String? = null
        try {
            //加密算法的类，这里的參数能够使MD4,MD5等加密算法
            val md = MessageDigest.getInstance("SHA1")
            //获得公钥
            assert(c != null)
            val publicKey = md.digest(c!!.encoded)
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey)
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        } catch (e: CertificateEncodingException) {
            e.printStackTrace()
        }

        return hexString
    }

    //这里是将获取到得编码进行16进制转换
    private fun byte2HexFormatted(arr: ByteArray): String {
        val str = StringBuilder(arr.size * 2)
        for (i in arr.indices) {
            var h = Integer.toHexString(arr[i].toInt())
            val l = h.length
            if (l == 1)
                h = "0$h"
            if (l > 2)
                h = h.substring(l - 2, l)
            str.append(h.toUpperCase())
            if (i < arr.size - 1)
                str.append(':')
        }
        return str.toString()
    }
}
