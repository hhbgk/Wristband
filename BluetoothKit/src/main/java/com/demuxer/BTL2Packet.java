package com.demuxer;

/**
 * Created by bob on 17-1-13.
 */

public class BTL2Packet {
    private byte commandId;//8bits
    private int version;//4bits
    private int reserve;//4bits

    private byte[] payload;//N bytes

    public byte getCommandId() {
        return commandId;
    }

    public void setCommandId(byte commandId) {
        this.commandId = commandId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
