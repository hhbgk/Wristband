package com.demuxer;

import android.util.SparseArray;

public class PayloadInfo {
    private int CommandId = -1;//8bit
    private int headerVersion;//4bit
    private int headerReserve;//4bit
    private SparseArray<byte[]> keyValue;

//    private byte[] payload;
//    private List<KeyInfo> keyInfo;

    public int getVersion() {
        return headerVersion;
    }

    public void setVersion(int version) {
        this.headerVersion = version;
    }

    public int getCommandId() {
        return CommandId;
    }

    public void setCommandId(int commandId) {
        CommandId = commandId;
    }

    int getHeaderReserve() {
        return headerReserve;
    }

    void setHeaderReserve(int headerReserve) {
        this.headerReserve = headerReserve;
    }

    public SparseArray<byte[]> getValue() {
        return keyValue;
    }

    public void setValue(SparseArray<byte[]> sparseArray) {
        this.keyValue = sparseArray;
//        sparseArray.get;

    }
}
