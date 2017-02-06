package com.demuxer;

import java.io.Serializable;

/**
 * Created by bob on 17-1-17.
 */

public class KeyInfo implements Serializable {
    private int key;//1byte
//    private int reserve;//7bits
//    private int valueLength;//9bits
    private byte[] value;//N bytes

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

}
