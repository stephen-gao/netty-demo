package com.gs.netty.common;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gs.netty.common.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 编码
 *
 * @author GaoSheng
 * @version 1.0
 * @blame GaoSheng
 * @since 2020/06/24 11:12
 **/
public class IMEncoder extends MessageToByteEncoder<Packet> {
    private static final Logger logger = LoggerFactory.getLogger(IMEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        System.out.println("###########");
        System.out.println(JSONObject.toJSONString(packet));
        try {
            out.markWriterIndex();
            //协议头
            out.writeByte(Protocol.FIRST_BYTE);
            out.writeByte(Protocol.SECOND_BYTE);
            out.writeByte(Protocol.THIRD_BYTE);
            out.writeByte(Protocol.FOUR_BYTE);
            //版本
            out.writeByte(packet.getProtocolVersion());
            //消息类型
            out.writeByte(packet.getMessageType());
            //加密方式
            out.writeByte(packet.getEncryptionType());
            //序列化方式
            out.writeByte(packet.getSerializeType());
            //业务类型
            out.writeInt(packet.getCmdEnum().getCmd());
            //子业务类型
            out.writeInt(packet.getCmdEnum().getSubcmd());
            //预留字段16位
            out.writeLong(0L);
            out.writeLong(0L);
            //设置消息Id
            out.writeBytes(packet.getMessageId().getBytes(StandardCharsets.US_ASCII));
            //发送者
            out.writeBytes(packet.getFrom().getBytes(StandardCharsets.US_ASCII));
            //接收者
            out.writeBytes(packet.getTo().getBytes(StandardCharsets.US_ASCII));
            //消息体
            if (packet.getData() != null && packet.getData().length > 0) {
                out.writeInt(packet.getData().length);
                out.writeBytes(packet.getData());
            } else {
                out.writeInt(0);
            }
        }catch (Throwable e){
            out.resetWriterIndex();
            logger.error("IMEncoder was happened some error",e);
        }

    }

}
