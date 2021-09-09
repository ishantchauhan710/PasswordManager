LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := key-jni
LOCAL_SRC_FILES := key-jni.c
include $(BUILD_SHARED_LIBRARY)
