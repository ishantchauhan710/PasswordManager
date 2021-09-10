 # include <jni.h>

JNIEXPORT jstring JNICALL


Java_com_ishant_passwordmanager_security_EncryptionDecryption_getKey(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "ishant chauhan");
}

