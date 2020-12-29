package com.middleyun.net;

import com.sun.corba.se.impl.interceptors.PICurrent;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class NettyClient {
    // 服务端绑定端口
    private Integer port;
    // 服务端ip地址
    private String ip;
    // 消息通道
    private Channel channel;
    private NioEventLoopGroup group;

    public NettyClient( String ip, Integer port) {
        this.port = port;
        this.ip = ip;
    }

    /**
     * 启动服务
     */
    public void start() {
        try {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            channelFuture.addListener(future -> {
                if (!future.isSuccess()) {
                    System.out.println("客服端连接失败");
                    System.out.println("message:"+future.cause().getMessage());;
                    group.shutdownGracefully();
                } else {
                    System.out.println("客服端成功连接到" + ip + ":" + port);
                }
            });
            this.channel = channelFuture.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
        byte[] d = {(byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34, (byte) 0x35,
                (byte) 0x36, (byte) 0x37, (byte) 0x38, (byte) 0x39};
        for (int i = 0; i < d.length; i++) {
            byte[] data = {(byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0xA5
                    , (byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0x02, (byte) 0x30
                    , (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30
                    , (byte) 0x30, (byte) 0x30, d[i], (byte) 0xDB, (byte) 0xB9, (byte) 0x5A};
            threadPool.scheduleAtFixedRate(new MyRunnable(new HeartBeat(data)), 0, 30, TimeUnit.SECONDS);
        }

    }

    @Data
    public static class MyRunnable implements Runnable {

        private HeartBeat heartBeat;
        private NettyClient nettyClient;

        public MyRunnable(HeartBeat heartBeat) {
            this.heartBeat = heartBeat;
            nettyClient = new NettyClient("127.0.0.1", 2372);
            nettyClient.start();
        }

        @Override
        public void run() {
            try {
                nettyClient.channel.writeAndFlush(Unpooled.copiedBuffer(heartBeat.getData()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Data
    public static class HeartBeat{
        public HeartBeat(byte[] data) {
            this.data = data;
        }

        private byte[] data;
    }
}
