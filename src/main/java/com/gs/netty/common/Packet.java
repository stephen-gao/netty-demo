package com.gs.netty.common;

import com.gs.netty.common.protocol.CmdEnum;

/**
 * ...
 *
 * @author GaoSheng
 * @version 1.0
 * @blame GaoSheng
 * @since 2020/06/24 11:44
 **/
public class Packet {

    /**
     * 协议类型
     */
    private byte protocolVersion;

    /**
     * 序列化方式
     */
    private byte serializeType;

    /**
     * 加密方式
     */
    private byte encryptionType;

    /**
     * 加密方式
     */
    private byte messageType;

    /**
     * 主业务
     */
    private CmdEnum cmdEnum;

    /**
     * 来源id 32字节
     */
    private String from;

    /**
     * 接受者id 32 字节
     */
    private String to;

    /**
     * 消息id 32 字节
     */
    private String messageId;

    /**
     *
     */
    private byte[] data;


    public byte getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(byte protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public byte getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(byte serializeType) {
        this.serializeType = serializeType;
    }

    public byte getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(byte encryptionType) {
        this.encryptionType = encryptionType;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public CmdEnum getCmdEnum() {
        return cmdEnum;
    }

    public void setCmdEnum(CmdEnum cmdEnum) {
        this.cmdEnum = cmdEnum;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
