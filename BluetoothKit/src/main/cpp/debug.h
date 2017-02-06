//
// Created by bob on 17-1-16.
//

#ifndef BLUETOOTHKIT_DEBUG_H
#define BLUETOOTHKIT_DEBUG_H

#ifdef __ANDROID__

#include <android/log.h>
#include <jni.h>

#define TAG "bt_band"
#define logd(...)  ((void)__android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__))
#define logi(...)  ((void)__android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__))
#define logw(...)  ((void)__android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__))
#define loge(...)  ((void)__android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__))

#else

#define logi(...)
#define logw(...)
#define loge(...)

#endif

#endif //BLUETOOTHKIT_DEBUG_H
