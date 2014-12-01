LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := FlowControl
LOCAL_SRC_FILES := FlowControl.cpp

include $(BUILD_SHARED_LIBRARY)
