package com.middleyun.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;

public class NettyHandler extends ChannelInboundHandlerAdapter {

    /**
     * 处理来自客服端发送的消息
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 如果是字节数据
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            System.out.println(new String(bytes));
        }
        else {  // 如果不是字节数据，String 类型， 或者其他类型
            System.out.println(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * 调用channelRead 方法抛出异常会执行该方法
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("server 读取数据出现异常, "+ cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
