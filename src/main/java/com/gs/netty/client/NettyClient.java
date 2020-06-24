package com.gs.netty.client;

import com.gs.netty.common.IMDecoder;
import com.gs.netty.common.IMEncoder;
import com.gs.netty.common.Packet;
import com.gs.netty.common.protocol.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    private Bootstrap boot;

    public Channel connect(int port, String host) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        boot = new Bootstrap();
        boot.group(group).channel(NioSocketChannel.class);

        ChannelFuture future;
        //进行连接
        try {
            synchronized (boot) {
                boot.handler(new ChannelInitializer<Channel>() {
                    //初始化channel
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 128, 4))
                                .addLast(new IdleStateHandler(0, 0, 30, TimeUnit.SECONDS))
                                .addLast(new IMDecoder())
                                .addLast(new IMEncoder())
                                .addLast(new HeartBeatClientHandler());
                    }
                });

                future = boot.connect(host, port);
            }

            // 以下代码在synchronized同步块外面是安全的
            future.sync();
        } catch (Throwable t) {
            throw new Exception("connects to  fails", t);
        }
        return future.channel();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        NettyClient client = new NettyClient();
        Channel channel = client.connect(port, "127.0.0.1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Packet packet = new Packet();
                packet.setFrom(UUID.randomUUID().toString().replace("-", ""));
                packet.setTo(UUID.randomUUID().toString().replace("-", ""));
                packet.setMessageId(UUID.randomUUID().toString().replace("-", ""));
                packet.setData("aa".getBytes());
                packet.setCmdEnum(CmdEnum.C2C_TYPE);
                packet.setEncryptionType(EncryptionType.NO_ENCRY);
                packet.setMessageType(MessageType.REQUEST);
                packet.setSerializeType(SerializeType.STRING);
                packet.setProtocolVersion(Protocol.PROTOCOL_V_BYTE);
                channel.writeAndFlush(packet);
            }
        }).start();
    }

}