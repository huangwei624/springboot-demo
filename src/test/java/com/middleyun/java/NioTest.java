package com.middleyun.java;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioTest {

    private final String filePath = "G:\\temp\\card-controller.conf";

    @Test
    public void byteBuff() {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        allocate.put("hello world!".getBytes());
        // 目前缓冲写指针的位置
        System.out.println(allocate.position());
        allocate.flip();
        // 重置缓冲写指针后，指针的位置
        System.out.println(allocate.position());
    }

    @Test
    public void selector() throws IOException {
        // 创建一个selector
        Selector selector = Selector.open();
        // 创建一个ServerSocketChannel
        ServerSocketChannel servChannel = ServerSocketChannel.open();
        servChannel.configureBlocking(false);
        // 绑定端口号
        servChannel.socket().bind(new InetSocketAddress(8080), 1024);
        // 注册感兴趣事件
        servChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 阻塞
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            SelectionKey key = null;
            while (it.hasNext()) {
                key = it.next();
                it.remove();
                if (key.isValid()) {
                    // 处理新接入的请求消息
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        // 接收客户端的连接，并创建一个SocketChannel
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        // 将SocketChannel和感兴趣事件注册到selector
                        sc.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        // 读数据的处理
                    }
                }
            }
        }
    }
}
