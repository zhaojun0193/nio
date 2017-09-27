package com.zhaojun.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @auther zhaojun0193
 * @create 2017/9/23
 */
public class NioServer {

    private static final int BUG_SIZE = 1024;
    private static final int PORT = 8888;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) {
        selector();
    }

    public static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel accept = channel.accept();

        accept.configureBlocking(false);
        accept.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUG_SIZE));
    }

    public static void handleRead(SelectionKey key) throws IOException{
        SocketChannel socketChannel = (SocketChannel) key.channel();

        ByteBuffer buffer = (ByteBuffer) key.attachment();

        long bytesRead = socketChannel.read(buffer);

        while (bytesRead > 0){
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.print((char)buffer.get());
            }
            System.out.println();
            buffer.clear();
            bytesRead = socketChannel.read(buffer);
        }
        if (bytesRead == -1){
            socketChannel.close();
        }
    }

    public static void handleWrite(SelectionKey key) throws IOException{
        ByteBuffer buffer = (ByteBuffer)key.attachment();

        buffer.flip();

        SocketChannel socketChannel = (SocketChannel)key.channel();

        while (buffer.hasRemaining()){
            socketChannel.write(buffer);
        }
        buffer.compact();
    }

    public static void selector() {
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;
        try{
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

            while (true){
                if(selector.select(TIMEOUT) == 0){
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()){
                        handleAccept(selectionKey);
                    }

                    if(selectionKey.isReadable()){
                        handleRead(selectionKey);
                    }

                    if(selectionKey.isValid() && selectionKey.isWritable()){
                        handleWrite(selectionKey);
                    }

                    if(selectionKey.isConnectable()){
                        System.out.println("isConnectable = true");
                    }

                    iterator.remove();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(selector != null){
                    selector.close();
                }

                if(serverSocketChannel != null){
                    serverSocketChannel.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
