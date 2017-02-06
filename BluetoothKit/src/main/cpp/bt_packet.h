#ifndef BLUETOOTHKIT_BT_PACKET_H
#define BLUETOOTHKIT_BT_PACKET_H

#include <stdio.h>
#include <asm/byteorder.h>
#include "queue/queue.h"

//payload
typedef struct {
    uint8_t key;
#if defined(__BIG_ENDIAN)
    uint16_t reserve:7;
    uint16_t value_len:9;
#elif defined(__LITTLE_ENDIAN)
    uint16_t value_len:9;
    uint16_t reserve:7;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
    uint8_t *value;
}key_value_t;

//L2 packet(2-504), L2 header+L2 payload
typedef struct {
    uint8_t cmd_id;
    uint8_t version:4;
    uint8_t reserve:4;
    key_value_t *data;
}payload_hdr_t;

//L1 packet(8-512)+L2 header (16 bits)
typedef struct packet_hdr{
    uint8_t magic;//8 bit
#if defined (__BIG_ENDIAN_BITFIELD)
    uint8_t reserve:2;//2 bit
    uint8_t err_flag:1;
    uint8_t ack_flag:1;
    uint8_t version:4;
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t version:4;
    uint8_t ack_flag:1;
    uint8_t err_flag:1;
    uint8_t reserve:2;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
    uint16_t payload_len;
    uint16_t crc16;
    uint16_t seq_id;

    uint8_t cmd_id;
#if defined (__BIG_ENDIAN_BITFIELD)
    uint8_t payload_version:4;
    uint8_t payload_reserve:4;
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t payload_reserve:4;
    uint8_t payload_version:4;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
    //queue_t *q_value;//L2 payload
}packet_hdr_t;

#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
#else
#error  "Please fix <asm/byteorder.h>"
#endif

typedef struct {//32bits
#if defined (__BIG_ENDIAN_BITFIELD)

#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t second:6;
    uint8_t minute:6;
    uint8_t hour:5;
    uint8_t day:5;
    uint8_t month:4;
    uint8_t year:6;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}phone_time_t;

typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
    uint8_t year:6;
    uint8_t month:4;
    uint8_t day:5;
    uint8_t hour:5;
    uint8_t minute:6;
    uint8_t id:3;
    uint8_t reserve:4;
    uint8_t day_flag:7;//?
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t day_flag:7;//?
    uint8_t reserve:4;
    uint8_t id:3;
    uint8_t minute:6;
    uint8_t hour:5;
    uint8_t day:5;
    uint8_t month:4;
    uint8_t year:6;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}alarm_t;

//用户profile设置
typedef struct {//32bits
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t reserve:5;
    uint16_t weight:10;
    uint16_t height:9;
    uint8_t age:7;
    uint8_t sex:1;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}user_profile_t;

//防丢失设置
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t mode:4;//0x00:no alert, 0x01:middle alert, 0x02:high alert
    uint8_t reserve:4;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}anti_lost_t;
//计步目标设定
typedef struct {//4Bytes
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint32_t target;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}step_target_t;
//久坐提醒
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t day_flags;
    uint8_t end_time;
    uint8_t start_time;
    uint8_t duration;
    uint16_t threshold;
    uint8_t open;
    uint8_t reserve;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}sedentary_alert_t;
//左还是右手佩戴
typedef struct {//8bits
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t hand;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}left_right_t;

typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t reserve;
    uint8_t os;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}phone_os_t;

typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t op;
    uint8_t *phone_info;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}call_list_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t enable;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}call_notify_t;
//----------------------binding command 0x03-----------------
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t state;//0x00 success, 0x01 fail
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}bind_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t state;//0x00 success, 0x01 fail
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}login_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint32_t user_id;
    uint32_t super_key;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}super_binding_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t state;//0x00 success, 0x01 fail, 0x02 super key crc fail, 0x03 power lowly
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}reply_super_binding_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t item_count;
    uint8_t reserve2;
    uint8_t day:5;
    uint8_t month:4;
    uint8_t year:6;
    uint8_t reserve1:1;
    uint8_t *sport_data;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}sport_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint16_t distance;
    uint32_t calorie:19;
    uint8_t active_time:4;
    uint16_t step_count:12;
    uint8_t mode:2;
    uint16_t offset:11;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}sport_item_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint16_t item_count;
    uint8_t day:5;
    uint8_t month:4;
    uint8_t year:6;
    uint8_t reserve:1;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}sleep_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t mode:4;
    uint16_t reserve:12;
    uint16_t minutes;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}sleep_item_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint16_t item_count;
    uint8_t day:5;
    uint8_t month:4;
    uint8_t year:6;
    uint8_t reserve:1;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}sleep_setting_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint8_t mode:4;
    uint16_t reserve:12;
    uint16_t minutes;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}sleep_setting_item_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint32_t total_calorie;
    uint32_t total_distance;
    uint32_t total_steps;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}today_sport_sync_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint16_t distance;
    uint16_t step_count;
    uint32_t calorie;
    uint8_t active_time;
    uint8_t mode;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}recent_sport_sync_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint16_t fifteen_minutes_distance;
    uint16_t fifteen_minutes_calorie;
    uint16_t fifteen_minutes_steps;
    uint32_t today_total_distance:24;
    uint32_t today_total_calorie:24;
    uint32_t today_total_steps:24;
    uint8_t offset;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}today_total_sport_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
    uint16_t delta_distance;
    uint16_t delta_calorie;
    uint16_t delta_steps;
    uint8_t offset;
#else
#error  "Please fix <asm/byteorder.h>"
#endif
}calibrate_delta_t;
typedef struct {
#if defined (__BIG_ENDIAN_BITFIELD)
#elif defined(__LITTLE_ENDIAN_BITFIELD)
#else
#error  "Please fix <asm/byteorder.h>"
#endif
};

uint16_t bd_crc16(uint16_t crc, uint8_t const *buffer, uint16_t len);

void bd_bt_set_version(packet_hdr_t *packet, int version);
packet_hdr_t *bd_bt_create_packet(void);
void bd_bt_set_key_value(queue_t *packet, uint8_t, int key, uint8_t *value, size_t size);

void bd_bt_set_magic(packet_hdr_t *packet, int );
void bd_bt_set_err_flag(packet_hdr_t *packet, int );
void bd_bt_set_ack_flag(packet_hdr_t *packet, int );
void bd_bt_set_seq_id(packet_hdr_t *packet, uint16_t);
void bd_bt_set_cmdId_version(packet_hdr_t *packet, int cmdId, int version);
void bd_bt_set_payload_length(packet_hdr_t *packet, uint16_t payload_len);
void bd_bt_set_crc16(packet_hdr_t *packet, uint16_t crc);
uint16_t bd_bt_get_seq_id(packet_hdr_t *packetHdr);
void bd_bt_update_seq_id(packet_hdr_t *packetHdr);
#endif //BLUETOOTHKIT_BT_PACKET_H
