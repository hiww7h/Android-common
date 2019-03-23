#include <jni.h>
#include <string>
#include "encryption.h"
#include "JNIListener.h"


//extern "C" JNIEXPORT jstring JNICALL
//Java_com_ww7h_ww_common_natives_Encryption_encryptionWords(
//        JNIEnv *env,
//        jobject jObj ,jstring original) {
//    std::string cOriginal = env->GetStringUTFChars(original,0);
//
//    jmethodID callPhone = getJMethodID(env, jObj, "callPhone", "(Ljava/lang/String)V");
//    (*env).CallVoidMethod(jObj, callPhone ,"13923192318");
//
//    return env->NewStringUTF(encryptionWords(cOriginal).c_str());
//}
