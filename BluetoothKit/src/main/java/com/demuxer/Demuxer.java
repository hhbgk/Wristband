package com.demuxer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Demuxer {

    static String tag = Demuxer.class.getSimpleName();
    private static Demuxer instance = null;

    public static Demuxer getInstance(){
        if (instance == null){
            synchronized (Demuxer.class){
                if (instance == null){
                    instance = new Demuxer();
                }
            }
        }
        return instance;
    }

    public static BTL1Packet parseL1Data(byte[] data){
        if (data == null || data.length <= 0){
            Log.e(tag, "Data illegal");
            return null;
        }

        if (data.length < 8){
            Log.e(tag, "Unknown data:"+data.length);
            return null;
        }
        BTL1Packet btl1Packet = new BTL1Packet();
        //if (data.length == 8)
        {
            parseL1Header(data, btl1Packet);
            if (data.length > 8){
                byte[] payload = new byte[data.length - 8];
//                byte[] payload = Arrays.copyOfRange(data, data.length - 1 - 8, data.length - 1);
                System.arraycopy(data, 8, payload, 0, data.length-8);
                btl1Packet.setPayload(payload);
            }
            return btl1Packet;
        }
    }

    private static void parseL1Header(byte[] data, BTL1Packet btl1Packet){
        btl1Packet.setMagic(data[0]);

        byte tmp = data[1];

        btl1Packet.setReserve(tmp & 0xC0);
        btl1Packet.setErrorFlag(tmp & 0x20);
        btl1Packet.setAckFlag(tmp & 0x10);
        btl1Packet.setVersion(tmp & 0x0F);

        short len = (short) ((data[2]<<8)|(data[3]));
        btl1Packet.setPayloadLength(len);

        short crc16 = (short) ((data[4]<<8)|(data[5]));
        btl1Packet.setCrc16(crc16);

        short seqId = (short) ((data[6]<<8)|(data[7]));
        btl1Packet.setSequenceId(seqId);
    }

    public static BTL2Packet parseL2Data(byte[] data){
        if (data == null || data.length <= 0){
            Log.e(tag, "Data illegal");
            return null;
        }

        if (data.length < 2){
            Log.e(tag, "Unknown data:"+data.length);
            return null;
        }

        BTL2Packet btl2Packet = new BTL2Packet();
        parseL2Header(data, btl2Packet);
        if (data.length > 2){
            byte[] payload = new byte[data.length-2];
            System.arraycopy(data, 2, payload, 0, data.length-2);
            btl2Packet.setPayload(payload);
        }
        return btl2Packet;
    }

    private static void parseL2Header(byte[] data, BTL2Packet btl2Packet){
        btl2Packet.setCommandId(data[0]);

        byte tmp = data[1];
        btl2Packet.setVersion(tmp & 0xF0);
        btl2Packet.setReserve(tmp & 0xF0);
    }

    public static List<PayloadInfo> parsePayload(byte[] data){
        if (data == null || data.length <= 0){
            Log.e(tag, "parsePayload: Data illegal");
            return null;
        }

        if (data.length > 3){
            Log.e(tag, "parsePayload : unknown data="+data.length);
            return null;
        }

/*
        int position = 0;
        while (position < data.length) {
            PayloadInfo payloadInfo = new PayloadInfo();
            payloadInfo.setKey(data[position]);

            payloadInfo.setReserve(data[position + 1] & 0x7F);

            short tmp = (short) ((data[position + 1]<<8)|(data[position + 2]));
            payloadInfo.setValueLength(tmp & 0x01FF);

            byte[] value = new byte[payloadInfo.getValueLength()];
            System.arraycopy(data, 1+2 + position, value, 0, payloadInfo.getValueLength());
            payloadInfo.setValue(value);

            list.add(payloadInfo);

            position += (1 + 2 + payloadInfo.getValueLength());
        }
*/
        List<PayloadInfo> list = new ArrayList<>();
        return list;
    }
}
