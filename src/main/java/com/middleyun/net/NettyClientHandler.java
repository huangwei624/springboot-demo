package com.middleyun.net;

import com.middleyun.util.CRC16Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Arrays;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // 两种响应
    private static byte[] program2_response = {(byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5,
            (byte)0xA5, (byte)0x00, (byte)0x80, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0xFE, (byte)0x02, (byte)0x09, (byte)0x00, (byte)0xA1, (byte)0x06, (byte)0x00, (byte)0x04, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x6D, (byte)0xA4, (byte)0x5A};

    private static byte[] program1_dynamic_response = {(byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5,
            (byte)0xA5, (byte)0xA5, (byte)0x00, (byte)0x80, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0xFE, (byte)0x02, (byte)0x05, (byte)0x00, (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x64, (byte)0xDC, (byte)0x5A};

    // 动态区指令
    private static byte[] dynamic = {(byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5,
            (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0xFE, (byte)0x02, (byte)0x37, (byte)0x00, (byte)0xA3, (byte)0x06, (byte)0x01, (byte)0x23, (byte)0x00, (byte)0x00,
            (byte)0x01, (byte)0x2E, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x08, (byte)0x00,
            (byte)0x10, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x02, (byte)0x02, (byte)0x01, (byte)0x00, (byte)0x0A, (byte)0x0A, (byte)0x13, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x5C, (byte)0x43, (byte)0x32, (byte)0xBF, (byte)0xD5, (byte)0xCE, (byte)0xBB, (byte)0x34, (byte)0x32, (byte)0x5C,
            (byte)0x54, (byte)0x30, (byte)0x30, (byte)0x33, (byte)0x62, (byte)0x61, (byte)0x63, (byte)0x6B, (byte)0x7C, (byte)0x20,
            (byte)0x15, (byte)0x5A};

    // 节目第一部分指令
    private static byte[] program1 = {(byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0xA5, (byte) 0xA5,
            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0xFE, (byte) 0x02, (byte) 0x0E, (byte) 0x00, (byte) 0xA1, (byte) 0x05, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x50, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xE1, (byte) 0x46, (byte) 0x5A};


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] data = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(data);
            // 节目第一部分的指令
            if (data.length <=40) {    // 节目第一部分指令
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(program1_dynamic_response));
                System.out.println("===收到节目第一部分指令，并做了响应，，");
            } else if (data.length >= 70) {   // 动态区指令
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(program1_dynamic_response));
                System.out.println("===收到动态区指令，并做了响应，，");
            } else {    // 节目第二部分的指令
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(program2_response));
                System.out.println("===收到节目第二部分指令，并做了响应，，");
            }
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
