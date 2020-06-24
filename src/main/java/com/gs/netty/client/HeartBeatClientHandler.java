package com.gs.netty.client;

import com.gs.netty.common.Packet;
import com.gs.netty.common.protocol.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
 
import java.util.Date;
import java.util.UUID;

@Sharable
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {
 
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("激活时间是："+new Date());
        System.out.println("HeartBeatClientHandler channelActive");
        ctx.fireChannelActive();
    }
 
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("停止时间是："+new Date());
        System.out.println("HeartBeatClientHandler channelInactive");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.ALL_IDLE) {
                // write heartbeat to server
                Packet packet = new Packet();
                packet.setFrom(UUID.randomUUID().toString().replace("-",""));
                packet.setTo(UUID.randomUUID().toString().replace("-",""));
                packet.setMessageId(UUID.randomUUID().toString().replace("-",""));
                packet.setCmdEnum(CmdEnum.HEART_TYPE);
                packet.setEncryptionType(EncryptionType.NO_ENCRY);
                packet.setMessageType(MessageType.REQUEST);
                packet.setSerializeType(SerializeType.STRING);
                packet.setProtocolVersion(Protocol.PROTOCOL_V_BYTE);
                System.out.println("发送心跳");
                ctx.writeAndFlush(packet);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}