package com.gs.netty.idel;

import com.alibaba.fastjson.JSONObject;
import com.gs.netty.common.Packet;
import com.gs.netty.common.protocol.CmdEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import sun.nio.cs.ext.MS874;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
 
 
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet)msg;
        if(CmdEnum.HEART_TYPE == packet.getCmdEnum()){
            System.out.println("心跳");
            System.out.println(JSONObject.toJSONString(msg));
            System.out.println("##############");
            return;
        }
        ctx.fireChannelRead(msg);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleState state = ((IdleStateEvent) evt).state();
        System.out.println(state.name());
        ctx.fireUserEventTriggered(evt);
    }


 
}