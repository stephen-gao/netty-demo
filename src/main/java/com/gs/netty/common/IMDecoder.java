package com.gs.netty.common;

import com.gs.netty.common.protocol.CmdEnum;
import com.gs.netty.common.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 解码
 *
 * @author GaoSheng
 * @version 1.0
 * @blame GaoSheng
 * @since 2020/06/24 15:34
 **/
public class IMDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(IMDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("*************");
        if (in == null || in.readableBytes() == 0) {
            return;
        }
        try {
            if (Protocol.FIRST_BYTE != in.readByte()) {
                logger.warn("first byte is not suitable for this protocol");
                return;
            }
            if (Protocol.SECOND_BYTE != in.readByte()) {
                logger.warn("second byte is not suitable for this protocol");
                return;
            }
            if (Protocol.THIRD_BYTE != in.readByte()) {
                logger.warn("third byte is not suitable for this protocol");
                return;
            }
            if (Protocol.FOUR_BYTE != in.readByte()) {
                logger.warn("four byte is not suitable for this protocol");
                return;
            }

            Packet packet = new Packet();
            //协议版本
            packet.setProtocolVersion(in.readByte());
            //消息类型
            packet.setMessageType(in.readByte());
            //加密类型
            packet.setEncryptionType(in.readByte());
            //序列化类型
            packet.setSerializeType(in.readByte());
            //业务类型
            int cmd = in.readInt();
            //子业务类型
            int subCmd = in.readInt();
            CmdEnum cmdEnum = CmdEnum.getCmd(cmd, subCmd);
            packet.setCmdEnum(cmdEnum);
            //预留字段16位
            in.readLong();
            in.readLong();
            //消息ID
            packet.setMessageId(readByteString(in, Protocol.MESSAGE_ID_LENGTH));
            //发送者
            packet.setFrom(readByteString(in, Protocol.FROM_LENGTH));
            //接收者
            packet.setTo(readByteString(in, Protocol.TO_LENGTH));
            //消息长度
            int length = in.readInt();
            //消息体
            if (length > 0) {
                packet.setData(readByteArr(in, length));
            }

            out.add(packet);

        } catch (Throwable e) {
            logger.error("IMDecoder was happened some error", e);
            ctx.close();
        }

    }

    private byte[] readByteArr(ByteBuf byteBuf, int length) {
        byte[] result = new byte[length];
        byteBuf.readBytes(result, 0, length);
        return result;
    }

    private String readByteString(ByteBuf byteBuf, int length) {
        byte[] bytes = readByteArr(byteBuf, length);
        return new String(bytes, StandardCharsets.US_ASCII);
    }
}
