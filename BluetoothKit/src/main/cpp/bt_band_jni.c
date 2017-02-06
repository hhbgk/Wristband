#include <stdlib.h>
#include <android/log.h>
#include <jni.h>
#include <assert.h>
#include "debug.h"
#include "bt_packet.h"
#include "bt_band_jni.h"

#define JNI_CLASS "com/demuxer/BtBandManager"
#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))

static JavaVM *gJVM = NULL;
static jobject gObj = NULL;
static packet_hdr_t g_data = {0};
static queue_t *q_value;//for L2 payload of values
static packet_hdr_t gRcv_data_hdr;
static jmethodID cb_native_callback_id;
static jmethodID cb_id_ack_response;

//static size_t curr_len;
//static uint8_t packet_buf[512];
typedef struct {
    uint8_t start;
    uint8_t end;
    packet_hdr_t packetHdr;
    size_t curr_pos;
    uint8_t payload_buf[512];
}app_context_t;
static app_context_t app_ctx;

static void destroy_queue_element(void *element);
static int parse_key_value(uint8_t , app_context_t *);

static void jni_init(JNIEnv *env, jobject thiz)
{
    logi("%s", __func__);
    (*env)->GetJavaVM(env, &gJVM);
    gObj = (*env)->NewGlobalRef(env, thiz);
    jclass clazz = (*env)->GetObjectClass(env, thiz);
    if(clazz == NULL)
    {
        (*env)->ThrowNew(env, "java/lang/NullPointerException", "Unable to find exception class");
    }

    memset(&app_ctx, 0, sizeof(app_context_t));

    cb_native_callback_id = (*env)->GetMethodID(env, clazz, "onNativeCallback", "(III)V");
    cb_id_ack_response = (*env)->GetMethodID(env, clazz, "onAckResponse", "(Z)V");
}

static void on_callback(int cmd, int key, int state)
{
    JNIEnv *env = NULL;

    if ((*gJVM)->GetEnv(gJVM, (void *) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
        // detached
        (*env)->CallVoidMethod(env, gObj, cb_native_callback_id, cmd, key, state);
        (*gJVM)->DetachCurrentThread(gJVM);
    } else {
        // attached
        assert(env != NULL);
        (*env)->CallVoidMethod(env, gObj, cb_native_callback_id, cmd, key, state);
    }
}

static void on_ack_response(jboolean isError)
{
    JNIEnv *env = NULL;

    if ((*gJVM)->GetEnv(gJVM, (void *) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
        // detached
        (*env)->CallVoidMethod(env, gObj, cb_id_ack_response, isError);
        (*gJVM)->DetachCurrentThread(gJVM);
    } else {
        // attached
        assert(env != NULL);
        (*env)->CallVoidMethod(env, gObj, cb_id_ack_response, isError);
    }
}
static jbyteArray jni_bt_wrap_data(JNIEnv *env, jobject thiz, jobject jPayloadInfo, jint version)
{
    logi("%s", __func__);
    uint16_t payload_len = 0x0;
    int total_size = 8;//L1 header
    int sparse_size = 0;//key value size
    uint32_t position = 0;
    jmethodID m_get = NULL;
    jmethodID m_keyAt = NULL;
    int cmd_id;
    uint8_t *payload_buf = NULL;
    packet_hdr_t *packet = &g_data;
    assert(packet != NULL);

    memset(packet, 0, sizeof(packet_hdr_t));

    bd_bt_set_magic(packet, 0xab);
    bd_bt_set_err_flag(packet, 0x0);
    bd_bt_set_ack_flag(packet, 0x0);
    bd_bt_set_version(packet, version);
    bd_bt_update_seq_id(packet);

    jclass cls_payloadInfo = (*env)->GetObjectClass(env, jPayloadInfo);
    //method in class PayloadInfo
    jmethodID m_getValue = (*env)->GetMethodID(env, cls_payloadInfo, "getValue", "()Landroid/util/SparseArray;");
    jmethodID m_getCommandId = (*env)->GetMethodID(env, cls_payloadInfo, "getCommandId", "()I");

    jobject jSparseArray = (jobject) (*env)->CallObjectMethod(env, jPayloadInfo, m_getValue);
    if (jSparseArray != NULL)
    {
        jclass cls_sparseArray = (*env)->FindClass(env, "android/util/SparseArray");
        m_keyAt = (*env)->GetMethodID(env, cls_sparseArray, "keyAt", "(I)I");
        m_get = (*env)->GetMethodID(env, cls_sparseArray, "get", "(I)Ljava/lang/Object;");
        jmethodID m_size = (*env)->GetMethodID(env, cls_sparseArray, "size", "()I");
        sparse_size = (*env)->CallIntMethod(env, jSparseArray, m_size);
        loge("sparse_size===%d", sparse_size);
        q_value = queue_create();
        if (q_value == NULL)
        {
            loge("create queue failed.");
            return NULL;
        }
    }
    else
    {
        logw("key value is empty");
    }

    cmd_id = (*env)->CallIntMethod(env, jPayloadInfo, m_getCommandId);
    if (cmd_id >= 0)
    {
        payload_len += 2;//L2 packet header
        bd_bt_set_cmdId_version(packet, cmd_id, 0x0);
    }

    for (int i = 0; i < sparse_size; ++i)
    {
        uint8_t *pArray = NULL;
        unsigned int value_len = 0;
        int key = (*env)->CallIntMethod(env, jSparseArray, m_keyAt, i);
        logi("key=0x%02x", key);
        payload_len += 1;//key byte
        payload_len += 2;//key header byte
        jobjectArray jobjectArray1 = (*env)->CallObjectMethod(env, jSparseArray, m_get, key);
        if (jobjectArray1 != NULL)
        {
            pArray = (uint8_t *) (*env)->GetByteArrayElements(env, jobjectArray1, NULL);
            value_len = (unsigned int)(*env)->GetArrayLength(env, jobjectArray1);
            payload_len += value_len;//key value byte
            logw("value_len=%u", value_len);
            bd_bt_set_key_value(q_value, packet->cmd_id, key, pArray, value_len);

//            for (int j = 0; j < value_len; ++j)
//                loge("valueArray=%d", pArray[j]);
            (*env)->ReleaseByteArrayElements(env, jobjectArray1, (jbyte *) pArray, NULL);
        }
        else
        {
            bd_bt_set_key_value(q_value, packet->cmd_id, key, pArray, value_len);
        }
    }
    total_size += payload_len;

    if (payload_len > 0)
    {
        payload_buf = calloc(payload_len, sizeof(uint8_t));
        if (!payload_buf)
        {
            loge("%s calloc failed", __func__);
            return NULL;
        }
        bd_bt_set_payload_length(packet, payload_len);
    }

    uint8_t *buf = (uint8_t *) packet;

    if (payload_buf)
    {
        if (cmd_id >= 0)
        {
            memcpy(payload_buf + position, buf+8, 2);//copy L2 header to payload buf
            position += 2;
        }

        int q_size = queue_empty(q_value) ? 0 : queue_elements(q_value);
        key_value_t *key_value = NULL;
        for (int i = 0; i < q_size; ++i)
        {
            queue_get(q_value, (void **) &key_value);
            if (key_value != NULL)
            {
                uint8_t *key_value_buf = (uint8_t *) key_value;
                memcpy(payload_buf+position, key_value_buf, 1+2);//copy key&key header to the end of L2 header
                position+=(1+2);

                memcpy(payload_buf+position, key_value->value, key_value->value_len);//copy value to the end of key&key header
                position+=key_value->value_len;
            }
            else
            {
                logw("key value is null");
            }
        }
        if (q_value)
            queue_destroy_complete(q_value, destroy_queue_element);
//        for (int i = 0; i < payload_len; i++)
//            logw("payload i=%d 0x%02x", i, (payload[i]));
        memcpy(buf + 8, payload_buf, payload_len);//copy payload to the end of L1 header
        //logi("queue_empty q size=%d", queue_elements(q_value));
        uint16_t crc = bd_crc16(0, payload_buf, payload_len);
        bd_bt_set_crc16(packet, crc);
        //logw("crc16=%02x", crc);
    }

    loge("total_size=%02x, payload_len=%d", total_size, payload_len);
    for (int i = 0; i < total_size; i++)
        logi("i=%d 0x%02x", i, (buf[i]));

    jbyteArray jbyteArray1 = (*env)->NewByteArray(env, total_size);
    (*env)->SetByteArrayRegion(env, jbyteArray1, 0, total_size, (const jbyte *) buf);
    if (payload_buf)
        free(payload_buf);
    return jbyteArray1;
}

static void destroy_queue_element(void *element)
{
    logi("%s:%p",__func__, element);
    if (element)
        free(element);
}
static jboolean jni_bt_release(JNIEnv *env, jobject thiz)
{
    logi("%s", __func__);
    packet_hdr_t *packet = &g_data;
    assert(packet != NULL);
    if (packet)
    {
//        if (q_value&&queue_elements(q_value)>0)
//            queue_destroy_complete(q_value, destroy_queue_element);
    }
    else
    {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

static jint jni_bt_parse_data(JNIEnv *env, jobject thiz, jbyteArray jbyteArray1, jint size)
{
    uint8_t *data = NULL;
    app_context_t *pApp = &app_ctx;
    //packet_hdr_t *rcv_data = NULL;
    if (jbyteArray1 == NULL || size <= 0)
    {
        loge("Data maybe is null");
        return BDBT_DATA_ILLEGAL;
    }
    data = (uint8_t *) (*env)->GetByteArrayElements(env, jbyteArray1, NULL);
    (*env)->ReleaseByteArrayElements(env, jbyteArray1, (jbyte *) data, NULL);
    logi("app->start=%d", pApp->start);
    if (!pApp->start)
    {
        //rcv_data = (packet_hdr_t *) data;
        memcpy(&pApp->packetHdr, data, sizeof(packet_hdr_t));
        //rcv_data = &pApp->packetHdr;
        for (int i = 0; i < size; i++) {
            logi("rcv data i=%d %02x", i, data[i]);
        }
        if (pApp->packetHdr.magic != 0xAB)
        {
            loge("The data maybe from hack...");
            return BDBT_MAGIC_ERROR;
        }
        bd_bt_set_err_flag(&pApp->packetHdr, pApp->packetHdr.err_flag);
        bd_bt_set_payload_length(&pApp->packetHdr, pApp->packetHdr.payload_len);
        bd_bt_set_crc16(&pApp->packetHdr, pApp->packetHdr.crc16);
        bd_bt_set_seq_id(&pApp->packetHdr, pApp->packetHdr.seq_id);
        if (pApp->packetHdr.ack_flag)
        {
            logi("ack packet: err flag=%x, seq id=%d", pApp->packetHdr.err_flag, pApp->packetHdr.seq_id);
            if (pApp->packetHdr.err_flag)
            {
                //on_ack_response(JNI_TRUE);
                return BDBT_SENT_SUCCESS;
            }
            else
            {
                //on_ack_response(JNI_FALSE);
                return BDBT_SENT_ERROR;
            }
        }
        else
        {
            memset(pApp, 0, sizeof(app_context_t));
            pApp->start = 1;
            pApp->end = 0;
            loge("len =0x%04x", pApp->packetHdr.payload_len);
            if (pApp->packetHdr.payload_len > 0)
            {
                size_t len = (size_t) (size - 8);
                if (len > 0)
                {
                    memcpy(pApp->payload_buf, &data[8], len);
                    pApp->curr_pos += len;
                }
            }
        }
    }
    else
    {
        memcpy(pApp->payload_buf+pApp->curr_pos, data, (size_t) size);
        pApp->curr_pos += size;
    }

    //loge("===%02x, %02x, %02x, %02x", rcv_data->reserve, rcv_data->err_flag, rcv_data->ack_flag, rcv_data->version);
    if (pApp->packetHdr.payload_len == pApp->curr_pos)
    {
        uint16_t crc = bd_crc16(0, pApp->payload_buf, (uint16_t) pApp->curr_pos);
        if (crc != pApp->packetHdr.crc16)
        {
            loge("The CRC of data is incorrect...");
            return BDBT_CRC_ERROR;
        }

        logi("cmd id=0x%02x", pApp->packetHdr.cmd_id);
        if (parse_key_value(pApp->packetHdr.cmd_id, pApp)<0)
        {
            return BDBT_UNKNOWN_ERROR;
        }
        pApp->start = 0;
    }

    return BDBT_RECV_SUCCESS;
}
static jbyteArray jni_bt_wrap_ack_packet(JNIEnv *env, jobject thiz, jint errFlag, jint ackFlag)
{
    app_context_t *ctx = &app_ctx;
    jint pack_size = 8;
    packet_hdr_t packetHdr;
    const uint8_t *ack_buf = calloc((size_t) pack_size, sizeof(uint8_t));
    if (!ack_buf)
    {
        loge("malloc failed");
        return NULL;
    }
    assert(ctx != NULL);
    //AB 10 00 00 00 00 00 1E
    memcpy(&packetHdr, &ctx->packetHdr, sizeof(packet_hdr_t));
    bd_bt_set_err_flag(&packetHdr, errFlag);
    bd_bt_set_ack_flag(&packetHdr, ackFlag);
    memcpy(ack_buf, &packetHdr, (size_t) pack_size);
    jbyteArray jbyteArray1 = (*env)->NewByteArray(env, pack_size);
    (*env)->SetByteArrayRegion(env, jbyteArray1, 0, pack_size, (const jbyte *) ack_buf);
    return jbyteArray1;
}
//static void parse_key_value(uint8_t cmd, uint8_t *payload_buf, uint16_t buf_size)
static int parse_key_value(uint8_t cmd, app_context_t *ctx)
{
    assert(ctx != NULL);
    loge("buf size= 0x%02x", ctx->packetHdr.payload_len);
    uint8_t key=0;
    uint16_t value_len;
    int position = 0;
    int key_value_len = ctx->packetHdr.payload_len - 2;
    uint8_t *key_value_buf = &ctx->payload_buf[2];//skip 2byte L2 header
    queue_t *queue;
    queue = queue_create();
    if (!queue)
    {
        loge("Create queue fail.");
        return -1;
    }

    while (position < key_value_len)
    {
        key = key_value_buf[position];
        value_len = (uint16_t) ((key_value_buf[position+1] & 0x01)<<8)|key_value_buf[position+2];
        position +=(1+2);

        key_value_t *key_value = calloc(1, sizeof(key_value_t));
        key_value->key = key;
        key_value->reserve = 0;//128;//7bit
        key_value->value_len = value_len;//9 bit
        loge("key=%02x, value len=%02x", key_value->key, key_value->value_len);
        if (value_len > 0)
        {
            key_value->value = calloc(value_len, sizeof(uint8_t));
            memcpy(key_value->value, key_value_buf+position, value_len);
            position+=value_len;
        }
        else
        {
            logw("No value ");
        }
        queue_put(queue, key_value);
    }
    logi("key=0x%02x", key);
    switch (cmd)
    {
        case CMD_FW_UPGRADE:
            switch (key)
            {
                case KEY_FW_UPGRADE_REQUEST:
                    break;
                case KEY_FW_UPGRADE_RESPONSE:
                    logi("fw upgrade response");
                    key_value_t *keyValue=NULL;
                    queue_get(queue, (void **) &keyValue);
                    if (keyValue)
                    {
                        logw("%02x, %02x, %02x,%02x", keyValue->key, keyValue->value_len, keyValue->value[0], keyValue->value[1]);
                        if (keyValue->value_len > 0)
                        {
                            on_callback(cmd, key, 2);
                            if (keyValue->value[0] == 0x00)
                            {
                                //success
                            }
                            else if (keyValue->value[0] == 0x01)
                            {
                                //failed
                                if (keyValue->value[1] == 0x01)
                                {
                                    //Power is too low
                                }
                            }
                        }
                    }

                    break;
                default:
                    break;
            }
            break;
        case CMD_SETTINGS:

            break;
        case CMD_BINDING:
        case CMD_ALERT:
        case CMD_SPORT:
        case CMD_FACTORY_TEST:
        case CMD_CONTROL:
        case CMD_DUMP_STACK:
        case CMD_READ_FLASH:
            break;
        default:
            loge("Unknown cmd 0x%02x", cmd);
            break;
    }
    if (queue)
        queue_destroy_complete(queue, destroy_queue_element);
    return 0;
}
static JNINativeMethod g_methods[] =
{
        {"nativeInit", "()V", (void*) jni_init},
        {"nativeRelease", "()Z", (void*) jni_bt_release},
        {"nativeWrapData", "(Lcom/demuxer/PayloadInfo;I)[B", (void*) jni_bt_wrap_data},
        {"nativeParseData", "([BI)I", (void *)jni_bt_parse_data},
        {"nativeWrapAckPacket", "(II)[B", (void*)jni_bt_wrap_ack_packet},
};

jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void**)&env, JNI_VERSION_1_6) != JNI_OK){
        return JNI_ERR;
    }
    assert(env != NULL);

    jclass klass = (*env)->FindClass(env, JNI_CLASS);
    if (klass == NULL){
        return JNI_ERR;
    }
    (*env)->RegisterNatives(env, klass, g_methods, NELEM(g_methods));

    return JNI_VERSION_1_6;
}