//
// Created by w w on 2019/1/22.
//
#include <jni.h>

#ifndef COMMON_JNILISTENER_H
#define COMMON_JNILISTENER_H

#endif //COMMON_JNILISTENER_H


extern "C" {

/**
 * fieldName 参数名
 * sig Z:boolean,B:byte,C:char,S:short,
 *     I:int,L:long,F:float,D:double,
 *     V:void,L用/分割完整包名加类名:object,
 *     [签名:Array,(参数1类型签名，参数2类型签名)返回值类型签名:Method
 * @return jmethodID
 */
//jmethodID getJMethodID (JNIEnv *env, jobject jObj, const char* methodName, const char* sig);

/**
 * fieldName 参数名
 * sig Z:boolean,B:byte,C:char,S:short,
 *     I:int,L:long,F:float,D:double,
 *     V:void,L用/分割完整包名加类名:object,
 *     [签名:Array,(参数1类型签名，参数2类型签名)返回值类型签名:Method
 * @return jfieldID
 */
//jfieldID getJFieldID() (JNIEnv *env, jobject jObj, const char* fieldName, const char* sig);

}