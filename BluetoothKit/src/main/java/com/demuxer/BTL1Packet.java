package com.demuxer;

/**
 * Transport layer L1
 * 协议层数据包结构
 */

public class BTL1Packet {

    private byte magic;//8 bits
    private int reserve;//2 bits
    private int errorFlag; //1 bit
    private int ackFlag;//1 bit
    private int version;//4 bits
    private short payloadLength;//16 bits
    private short crc16;//16 bits
    private short sequenceId;//16 bits

    private byte[] payload;//0-504 bytes

    public byte getMagic() {
        return magic;
    }

    public void setMagic(byte magic) {
        this.magic = magic;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public int getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(int errorFlag) {
        this.errorFlag = errorFlag;
    }

    public int getAckFlag() {
        return ackFlag;
    }

    public void setAckFlag(int ackFlag) {
        this.ackFlag = ackFlag;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public short getPayloadLength() {
        return payloadLength;
    }

    public void setPayloadLength(short payloadLength) {
        this.payloadLength = payloadLength;
    }

    public short getCrc16() {
        return crc16;
    }

    public void setCrc16(short crc16) {
        this.crc16 = crc16;
    }

    public short getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(short sequenceId) {
        this.sequenceId = sequenceId;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
