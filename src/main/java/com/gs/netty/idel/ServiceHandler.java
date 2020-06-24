package com.gs.netty.idel;

import com.alibaba.fastjson.JSONObject;
import com.gs.netty.common.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import sun.nio.cs.ext.MS874;

/**
 * 业务handler
 */
@Log4j2
@ChannelHandler.Sharable
@Component("im.tcp.ServiceHandler")
public class ServiceHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet)msg;
        System.out.println("消息");
        System.out.println(JSONObject.toJSONString(packet));
        System.out.println(new String(packet.getData()));
        System.out.println("##############");
        ctx.fireChannelRead(msg);

    }
}
