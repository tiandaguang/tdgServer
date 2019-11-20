package com.boot.demo.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @DATE 2019/11/19 19:47
 * @Description NIO服务端
 * @Author Tian Daguang
 **/
public class TdgNioServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.socket().bind(new InetSocketAddress(9999));

            while (true) {
                SocketChannel socketChannel =
                        serverSocketChannel.accept();
                Selector selector = Selector.open();
                socketChannel.configureBlocking(false);
//                SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {
                            System.out.println("isAcceptable");
                            // a connection was accepted by a ServerSocketChannel.
                        } else if (key.isConnectable()) {
                            System.out.println("isConnectable");
                            // a connection was established with a remote server.
                        } else if (key.isReadable()) {
                            System.out.println("isReadable");
                            // a channel is ready for reading
                        } else if (key.isWritable()) {
                            System.out.println("isWritable");
                            // a channel is ready for writing
                        }
                        keyIterator.remove();
                    }
                }
                //do something with socketChannel...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
