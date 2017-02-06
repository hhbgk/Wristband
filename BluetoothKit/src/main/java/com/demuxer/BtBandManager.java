package com.demuxer;

import android.util.Log;

import com.inuker.bluetooth.library.connect.response.BleAckResponse;

public class BtBandManager {
    String tag = getClass().getSimpleName();
    private static BtBandManager instance = null;
    private int mVersion;

    public static BtBandManager getInstance(){
        if (instance == null){
            synchronized (BtBandManager.class){
                if (instance == null){
                    instance = new BtBandManager();
                }
            }
        }

        return instance;
    }

    private BtBandManager(){
        nativeInit();

       //test wrap data
/*        PayloadInfo payloadInfo = new PayloadInfo();
        payloadInfo.setCommandId(0x06);
        SparseArray<byte[]> sparseArray = new SparseArray<>();
//        sparseArray.put(0x06, new byte[]{5,4,3,2,1});
        sparseArray.put(0x10, null);
        payloadInfo.setValue(sparseArray);
        byte[] data = nativeWrapData(payloadInfo, mVersion);
*/
//        for (int i = 0; i < data.length; i++)
//            Log.e(tag, String.format(Locale.US, "%02x", data[i]));


        //testing parse data from receive interface
  /*      byte[] data = new byte[15];
        data[0] = (byte) 0xab;
        data[1] = 0x3F;//reserve, err flag, ack flag, version
        data[2] = 0x00;//payload length
        data[3] = 0x07;//payload length
        data[4] = 0x01;//crc
        data[5] = (byte) 0xf1;//crc
        data[6] = 0x00;//sequence id
        data[7] = 0x1e;//sequence id

        data[8] = 0x01;//cmd id
        data[9] = 0x00;//version, reserve

        data[10] = 0x02;//key
        data[11] =0x00;//key header
        data[12]=0x02;//value length
        data[13] = 0x0f;
        data[14] = 0x0a;

        nativeParseData(data, data.length);
*/
    }

    public byte[] wrapData(PayloadInfo payloadInfo, int version){
        if (payloadInfo == null){
            throw new NullPointerException("payloadInfo can not be null");
        }

        return nativeWrapData(payloadInfo, version);
    }

    private BleAckResponse mBleAckResponse;

    public int parseData(byte[] receivedData){
        if (receivedData == null){
            throw new NullPointerException("Received data can not be null");
        }
        return nativeParseData(receivedData, receivedData.length);
    }

    private void onNativeCallback(int cmd, int key, int state){
        Log.w(tag, "cmd "+ cmd +", key="+key + ", state="+state);
    }

    private void onAckResponse(boolean isError){
        Log.i(tag, "onAckResponse:"+isError);
        if (mBleAckResponse != null){
            if (isError)
                mBleAckResponse.onError("Maybe magic byte or CRC incorrect!");
            else
                mBleAckResponse.onSuccess();
        }
    }

    public byte[] wrapData(PayloadInfo payloadInfo){
        return wrapData(payloadInfo, mVersion);
    }

    public byte[] wrapAckPacket(int errFlag, int ackFlag){
        return nativeWrapAckPacket(errFlag, ackFlag);
    }

    private void setVersion(int version){
        mVersion = version;
    }

    public int getVersion(){
        return mVersion;
    }

    public boolean release(){
        return nativeRelease();
    }
    static {
        System.loadLibrary("queue");
        System.loadLibrary("bt_band");
    }

    private native void nativeInit();
    private native boolean nativeRelease();
    private native byte[] nativeWrapData(PayloadInfo payloadInfo, int version);
    private native int nativeParseData(byte[] receivedData, int size);
    private native byte[] nativeWrapAckPacket(int errorFlag, int ackFlag);
}
