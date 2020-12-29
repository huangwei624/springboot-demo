package com.middleyun.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 基于tcp协议的socket 服务
 */
public class NettyServer {
    // 服务绑定端口
    private Integer port;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workGroup;

    public NettyServer(Integer port) {
        this.port = port;
    }

    /**
     * 启动服务
     */
    public void start() {
        try {
            // 接收客服端连接的线程组
            bossGroup = new NioEventLoopGroup();
            // 处理网络读取等操作的线程组
            workGroup = new NioEventLoopGroup();
            // 服务启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    // 针对bossGroup, tcp 连接队列的长度，超过该队列长度，则拒绝处理
                    .option(ChannelOption.SO_BACKLOG, 256)
                    // 针对 workGroup, 但客服端连接意外断掉后，服务端可以通过主动探测，得知客服端连接的情况
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            // pipeline.addLast(new FixedLengthFrameDecoder(4));
                            pipeline.addLast(new NettyHandler());
                        }
                    });
            System.out.println("服务在端口"+port+"启动");
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 关闭线程池
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(18080);
        nettyServer.start();
    }
}
